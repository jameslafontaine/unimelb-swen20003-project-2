import bagel.*;
import bagel.util.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class Level1 extends Level {

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private static final String SHOOT_MESSAGE = "PRESS 'S TO SHOOT";
    private static final int SHOOT_MESSAGE_GAP = 68;
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    // adjusted for FPS reasons
    private static final Image BIRD_WING_DOWN = new Image("res/level-1/birdWingDown.png");
    private static final Image BIRD_WING_UP = new Image("res/level-1/birdWingUp.png");
    private static final int STARTING_LIVES = 6;
    private static final int SCORE_THRESHOLD = 30;
    private static final int LEVEL_ZERO = 0;
    protected static final int LEVEL_ONE = 1;
    private int weaponFrameCount = pipeSpawnFrequency / 2;


    public Level1() {
        background = new Image("res/level-1/background.png");
        bird = new Bird(BIRD_WING_DOWN, BIRD_WING_UP);
        startingLives = STARTING_LIVES;
        lives = STARTING_LIVES;
        scoreThreshold = SCORE_THRESHOLD;
    }

    private void generateWeapon() {
        if (Math.random() < 0.5) {
            weapons.add(new Rock());
        } else {
            weapons.add(new Bomb());
        }
    }

    private void keepWeaponAttached() {
        for (Weapon weapon: weapons) {
            if (weapon.getIsAttached()) {
                weapon.setX(bird.getHitbox().right());
                weapon.setY(bird.getPosition().y);
            }
        }
    }

    protected void detectCollision() {
        // check if the bird has collided with a pipe
        for (PipeSet pipeSet: pipeSets) {
            if (bird.getHitbox().intersects(pipeSet.getHitBoxTop()) ||
                    bird.getHitbox().intersects(pipeSet.getHitboxBottom())) {
                lives--;
                pipeSets.remove(pipeSet);
            }
        }
        // check for weapon collisions with pipes
        ListIterator<PipeSet> iterPipeSet = pipeSets.listIterator();
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

    private void checkWeaponTravel() {
        weapons.removeIf(weapon -> weapon.getWasShot() && weapon.getFramesTravelled() > weapon.getShootingRange());
    }

    protected void changeTimescale(int change) {
        return;
    }

    protected void generatePipeSet() {
       if (Math.random() < 0.5) {
           pipeSets.add(new PlasticPipeSet(LEVEL_ONE));
       } else {
           pipeSets.add(new SteelPipeSet());
       }
    }

    protected void drawStartMessage() {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(START_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(START_MESSAGE) / 2.0,
                CENTRE_SCREEN.y + FONT_SIZE / 2.0);
        FONT.drawString(SHOOT_MESSAGE, CENTRE_SCREEN.x - FONT.getWidth(SHOOT_MESSAGE) / 2.0,
                CENTRE_SCREEN.y + FONT_SIZE / 2.0 + SHOOT_MESSAGE_GAP);
    }

    public void update(Input input) {
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
        // display the starting message until the player presses space bar for the first time and starts the level
        if (!levelStarted) {
            drawStartMessage();
            if (input.wasPressed(Keys.SPACE)) {
                levelStarted = true;
            }
        } else {
            // otherwise, the level has started, and we must constantly update and draw the bird and pipes' positions.
            // we must draw the score counter and life bar, and generate pipe sets and weapons.
            // we also have to detect pipe passes, collisions, and out of bounds.
            if (score < scoreThreshold && lives > NO_LIVES) {
                background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
                bird.update(input);
                for (PipeSet pipeSet: pipeSets) {
                    pipeSet.update();
                }
                for (Weapon weapon: weapons) {
                    weapon.update();
                }
                FONT.drawString("SCORE: " + score, SCORE_POINT.x, SCORE_POINT.y);
                renderLifeBar();
                if (pipeFrameCount == pipeSpawnFrequency) {
                    generatePipeSet();
                    pipeFrameCount = 0;
                }
                if (weaponFrameCount == pipeSpawnFrequency * 2) {
                    generateWeapon();
                    weaponFrameCount = 0;
                }
                detectCollision();
                detectOutOfBounds();
                detectPipePass();
                keepWeaponAttached();
                checkWeaponTravel();
                pipeFrameCount++;
                weaponFrameCount++;
            } else if (score == scoreThreshold) {
                drawEndMessage(WIN_MESSAGE);
            } else if (lives == NO_LIVES) {
                drawGameOver();
            }
        }
    }
}
