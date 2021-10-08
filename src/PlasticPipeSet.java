import bagel.*;
import bagel.util.*;

public class PlasticPipeSet extends PipeSet {

    public PlasticPipeSet(int currentLevel) {
        pipeImage = new Image("res/level/plasticPipe.png");
        if (currentLevel == LEVEL_ZERO) {
            // use level 0 pipe spawning logic
            double randomNum = Math.random();
            if (randomNum < 1 / 3.0) {
                yTop = HIGH_GAP_CENTRE - pipeImage.getHeight() / 2.0 - PIPE_GAP / 2.0;
                yBottom = HIGH_GAP_CENTRE + pipeImage.getHeight() / 2.0 + PIPE_GAP / 2.0;
            } else if (randomNum < 2 / 3.0) {
                yTop = MID_GAP_CENTRE - pipeImage.getHeight() / 2.0 - PIPE_GAP / 2.0;
                yBottom = MID_GAP_CENTRE + pipeImage.getHeight() / 2.0 + PIPE_GAP / 2.0;
            } else {
                yTop = LOW_GAP_CENTRE - pipeImage.getHeight() / 2.0 - PIPE_GAP / 2.0;
                yBottom = LOW_GAP_CENTRE + pipeImage.getHeight() / 2.0 + PIPE_GAP / 2.0;
            }
        } else {
            // use level 1 pipe spawning logic (random bounded y coordinate)
        }
        hitboxTop = pipeImage.getBoundingBoxAt(new Point(xTop, yTop));
        hitboxBottom = pipeImage.getBoundingBoxAt(new Point(xBottom, yBottom));
    }
    public void update() {
        // move the pipes towards the left border of the screen
        xTop -= stepSize;
        xBottom -= stepSize;

        // draw the top and bottom pipes
        pipeImage.draw(xTop, yTop);
        pipeImage.draw(xBottom, yBottom, drawOptions.setRotation(Math.PI));

        // move the pipe hitboxes to keep them aligned with the pipes
        hitboxTop.moveTo(new Point(xTop - pipeImage.getWidth() / 2.0, yTop - pipeImage.getHeight() / 2.0));
        hitboxBottom.moveTo(new Point(xBottom - pipeImage.getWidth() / 2.0,
                                      yBottom - pipeImage.getHeight() / 2.0));

        // visualise the pipe hitboxes

        // Drawing.drawRectangle(new Point(xTop - pipeImage.getWidth() / 2.0, yTop - pipeImage.getHeight() / 2.0),
        //         pipeImage.getWidth(), pipeImage.getHeight(), Colour.WHITE);
        // Drawing.drawRectangle(new Point(xBottom - pipeImage.getWidth() / 2.0, yBottom - pipeImage.getHeight() / 2.0),
        //         pipeImage.getWidth(), pipeImage.getHeight(), Colour.WHITE);
    }
}
