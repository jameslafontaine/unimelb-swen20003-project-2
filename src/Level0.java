import bagel.*;

import java.util.ListIterator;

/**
 * A subclass of the Level class which stores attributes and methods specific to Level 0
 * @author: James La Fontaine
 */
public class Level0 extends Level {

    private static final String LEVELUP_MESSAGE = "LEVEL-UP!";
    // adjusted for FPS reasons
    private static final int LEVELUP_DURATION = 350;
    private static final Image BIRD_WING_DOWN = new Image("res/level-0/birdWingDown.png");
    private static final Image BIRD_WING_UP = new Image("res/level-0/birdWingUp.png");
    private static final int STARTING_LIVES = 3;
    private static final int SCORE_THRESHOLD = 10;
    private static final int LEVEL_ZERO = 0;
    private int levelUpFrameCount = 1;

    /**
     * Creates an instance of the Level0 class by setting the appropriate background, creating a new Bird,
     * and setting important metrics to level-specific values
     */
    public Level0() {
        background = new Image("res/level-0/background.png");
        bird = new Bird(BIRD_WING_DOWN, BIRD_WING_UP);
        startingLives = STARTING_LIVES;
        lives = STARTING_LIVES;
        scoreThreshold = SCORE_THRESHOLD;
    }

    // Detects if the bird or pipe sets have moved off the screen and resets the bird's position or
    // removes the pipe set from the game in each respective case
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
    }

    // Detect collisions between the bird and the pipe sets
    protected void detectCollision() {
        ListIterator<PipeSet> iterPipeSet = pipeSets.listIterator();
        while(iterPipeSet.hasNext()) {
            PipeSet pipeSet = iterPipeSet.next();
            if (bird.getHitbox().intersects(pipeSet.getHitBoxTop()) ||
                    bird.getHitbox().intersects(pipeSet.getHitboxBottom())) {
                lives--;
                iterPipeSet.remove();
            }
        }
    }

    // Generates a new PipeSet and stores it in the pipeSet ArrayList
    protected void generatePipeSet() {
        pipeSets.add(new PlasticPipeSet(LEVEL_ZERO));
    }

    // Draws the start of level message
    protected void drawStartMessage() {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(START_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(START_MESSAGE) / 2.0,
                     CENTRE_SCREEN.y + FONT_SIZE / 2.0);
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
            }
        } else {
            // otherwise, the level has started, and we must constantly update and draw the bird and pipes' positions.
            // we must draw the score counter and life bar and generate pipe sets.
            // we also have to detect pipe passes, collisions, and out of bounds.
            if (score < scoreThreshold && lives > NO_LIVES) {
                // timescale adjustments
                if (input.wasPressed(Keys.L) && timescale < MAX_TIMESCALE) {
                    PipeSet.increaseStepSize();
                    pipeSpawnFrequency = (int) Math.round(pipeSpawnFrequency / TIMESCALE_FACTOR);
                    pipeFrameCount = (int) Math.round(pipeFrameCount / TIMESCALE_FACTOR);
                    timescale++;
                }
                if (input.wasPressed(Keys.K) && timescale > MIN_TIMESCALE) {
                    PipeSet.decreaseStepSize();
                    pipeSpawnFrequency = (int) Math.round(pipeSpawnFrequency * TIMESCALE_FACTOR);
                    pipeFrameCount = (int) Math.round(pipeFrameCount * TIMESCALE_FACTOR);
                    timescale--;
                }
                background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
                // update the bird and pipe sets
                bird.update(input);
                for (PipeSet pipeSet: pipeSets) {
                    pipeSet.update();
                }
                // render score and life bar
                FONT.drawString("SCORE: " + score, SCORE_POINT.x, SCORE_POINT.y);
                renderLifeBar();
                // generate pipes at the appropriate frequency
                if (pipeFrameCount >= pipeSpawnFrequency) {
                    generatePipeSet();
                    pipeFrameCount = 0;
                }
                // detect events and interactions between the bird and pipe sets relevant to the game
                detectCollision();
                detectOutOfBounds();
                detectPipePass();
                pipeFrameCount++;
            } else if (score == scoreThreshold) {
                // the player has reached the required score so display the level up message for the specified duration
                // and move on to the next level
                if (levelUpFrameCount <= LEVELUP_DURATION) {
                    drawEndMessage(LEVELUP_MESSAGE);
                    levelUpFrameCount++;
                } else {
                    levelCompleted = true;
                }
            } else if (lives == NO_LIVES) {
                // the player has lost, draw the game over message until ESC is pressed
                drawGameOver();
            }
        }
    }
}

