import bagel.Image;

public class Bomb extends Weapon {

    public Bomb() {
        image = new Image("res/level-1/bomb.png");
        hitbox = image.getBoundingBoxAt(START_POINT);
        // adjusted for FPS reasons
        shootingRange = 120;

    }
}
