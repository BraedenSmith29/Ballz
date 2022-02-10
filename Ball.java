public class Ball {
    
    private final float speed = 6;
    private double xPos = 225;
    private double yPos = 646;
    private double deltaX;
    private double deltaY;
    
    public Ball(double a) {
        deltaX = speed * Math.cos(a);
        deltaY = speed * Math.sin(a);
    }
    
    public void update() {
        if (xPos <= 0 || xPos >= 446) deltaX *= -1;
        if (yPos <= 50) deltaY *= -1;
        if (yPos > 646) Window.removeBall(this);
        
        Block other = Window.checkCollision((int)xPos, (int)yPos);
        if (other != null) {
            int otherX = other.getX();
            int otherY = other.getY();
            double xDistance = Math.abs((otherX + 20) - xPos);
            double yDistance = Math.abs((otherY + 20) - yPos);
            if (xDistance < 20 && yDistance < 20) {
                other.decreaseHealth();
                if (xDistance > yDistance) {
                    deltaX *= -1;
                } else {
                    deltaY *= -1;
                }
            }
        }
        
        xPos += deltaX;
        yPos += deltaY;
    }
    
    public double getX() {
        return xPos;
    }
    
    public double getY() {
        return yPos;
    }
}
