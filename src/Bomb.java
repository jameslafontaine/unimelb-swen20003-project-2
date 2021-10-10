import bagel.Image;

/**
 * A subclass of the weapon class relating to the bomb weapon
 * @author: James La Fontaine
 */
public class Bomb extends Weapon {

    /**
     * Creates an instance of the bomb class by setting the appropriate image and shooting range and creating a hitbox
     */
    public Bomb() {
        image = new Image("res/level-1/bomb.png");
        hitbox = image.getBoundingBoxAt(START_POINT);
        // adjusted for FPS reasons
        shootingRange = 120;

    }
}
