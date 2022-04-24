package tengine.graphics.entities.sprites;

import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.entities.TGraphicObject;

import java.awt.*;
import java.io.InputStream;

public class Sprite extends TGraphicObject {
    protected Image image;

    public Sprite(InputStream is, Dimension dimension) {
        super(dimension);
        image = ImageLoader.loadImage(is);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, dimension);
    }
}
