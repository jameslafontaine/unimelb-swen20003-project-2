import bagel.*;
import bagel.util.Point;

/**
 * @author: James La Fontaine
 */
public class ShadowFlap extends AbstractGame {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;
    private static final int NUM_LEVELS = 2;
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private int currentLevel = 1;
    private Level[] levels = new Level[NUM_LEVELS];

    public ShadowFlap() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, "ShadowFlap");
        // levels[LEVEL_ZERO] = new Level0();
        levels[LEVEL_ONE] = new Level1();
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
