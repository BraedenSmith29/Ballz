public class Block {

    int maxHealth;
    int health;
    int xPos;
    int yPos;

    public Block(int h) {
        maxHealth = h;
        health = h;
    }

    public void updateCoordinates(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void decreaseHealth() {
        if (--health <= 0) {
            Window.removeBlock(this);
        }
    }

    public int getHealth() {
        return health;
    }

    public double getPercentHealth() {
        return (double)health/(double)maxHealth;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}
