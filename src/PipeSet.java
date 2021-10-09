import bagel.*;
import bagel.util.*;


public abstract class PipeSet {
    protected static final int PIPE_GAP = 168;
    protected static final int HIGH_GAP_CENTRE = 184;
    protected static final int MID_GAP_CENTRE = 384;
    protected static final int LOW_GAP_CENTRE = 584;
    protected static final int START_X = ShadowFlap.WINDOW_WIDTH;
    protected double xTop = START_X;
    protected double yTop;
    protected double xBottom = START_X;
    protected double yBottom;
    protected Image pipeImage;
    protected Rectangle hitboxTop;
    protected Rectangle hitboxBottom;
    // adjusted for FPS reasons
    protected static double stepSize = 2.5;
    protected boolean hasPassed = false;


    protected DrawOptions drawOptions = new DrawOptions();


    public double getRightX() { return hitboxTop.right(); }

    public Rectangle getTrueHitboxTop() { return new Rectangle(hitboxTop.left(), yTop - pipeImage.getHeight() / 2.0, pipeImage.getWidth(), pipeImage.getHeight()); }

    public Rectangle getTrueHitboxBottom() { return new Rectangle(hitboxBottom.left(), yBottom - pipeImage.getHeight() / 2.0, pipeImage.getWidth(), pipeImage.getHeight()); }

    public Rectangle getHitBoxTop() {
        return hitboxTop;
    }

    public Rectangle getHitboxBottom() {
        return hitboxBottom;
    }

    public boolean getHasPassed() { return hasPassed; }

    public void setHasPassed(boolean state) { hasPassed = state; }

    public abstract void update();

        // visualise the pipe hitboxes (put these in the subclasses)

        // Drawing.drawRectangle(new Point(xTop - pipe.getWidth() / 2.0, yTop - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
        // Drawing.drawRectangle(new Point(xBottom - pipe.getWidth() / 2.0, yBottom - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
}
