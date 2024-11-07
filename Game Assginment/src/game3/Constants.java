package game3;
import java.awt.*;
import java.io.IOException;

import utilities.ImageManager;

public class Constants {
    public static final int FRAME_HEIGHT = 480;  //
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final int DELAY = 20;  // milliseconds
    public static final double DT = DELAY/1000.0;  // seconds
    public static Image ASTEROID1, MILKYWAY1;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            System.out.println("loaded images");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
