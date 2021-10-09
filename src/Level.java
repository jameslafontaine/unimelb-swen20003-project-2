import bagel.*;
import bagel.util.*;

import java.util.ArrayList;

public abstract class Level {
    protected Image background;
    protected Bird bird;
    protected ArrayList<PipeSet> pipeSets = new ArrayList<>();
    protected static final Image FULL_LIFE = new Image("res/level/fullLife.png");
    protected static final Image NO_LIFE = new Image("res/level/noLife.png");
    protected static final int FONT_SIZE = 48;
    protected static final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    protected static final String START_MESSAGE = "PRESS SPACE TO START";
    protected static final String LOSS_MESSAGE = "GAME OVER";
    protected static final int LOSS_SCORE_GAP = 75;
    protected static final Point SCORE_POINT = new Point(100, 100);
    protected static final Point LIFE_POINT = new Point(100, 15);
    protected static final int LIFE_GAP = 50;
    protected static final int MAX_TIMESCALE = 5;
    protected static final int MIN_TIMESCALE = 1;
    protected static final int NO_LIVES = 0;
    protected static final int WINDOW_WIDTH = ShadowFlap.WINDOW_WIDTH;
    protected static final int WINDOW_HEIGHT = ShadowFlap.WINDOW_HEIGHT;
    protected static final int LEFT_BORDER = 0;
    protected static final Point CENTRE_SCREEN = new Point(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);
    protected static final int INCREASE = 1;
    protected static final int DECREASE = -1;
    protected int scoreThreshold;
    protected int score = 0;
    protected int timescale = 1;
    protected int startingLives;
    protected int lives;
    protected int pipeFrameCount = 1;
    // adjusted for FPS reasons
    protected int pipeSpawnFrequency = 300;
    protected boolean levelStarted = false;
    protected boolean levelCompleted = false;


    public abstract void update(Input input);
    protected abstract void detectCollision();
    protected abstract void changeTimescale(int change);
    protected abstract void generatePipeSet();
    protected abstract void drawStartMessage();

    public boolean getLevelCompleted() { return levelCompleted; }

    protected void detectOutOfBounds() {
        if (bird.getPosition().y < -bird.getImageHeight() / 2.0 || bird.getPosition().y > ShadowFlap.WINDOW_HEIGHT +
                                                                   bird.getImageHeight() / 2.0) {
            lives--;
            bird.resetPosition();
        }
        for (PipeSet pipeSet: pipeSets) {
            if (pipeSet.getRightX() < LEFT_BORDER) {
                pipeSets.remove(pipeSet);
            }
        }
    }

    protected void detectPipePass() {
        for (PipeSet pipeSet: pipeSets) {
            if (bird.getPosition().x > pipeSet.getRightX() && pipeSet.getHasPassed() == false) {
                score++;
                pipeSet.setHasPassed(true);
            }
        }
    }

    protected void renderLifeBar() {
        for (int i=0; i < lives; i++) {
            FULL_LIFE.drawFromTopLeft(LIFE_POINT.x + i*LIFE_GAP , LIFE_POINT.y);
        }
        for (int i=lives; i < startingLives; i++) {
            NO_LIFE.drawFromTopLeft(LIFE_POINT.x + i*LIFE_GAP, LIFE_POINT.y);
        }
    }

    protected void drawEndMessage(String message) {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(message, CENTRE_SCREEN.x - FONT.getWidth(message) / 2.0,
                     CENTRE_SCREEN.y + FONT_SIZE / 2.0);
        }

    protected void drawGameOver() {
        background.draw(CENTRE_SCREEN.x, CENTRE_SCREEN.y);
        FONT.drawString(LOSS_MESSAGE,
                CENTRE_SCREEN.x - FONT.getWidth(LOSS_MESSAGE) / 2.0,
                CENTRE_SCREEN.y + FONT_SIZE / 2.0);
        FONT.drawString("FINAL SCORE: " + score,
                CENTRE_SCREEN.x - FONT.getWidth("FINAL SCORE: k") / 2.0,
                CENTRE_SCREEN.y + LOSS_SCORE_GAP + FONT_SIZE / 2.0);
    }


}
