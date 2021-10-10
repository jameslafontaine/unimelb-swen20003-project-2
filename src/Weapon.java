import bagel.*;
import bagel.util.*;

/**
 * A class which stores all methods and attributes relating to a Weapon
 * @author James La Fontaine
 */
public class Weapon {
    protected Image image;
    protected static final int MAX_Y = 500;
    protected static final int MIN_Y = 100;
    protected final Point START_POINT = new Point(ShadowFlap.WINDOW_WIDTH, Math.random() * (MAX_Y - MIN_Y) + MIN_Y);
    // adjusted for FPS reasons
    protected static final double SHOOT_SPEED = 2.5;
    protected static final double TIMESCALE_FACTOR = 1.5;
    protected double x = START_POINT.x;
    protected double y = START_POINT.y;
    protected double shootingRange;
    protected double framesTravelled;
    protected Rectangle hitbox;
    // adjusted for FPS reasons
    protected static double stepSize = 2.5;
    protected boolean isAttached = false;
    protected boolean wasShot = false;

    /**
     * Gets the right x coordinate of a Weapon
     * @return double The x coordinate of the right side of the Weapon
     */
    public double getRightX() { return hitbox.right(); }

    /**
     * Gets the hitbox of the weapon
     * @return Rectangle The hitbox of the weapon
     */
    public Rectangle getHitbox() { return hitbox;}

    /**
     * Returns whether this weapon is currently attached to the bird
     * @return boolean A state indicating if this weapon is currently held by the bird
     */
    public boolean getIsAttached() { return isAttached; }

    /**
     * Returns whether this weapon has been shot
     * @return boolean A state indicating if this weapon has been shot
     */
    public boolean getWasShot() { return wasShot; }

    /**
     * Returns a double indicating how many frames a weapon can travel for after being shot
     * @return double A value indicating how many frames a weapon can travel for after being shot
     */
    public double getShootingRange() { return shootingRange; }

    /**
     * Returns a double indicating how many frames a weapon has travelled for since being shot
     * @return double A value indicating how many frames a weapon has travelled for since being shot
     */
    public double getFramesTravelled() { return framesTravelled; }

    /**
     * Sets the state of isAttached to the provided state
     * @param state A boolean indicating if this weapon is currently being held by the bird or not
     */
    public void setIsAttached(boolean state) { isAttached = state; }


    /**
     * Sets the state of wasShot to the provided state
     * @param state A boolean indicating if this weapon has been shot
     */
    public void setWasShot(boolean state) { wasShot = state; }

    /**
     * Sets the x coordinate of the weapon to the provided double
     * @param x A double representing the x coordinate of the weapon
     */
    public void setX(double x) { this.x = x; }

    /**
     * Sets the y coordinate of the weapon to the provided double
     * @param y A double representing the y coordinate of the weapon
     */
    public void setY(double y) { this.y = y; }

    /**
     * Increases the stepSize of all Weapons by 50% so that they move faster from right to left
     */
    public static void increaseStepSize() { stepSize *= TIMESCALE_FACTOR; }

    /**
     * Undoes an increase in the stepSize of all Weapons so that they move slower from right to left
     */
    public static void decreaseStepSize() { stepSize /= TIMESCALE_FACTOR; }

    /**
     * Performs a state update for the Weapon
     */
    public void update() {
        if (!isAttached && !wasShot) {
            // draw and move the weapon and its hitbox towards the left border of the screen
            x -= stepSize;
            image.draw(x, y);
            hitbox.moveTo(new Point(x - image.getWidth() / 2.0, y - image.getHeight() / 2.0));
        } else if (isAttached) {
            // when the weapon is picked up, its x and y coordinates will be updated by the level class
            // to keep the weapon attached to the bird
            image.draw(x, y);
        } else {
            // the weapon has been shot and will move towards the right until reaching the end of
            // its shooting range, or it collides with a pipe
            if (framesTravelled <= shootingRange) {
                x += SHOOT_SPEED;
                image.draw(x, y);
                hitbox.moveTo(new Point(x - image.getWidth() / 2.0, y - image.getHeight() / 2.0));
                framesTravelled++;
            }
        }
    }


}
