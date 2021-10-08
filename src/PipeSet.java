import bagel.*;
import bagel.util.*;


public abstract class PipeSet {
    protected static final int PIPE_GAP = 168;
    protected static final int HIGH_GAP_CENTRE = 184;
    protected static final int MID_GAP_CENTRE = 384;
    protected static final int LOW_GAP_CENTRE = 584;
    protected static final int LEVEL_ZERO = 0;
    protected static final int START_X = ShadowFlap.WINDOW_WIDTH;
    protected double xTop = START_X;
    protected double yTop;
    protected double xBottom = START_X;
    protected double yBottom;
    protected Image pipeImage;
    protected Rectangle hitboxTop;
    protected Rectangle hitboxBottom;
    protected int stepSize = 3;
    protected boolean hasPassed = false;


    protected DrawOptions drawOptions = new DrawOptions();


    public double getRightX() { return hitboxTop.right(); }

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
