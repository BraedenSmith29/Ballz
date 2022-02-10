public class BallIncrease extends Block {
    
    
    public BallIncrease(int h) {
        super(h);
    }
    
    @Override
    public void decreaseHealth() {
       BallLauncher.addBall();
       Window.removeBlock(this);
    }
}
