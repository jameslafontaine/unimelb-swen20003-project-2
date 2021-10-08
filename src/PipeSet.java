import bagel.*;
import bagel.util.*;


public abstract class PipeSet {
    protected static final int PIPE_GAP = 168;
    protected static final int HIGH_GAP_CENTRE = 184;
    protected static final int MID_GAP_CENTRE = 384;
    protected static final int LOW_GAP_CENTRE = 584;
    protected double xTop;
    protected double yTop;
    protected double xBottom;
    protected double yBottom;
    protected final Image pipeImage;
    protected Rectangle hitboxTop;
    protected Rectangle hitboxBottom;
    protected int stepSize = 3;
    protected boolean hasPassed = false;


    public double getRightX() { return hitboxTop.right(); }

    public Rectangle getHitBoxTop() {
        return hitboxTop;
    }

    public Rectangle getHitboxBottom() {
        return hitboxBottom;
    }

    public abstract void update();

        // visualise the pipe hitboxes (put these in the subclasses)

        // Drawing.drawRectangle(new Point(xTop - pipe.getWidth() / 2.0, yTop - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
        // Drawing.drawRectangle(new Point(xBottom - pipe.getWidth() / 2.0, yBottom - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
    }
}
