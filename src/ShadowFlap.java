import bagel.*;

/**
 * @author: James La Fontaine
 *
 * have to remove debugging code like the hitbox visualisers and timescale message, try to clean up code into
 * methods wherever possible or even recode if there is a cleaner solution
 *
 * also make sure visibility modifiers are correct and there are no useless constants or variables anywhere
 *
 * add javadoc to public classes, attributes and methods (including getters, setters and constructors):
 * description of method or attribute
 * @param:
 * @return:
 *
 * for classes:
 * description
 * @author:
 * @version:
 *
 */
public class ShadowFlap extends AbstractGame {

    /**
     * The width of the game window in pixels
     */
    public static final int WINDOW_WIDTH = 1024;
    /**
     * The height of the game window in pixels
     */
    public static final int WINDOW_HEIGHT = 768;
    private static final int NUM_LEVELS = 2;
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private int currentLevel = 0;
    private Level[] levels = new Level[NUM_LEVELS];

    /**
     * Creates an instance of the ShadowFlap class and creates instances of level 0 and level 1 and stores them
     */
    public ShadowFlap() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, "ShadowFlap");
        levels[LEVEL_ZERO] = new Level0();
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
     * @param input This stores the users inputs for each frame / update
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
