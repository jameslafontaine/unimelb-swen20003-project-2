import bagel.*;
import bagel.util.*;

public class Bird {
    private final Image birdWingDown;
    private final Image birdWingUp;
    private final Rectangle hitbox;
    private static final Point START_POINT = new Point(200, 350);
    private static final int FLAP_FRAME = 10;
    // constants adjusted for FPS reasons
    private static final double GRAVITY = -0.1;
    private static final double TERMINAL_VELOCITY = -4;
    private static final double FLIGHT_SPEED = 3.5;

    private final double x = START_POINT.x;
    private double y = START_POINT.y;
    private double velocity = 0;
    private int frameCount = 1;
    private boolean holdingWeapon = false;


    public Bird(Image birdWingDown, Image birdWingUp) {
        this.birdWingDown = birdWingDown;
        this.birdWingUp = birdWingUp;
        hitbox = birdWingDown.getBoundingBoxAt(START_POINT);
    }

    public Point getPosition() { return new Point(x, y); }

    public double getImageHeight() { return birdWingDown.getHeight(); }

    public Rectangle getHitbox() { return hitbox; }

    public boolean getHoldingWeapon() { return holdingWeapon; }

    public void setHoldingWeapon(boolean state) { holdingWeapon = state; }

    public void resetPosition() {
        y = START_POINT.y;
    }

    public void update(Input input) {
        // set the velocity of the bird to be 6 pixels upwards whenever space bar is pressed
        if (input.wasPressed(Keys.SPACE)) {
            velocity = FLIGHT_SPEED;
            y += velocity;
        } else {
            // increase the rate at which the bird is falling every frame until the maximum fall velocity is reached
            velocity = Math.max(TERMINAL_VELOCITY, velocity + GRAVITY);
            y -= velocity;
        }

        // draw the bird, with wings up every 10th frame to simulate the flapping of its wings, and
        // keep the bird's hitbox aligned with its image
        if (frameCount == FLAP_FRAME) {
            birdWingUp.draw(x, y);
            hitbox.moveTo(new Point(x - birdWingUp.getWidth() / 2.0, y - birdWingUp.getHeight() / 2.0));
            frameCount = 1;
        } else {
            birdWingDown.draw(x, y);
            hitbox.moveTo(new Point(x - birdWingDown.getWidth() / 2.0, y - birdWingDown.getHeight() / 2.0));
            frameCount++;
        }

        // visualise the bird's hitbox
        //Drawing.drawRectangle(new Point(x - birdWingDown.getWidth() / 2.0, y - birdWingDown.getHeight() / 2.0),
          //                                             birdWingDown.getWidth(), birdWingDown.getHeight(), Colour.WHITE);


    }




}
