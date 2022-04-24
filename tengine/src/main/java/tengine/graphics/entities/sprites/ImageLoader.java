package tengine.graphics.entities.sprites;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

class ImageLoader {
    static Image loadImage(InputStream is) {
        try {
            return ImageIO.read(is);
        } catch (IOException var2) {
            System.err.println("Error: could not load image: " + is);
            System.exit(1);
            return null;
        }
    }
}
