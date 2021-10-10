import bagel.Image;

/**
 * A subclass of the weapon class relating to the rock weapon
 * @author: James La Fontaine
 */
public class Rock extends Weapon {

    /**
     * Creates an instance of the rock class by setting the appropriate image and shooting range and creating a hitbox
     */
    public Rock() {
        image = new Image("res/level-1/rock.png");
        hitbox = image.getBoundingBoxAt(START_POINT);
        // adjusted for FPS reasons
        shootingRange = 60;
    }
}
