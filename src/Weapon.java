import bagel.*;
import bagel.util.*;

public class Weapon {
    protected Image image;
    protected static final int ROCK = 1;
    protected static final int BOMB = 2;
    protected static final int MAX_Y = 500;
    protected static final int MIN_Y = 100;
    protected final Point START_POINT = new Point(ShadowFlap.WINDOW_WIDTH, Math.random() * (MAX_Y - MIN_Y) + MIN_Y);
    // adjusted for FPS reasons
    protected static final double SHOOT_SPEED = 2.5;
    protected double x = START_POINT.x;
    protected double y = START_POINT.y;
    protected int type;
    protected double shootingRange;
    protected double framesTravelled;
    protected Rectangle hitbox;
    // adjusted for FPS reasons
    protected double stepSize = 2.5;
    protected boolean isAttached = false;
    protected boolean wasShot = false;

    public void update(Input input) {
        // shoot the weapon when the S key is pressed
        if (input.wasPressed(Keys.S) && isAttached) {
            wasShot = true;
        }
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
