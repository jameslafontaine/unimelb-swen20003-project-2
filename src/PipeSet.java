import bagel.*;
import bagel.util.*;

/**
 * An abstract class which stores all methods and attributes relating to a PipeSet
 * @author James La Fontaine
 */
public abstract class PipeSet {
    protected static final int PIPE_GAP = 168;
    protected static final int HIGH_GAP_CENTRE = 184;
    protected static final int MID_GAP_CENTRE = 384;
    protected static final int LOW_GAP_CENTRE = 584;
    protected static final int START_X = ShadowFlap.WINDOW_WIDTH;
    protected static final double TIMESCALE_FACTOR = 1.5;
    protected static final double DEFAULT_STEP_SIZE = 2.5;
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

    /**
     * Gets the right x coordinate of a PipeSet
     * @return double The x coordinate of the right side of the PipeSet
     */
    public double getRightX() { return hitboxTop.right(); }

    /**
     * Gets the hitbox of the top pipe without considering flames or any extra features
     * @return Rectangle The hitbox of the top pipe without any extra additions like flames
     */
    public Rectangle getTrueHitboxTop() { return new Rectangle(hitboxTop.left(), yTop - pipeImage.getHeight() / 2.0, pipeImage.getWidth(), pipeImage.getHeight()); }

    /**
     * Gets the hitbox of the bottom pipe without considering flames or any extra features
     * @return Rectangle The hitbox of the bottom pipe without any extra additions like flames
     */
    public Rectangle getTrueHitboxBottom() { return new Rectangle(hitboxBottom.left(), yBottom - pipeImage.getHeight() / 2.0, pipeImage.getWidth(), pipeImage.getHeight()); }

    /**
     * Gets the hitbox of the top pipe while considering flames or any extra features
     * @return Rectangle The hitbox of the top pipe with extra additions like flames accounted for
     */
    public Rectangle getHitBoxTop() {
        return hitboxTop;
    }

    /**
     * Gets the hitbox of the bottom pipe while considering flames or any extra features
     * @return Rectangle The hitbox of the bottom pipe with extra additions like flames accounted for
     */
    public Rectangle getHitboxBottom() {
        return hitboxBottom;
    }

    /**
     * Gets the current state of hasPassed
     * @return boolean A variable representing whether this PipeSet has passed the bird or not
     */
    public boolean getHasPassed() { return hasPassed; }

    /**
     * Sets the state of hasPassed to the provided state
     * @param state A variable representing whether this PipeSet has passed the bird or not
     */
    public void setHasPassed(boolean state) { hasPassed = state; }

    /**
     * Increases the stepSize of all PipeSets by 50% so that they move faster
     */
    public static void increaseStepSize() { stepSize *= TIMESCALE_FACTOR; }

    /**
     * Undoes an increase in the stepSize of all PipeSets so that they move slower
     */
    public static void decreaseStepSize() { stepSize /= TIMESCALE_FACTOR; }

    /**
     * Resets the stepSize of all PipeSets to their default stepSize
     */
    public static void resetStepSize() { stepSize = DEFAULT_STEP_SIZE; }

    /**
     * Performs a state update for the PipeSet
     */
    public abstract void update();

        // visualise the pipe hitboxes (put these in the subclasses)

        // Drawing.drawRectangle(new Point(xTop - pipe.getWidth() / 2.0, yTop - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
        // Drawing.drawRectangle(new Point(xBottom - pipe.getWidth() / 2.0, yBottom - pipe.getHeight() / 2.0),
        //         pipe.getWidth(), pipe.getHeight(), Colour.WHITE);
}
