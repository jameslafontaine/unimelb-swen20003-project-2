import bagel.Image;

public class Rock extends Weapon {

    public Rock() {
        image = new Image("res/level-1/rock.png");
        hitbox = image.getBoundingBoxAt(START_POINT);
        type = ROCK;
        // adjusted for FPS reasons
        shootingRange = 60;
    }
}
