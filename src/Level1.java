import bagel.*;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A subclass of the Level class which stores attributes and methods specific to Level 1
 * @author: James La Fontaine
 */
public class Level1 extends Level {

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private static final String SHOOT_MESSAGE = "PRESS 'S' TO SHOOT";
    private static final int SHOOT_MESSAGE_GAP = 68;
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    // adjusted for FPS reasons
    private static final Image BIRD_WING_DOWN = new Image("res/level-1/birdWingDown.png");
    private static final Image BIRD_WING_UP = new Image("res/level-1/birdWingUp.png");
    private static final int STARTING_LIVES = 6;
    private static final int SCORE_THRESHOLD = 30;
    private static final int LEVEL_ONE = 1;
    private int weaponFrameCount = pipeSpawnFrequency / 2;

    /**
     * Creates an instance of the Level1 class by setting the appropriate background, creating a new Bird,
     * and setting important metrics to level-specific values
     */
    public Level1() {
        background = new Image("res/level-1/background.png");
        bird = new Bird(BIRD_WING_DOWN, BIRD_WING_UP);
        startingLives = STARTING_LIVES;
        lives = STARTING_LIVES;
        scoreThreshold = SCORE_THRESHOLD;
    }

    // Generate either a new Rock or Bomb and store it in the weapons ArrayList
    private void generateWeapon() {
        if (Math.random() < 0.5) {
            weapons.add(new Rock());
        } else {
            weapons.add(new Bomb());
        }
    }

    // Keep a weapon attached to the bird's beak if it is currently being held by the bird
    private void keepWeaponAttached() {
        for (Weapon weapon: weapons) {
            if (weapon.getIsAttached()) {
                weapon.setX(bird.getHitbox().right());
                weapon.setY(bird.getPosition().y);
            }
        }
    }

    // Detects if the bird, pipe sets, or weapons have moved off the screen and resets the bird's position or
    // removes the pipe set / weapon from the game in each respective case
    protected void detectOutOfBounds() {
        if (bird.getPosition().y < -bird.getImageHeight() / 2.0 || bird.getPosition().y > ShadowFlap.WINDOW_HEIGHT +
                bird.getImageHeight() / 2.0) {
            lives--;
            bird.resetPosition();
        }
        ListIterator<PipeSet> iterPipeSet = pipeSets.listIterator();
        while(iterPipeSet.hasNext()) {
            PipeSet pipeSet = iterPipeSet.next();
            if (pipeSet.getRightX() < LEFT_BORDER) {
                iterPipeSet.remove();
            }
        }
        ListIterator<Weapon> iterWeapon = weapons.listIterator();
        while(iterWeapon.hasNext()) {
            Weapon weapon = iterWeapon.next();
            if (weapon.getRightX() < LEFT_BORDER) {
                iterWeapon.remove();
            }
        }
    }

    // Detects collisions between the bird, pipe sets and weapons
    protected void detectCollision() {
        // check if the bird has collided with a pipe and remove the pipes and one of the bird's lives if so
        ListIterator<PipeSet> iterPipeSet = pipeSets.listIterator();
        while(iterPipeSet.hasNext()) {
            PipeSet pipeSet = iterPipeSet.next();
            if (bird.getHitbox().intersects(pipeSet.getHitBoxTop()) ||
                    bird.getHitbox().intersects(pipeSet.getHitboxBottom())) {
                lives--;
                iterPipeSet.remove();
            }
        }
        // check for weapon collisions with pipes, remove the weapon if so and remove the pipes if the weapon is
        // strong enough
        iterPipeSet = pipeSets.listIterator();
        while(iterPipeSet.hasNext()) {
            PipeSet pipeSet = iterPipeSet.next();
            ListIterator<Weapon> iterWeapon = weapons.listIterator();
            while(iterWeapon.hasNext()) {
                Weapon weapon = iterWeapon.next();
                if (weapon.getWasShot() && (weapon.getHitbox().intersects(pipeSet.getTrueHitboxTop())
                        || weapon.getHitbox().intersects(pipeSet.getTrueHitboxBottom()))) {

                    if (weapon instanceof Rock && pipeSet instanceof PlasticPipeSet) {
                        iterPipeSet.remove();
                        score++;
                    } else if (weapon instanceof Bomb) {
                        iterPipeSet.remove();
                        score++;
                    }
                    iterWeapon.remove();
                }
            }
        }
        // check if the bird is picking up an unfired weapon when it isn't currently holding one
        for (Weapon weapon: weapons) {
            if (bird.getHitbox().intersects(weapon.getHitbox()) && !weapon.getIsAttached() && !weapon.getWasShot()
                                                                && !bird.getHoldingWeapon()) {
                weapon.setIsAttached(true);
                bird.setHoldingWeapon(true);
            }
        }
    }

