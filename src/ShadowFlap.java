import bagel.*;
import bagel.util.Point;

/**
 * @author: James La Fontaine
 */
public class ShadowFlap extends AbstractGame {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;
    private static final int NUM_LEVELS = 2;
    private int currentLevel = 0;
    private Level[] levels = new Level[NUM_LEVELS];

    public ShadowFlap() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, "ShadowFlap");
        levels[0] = new Level0();
        levels[1] = new Level1();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (!levels[currentLevel].getLevelCompleted()) {
            levels[currentLevel].update(input);
        } else {
            currentLevel += 1;
        }
    }

}
