import bagel.*;
import bagel.util.*;

public class SteelPipeSet extends PipeSet {
    private final Image flameImage = new Image("res/level-1/flame.png");
    private static final int FLAME_GAP = 10;
    // adjusted for FPS reasons
    private int flameDuration = 70;
    private int flameFrequency = 50;
    private int flameFrameCount = 1;
    private int flameActiveFrameCount = 1;


    public SteelPipeSet() {
        pipeImage = new Image("res/level-1/steelPipe.png");
        // use level 1 pipe spawning logic
        // (i.e. random y coordinate with the centre of the gap bounded between [184, 584])
        double randomNum = Math.random();
        yTop = randomNum * (HIGH_GAP_CENTRE - LOW_GAP_CENTRE) + LOW_GAP_CENTRE
                - pipeImage.getHeight() / 2.0 - PIPE_GAP / 2.0;
        yBottom = randomNum * (HIGH_GAP_CENTRE - LOW_GAP_CENTRE) + LOW_GAP_CENTRE
                + pipeImage.getHeight() / 2.0 + PIPE_GAP / 2.0;
        hitboxTop = pipeImage.getBoundingBoxAt(new Point(xTop, yTop));
        hitboxBottom = pipeImage.getBoundingBoxAt(new Point(xBottom, yBottom));
    }

    public void update() {

        // move the pipes towards the left border of the screen
        xTop -= stepSize;
        xBottom -= stepSize;

        // render the flames within the gap of the pipes at the appropriate frequency for the appropriate duration
        if (flameFrameCount >= flameFrequency) {
            if (flameActiveFrameCount <= flameDuration) {
                flameImage.draw(xTop, yTop + pipeImage.getHeight() / 2.0 + FLAME_GAP);
                flameImage.draw(xBottom, yBottom - pipeImage.getHeight() / 2.0 - FLAME_GAP,
                                                                                    drawOptions.setRotation(Math.PI));
                pipeImage.draw(xTop, yTop);
                pipeImage.draw(xBottom, yBottom, drawOptions.setRotation(Math.PI));
                // shift the hitbox inwards at the gap to account for the 'hitbox' of the flames
                hitboxTop.moveTo(new Point(xTop - pipeImage.getWidth() / 2.0,
                                           yTop - pipeImage.getHeight() / 2.0 + flameImage.getHeight() / 2.0
                                                                                 + FLAME_GAP));
                hitboxBottom.moveTo(new Point(xBottom - pipeImage.getWidth() / 2.0,
                                              yBottom - pipeImage.getHeight() / 2.0 - flameImage.getHeight() / 2.0
                                                                                       - FLAME_GAP));
                flameActiveFrameCount++;
                // visualise the pipe with flame hitbox
                //Drawing.drawRectangle(new Point(xTop - pipeImage.getWidth() / 2.0, yTop - pipeImage.getHeight() / 2.0 + flameImage.getHeight() / 2.0 + FLAME_GAP),
                //        pipeImage.getWidth(), pipeImage.getHeight(), Colour.RED);
                //Drawing.drawRectangle(new Point(xBottom - pipeImage.getWidth() / 2.0,
                //        yBottom - pipeImage.getHeight() / 2.0 - flameImage.getHeight() / 2.0 - FLAME_GAP), pipeImage.getWidth(),
                //           pipeImage.getHeight(), Colour.RED);

            } else {
                flameActiveFrameCount = 1;
                flameFrameCount = 1;
            }
        } else {
            // in between flame rendering we just render the pipes normally with their regular hitbox
            pipeImage.draw(xTop, yTop);
            pipeImage.draw(xBottom, yBottom, drawOptions.setRotation(Math.PI));
            hitboxTop.moveTo(new Point(xTop - pipeImage.getWidth() / 2.0, yTop - pipeImage.getHeight() / 2.0));
            hitboxBottom.moveTo(new Point(xBottom - pipeImage.getWidth() / 2.0,
                    yBottom - pipeImage.getHeight() / 2.0));

            flameFrameCount++;

            // visualise the pipes without flames
            //Drawing.drawRectangle(new Point(xTop - pipeImage.getWidth() / 2.0, yTop - pipeImage.getHeight() / 2.0),
            //        pipeImage.getWidth(), pipeImage.getHeight(), Colour.WHITE);
            //Drawing.drawRectangle(new Point(xBottom - pipeImage.getWidth() / 2.0, yBottom - pipeImage.getHeight() / 2.0),
            //        pipeImage.getWidth(), pipeImage.getHeight(), Colour.WHITE);
        }
    }
}
