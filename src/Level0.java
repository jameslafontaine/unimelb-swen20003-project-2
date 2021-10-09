import bagel.*;
import bagel.util.*;

import java.util.ArrayList;
import java.util.ListIterator;

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


    public Level0() {
        background = new Image("res/level-0/background.png");
        bird = new Bird(BIRD_WING_DOWN, BIRD_WING_UP);
        startingLives = STARTING_LIVES;
        lives = STARTING_LIVES;
        scoreThreshold = SCORE_THRESHOLD;
    }

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

    protected void generatePipeSet() {
        pipeSets.add(new PlasticPipeSet(LEVEL_ZERO));
    }

    protected void drawStartMessage() {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(START_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(START_MESSAGE) / 2.0,
                     CENTRE_SCREEN.y + FONT_SIZE / 2.0);
    }

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
            // we also have to detect for pipe passes, collisions, and out of bounds
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
                bird.update(input);
                for (PipeSet pipeSet: pipeSets) {
                    pipeSet.update();
                }
                FONT.drawString("SCORE: " + score, SCORE_POINT.x, SCORE_POINT.y);
                FONT.drawString("TIMESCALE " + timescale, SCORE_POINT.x, SCORE_POINT.y + 100);
                renderLifeBar();
                if (pipeFrameCount >= pipeSpawnFrequency) {
                    generatePipeSet();
                    pipeFrameCount = 0;
                }
                detectCollision();
                detectOutOfBounds();
                detectPipePass();
                pipeFrameCount++;
            } else if (score == scoreThreshold) {
                if (levelUpFrameCount <= LEVELUP_DURATION) {
                    drawEndMessage(LEVELUP_MESSAGE);
                    levelUpFrameCount++;
                } else {
                    levelCompleted = true;
                }
            } else if (lives == NO_LIVES) {
                drawGameOver();
            }
        }
    }
}

