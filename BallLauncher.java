public class BallLauncher {

    private static double angle;
    private static int ballCount = 1;
    private static int ballsFired = 0;
    private static boolean firing = false;
    private static boolean canFire = true;
    private static int firingLag = 0;
    private static int xPos = 225;
    private static int yPos = 650;

    public BallLauncher() {

    }
    
    public static int getBalls() {
        return ballCount;
    }

    public static void update() {
        if (firing && ballCount > 0) {
            if (firingLag >= 10) {
                Window.addBall(angle);
                if (++ballsFired >= ballCount) {
                    firing = false;
                    ballsFired = 0;
                }
                firingLag = 0;
            } else {
                firingLag++;
            }
        }
    }

    public static void aim(int mouseX, int mouseY) {
        if (firing) return;
        if (mouseX - xPos == 0) {
            angle = (3 * Math.PI) / 2;
            return;
        }
        angle = Math.atan((double)(mouseY - yPos)/(double)(mouseX - xPos));
        if (angle > 0) angle = Math.PI + angle;
    }
    
    public static void canFire() {
        canFire = true;
    }

    public static double getAngle() {
        return angle;
    }

    public static void fire() {
        if (canFire) {
            firing = true;
            canFire = false;
        }
    }
    
    public static void addBall() {
        ballCount++;
    }
}
