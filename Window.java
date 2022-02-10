import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Window extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private static JFrame frame = new JFrame("Ballz");
    private static Window window = new Window();
    private static int roundsCleared = 0;
    private static ArrayList<Ball> balls = new ArrayList<Ball>();
    private static ArrayList<Block[]> blocks = new ArrayList<Block[]>();
    private static boolean gameOver = false;

    public static void main(String[] args) {

        nextRound();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(window, BorderLayout.CENTER);
        frame.setSize(475, 700);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Window() {
        setBackground(new Color(0, 255, 0));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        Timer timer = new Timer(1, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        BallLauncher.update();
        if (balls.size() > 0)
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).update();
            }
        repaint();
    }

    public static void addBall(double angle) {
        balls.add(new Ball(angle));
    }

    public static void nextRound() {
        roundsCleared++;
        Block[] row = new Block[9];
        Random rand = new Random();
        int blockAmount = rand.nextInt(9);
        int doubleBlockAmount = blockAmount - rand.nextInt(4);
        blockAmount -= doubleBlockAmount;

        for (int i = 0; i < row.length; i++) {
            row[i] = null;
        }
        for (int i = 0; i < blockAmount; i++) {
            int pos = rand.nextInt(9);
            while (row[pos] != null) {
                pos = rand.nextInt(9);
            }
            row[pos] = new Block(roundsCleared);
        }
        for (int i = 0; i < doubleBlockAmount; i++) {
            int pos = rand.nextInt(9);
            while (row[pos] != null) {
                pos = rand.nextInt(9);
            }
            row[pos] = new Block(roundsCleared * 2);
        }
        int pos = rand.nextInt(9);
        while (row[pos] != null) {
            pos = rand.nextInt(9);
        }
        row[pos] = new BallIncrease(-1);

        blocks.add(0, row);
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < 9; j++) {
                Block b = blocks.get(i)[j];
                if (b != null) b.updateCoordinates(10 + 50 * j, 60 + 50 * i);
            }
        }
        if (blocks.size() < 12) {
            BallLauncher.canFire();
        } else {
            boolean end = false;
            
            for (int i = 0; i < blocks.get(11).length; i++) {
                if (blocks.get(11)[i] != null) {
                    end = true;
                    break;
                }
            }
            
            if (end) gameOver = true;
            else BallLauncher.canFire();
        }
    }

    public static void removeBlock(Block b) {
        blocks.get((b.getY() - 60) / 50)[(b.getX() - 10) / 50] = null;
    }

    public static void removeBall(Ball b) {
        for (int i = 0; i < balls.size(); i++) {
            if (b == balls.get(i)) {
                balls.remove(i);
            }
        }
        if (balls.size() == 0) {
            nextRound();
        }
    }

    public void paintComponent(Graphics g) {
        if (gameOver) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, 460, 650);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("Game Over", 30, 325);
            return;
        }
        //board
        g.setColor(new Color(40, 36, 40));
        g.fillRect(0, 0, 460, 50);
        g.setColor(new Color(34, 30, 35));
        g.fillRect(0, 50, 460, 600);
        g.setColor(new Color(255, 255, 255));
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString("Balls: " + BallLauncher.getBalls(), 10, 30);
        //launcher
        double launcherAngle = BallLauncher.getAngle();
        int length = 30;
        g.drawLine(225, 650, 225 + (int)(length * Math.cos(launcherAngle)), 650 + (int)(length * Math.sin(launcherAngle)));
        //balls
        if (balls.size() > 0)
            for (int i = 0; i < balls.size(); i++) {
                g.fillOval((int)balls.get(i).getX(), (int)balls.get(i).getY(), 8, 8);
            }
        //blocks
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < 9; j++) {
                Block b = blocks.get(i)[j];
                if (b != null) {
                    if (b.getHealth() != -1) {
                        int color = (int)(b.getPercentHealth() * 255);
                        g.setColor(new Color(255 - color, color, 0));
                        g.fillRect(b.getX(), b.getY(), 40, 40);
                        g.setColor(new Color(255, 255, 255));
                        String health = Integer.toString(b.getHealth());
                        
                        int digits = 0;
                        int h = b.getHealth();
                        while (h >= 1) {
                            digits++;
                            h /= 10;
                        }
                        
                        int x = 31 - (digits * 6); //change numbers for multiple digits
                        x += 50 * j;
                        g.drawString(health, x, 86 + 50 * i);
                    } else {
                        g.setColor(new Color(0, 0, 255));
                        g.fillRect(b.getX(), b.getY(), 40, 40);
                        g.setColor(new Color(255, 255, 255));
                        g.setFont(new Font("Monospaced", Font.BOLD, 20));
                        g.drawString("+", b.getX() + 15, b.getY() + 26);
                    }
                }
            }
        }
    }

    public static Block checkCollision(int x, int y) {
        x = (x - 10) / 50;
        y = (y - 60) / 50;
        if (y >= blocks.size()) return null;
        return blocks.get(y)[x] != null ? blocks.get(y)[x] : null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        BallLauncher.aim(e.getX(), e.getY());
        if (e.getButton() == 1) {
            BallLauncher.fire();
        }
    }

    public void mouseMoved(MouseEvent e) {
        BallLauncher.aim(e.getX(), e.getY());
    }

    //trash  v
    public void mouseDragged(MouseEvent e) {} 

    public void mouseClicked(MouseEvent e) {} 

    public void mouseEntered(MouseEvent e) {} 

    public void mouseExited(MouseEvent e) {} 

    public void mouseReleased(MouseEvent e) {}
    //trash  ^
}