    // Checks whether a weapon has exceeded its travelling range and removes it if so
    private void checkWeaponTravel() {
        weapons.removeIf(weapon -> weapon.getWasShot() && weapon.getFramesTravelled() > weapon.getShootingRange());
    }

    // Generates either a new PlasticPipeSet or a new SteelPipeSet and stores it in the pipeSet ArrayList
    protected void generatePipeSet() {
       if (Math.random() < 0.5) {
           pipeSets.add(new PlasticPipeSet(LEVEL_ONE));
       } else {
           pipeSets.add(new SteelPipeSet());
       }
    }

    // Draws the start of level message
    protected void drawStartMessage() {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(START_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(START_MESSAGE) / 2.0,
                CENTRE_SCREEN.y + FONT_SIZE / 2.0);
        FONT.drawString(SHOOT_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(SHOOT_MESSAGE) / 2.0,
                CENTRE_SCREEN.y + FONT_SIZE / 2.0 + SHOOT_MESSAGE_GAP);
    }

    /**
     * Performs a state update for the level.
     * @param input Stores the user's input for the current frame / update
     */
    public void update(Input input) {
        // display the starting message until the player presses space bar for the first time and starts the level
        if (!levelStarted) {
            drawStartMessage();
            if (input.wasPressed(Keys.SPACE)) {
                levelStarted = true;
                PipeSet.resetStepSize();
            }
        } else {
            // otherwise, the level has started, and we must constantly update and draw the bird and pipes' positions.
            // we must draw the score counter and life bar, and generate pipe sets and weapons.
            // we also have to detect pipe passes, collisions, and out of bounds.
            if (score < scoreThreshold && lives > NO_LIVES) {
                // timescale adjustments
                if (input.wasPressed(Keys.L) && timescale < MAX_TIMESCALE) {
                    PipeSet.increaseStepSize();
                    Weapon.increaseStepSize();
                    pipeSpawnFrequency = (int) Math.round(pipeSpawnFrequency / TIMESCALE_FACTOR);
                    pipeFrameCount = (int) Math.round(pipeFrameCount / TIMESCALE_FACTOR);
                    weaponFrameCount = (int) Math.round(weaponFrameCount / TIMESCALE_FACTOR);
                    timescale++;
                }
                if (input.wasPressed(Keys.K) && timescale > MIN_TIMESCALE) {
                    PipeSet.decreaseStepSize();
                    Weapon.decreaseStepSize();
                    pipeSpawnFrequency = (int) Math.round(pipeSpawnFrequency * TIMESCALE_FACTOR);
                    pipeFrameCount = (int) Math.round(pipeFrameCount * TIMESCALE_FACTOR);
                    weaponFrameCount = (int) Math.round(weaponFrameCount * TIMESCALE_FACTOR);
                    timescale--;
                }
                // shoot a held weapon when the S key is pressed
                if (input.wasPressed(Keys.S) && bird.getHoldingWeapon()) {
                    for (Weapon weapon: weapons) {
                        if (weapon.getIsAttached()) {
                            weapon.setWasShot(true);
                            weapon.setIsAttached(false);
                            bird.setHoldingWeapon(false);
                        }
                    }
                }
                background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
                // update the bird, pipe sets and weapons
                bird.update(input);
                for (PipeSet pipeSet: pipeSets) {
                    pipeSet.update();
                }
                for (Weapon weapon: weapons) {
                    weapon.update();
                }
                // render the score and life bar
                FONT.drawString("SCORE: " + score, SCORE_POINT.x, SCORE_POINT.y);
                renderLifeBar();
                // generate pipe sets and weapons at non-overlapping intervals
                if (pipeFrameCount >= pipeSpawnFrequency) {
                    generatePipeSet();
                    pipeFrameCount = 0;
                }
                if (weaponFrameCount >= pipeSpawnFrequency * 2) {
                    generateWeapon();
                    weaponFrameCount = 0;
                }
                // detect the relevant events and interactions between game entities
                detectCollision();
                detectOutOfBounds();
                detectPipePass();
                keepWeaponAttached();
                checkWeaponTravel();
                pipeFrameCount++;
                weaponFrameCount++;

            } else if (score == scoreThreshold) {
                // the player has won, draw the win message until ESC is pressed
                drawEndMessage(WIN_MESSAGE);
            } else if (lives == NO_LIVES) {
                // the player has lost, draw the game over message until ESC is pressed
                drawGameOver();
            }
        }
    }
}
