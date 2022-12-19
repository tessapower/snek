package tengine.graphics.components.sprites;

import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.components.TGraphicObject;

import java.awt.*;
import java.io.InputStream;

/**
 * Represents a two-dimensional image that can be drawn to the screen.
 *
 * @author Tessa Power
 * @see TAnimatedSprite
 */
public class TSprite extends TGraphicObject {
    protected Image image;

    /**
     * Constructs a <code>TSprite</code> from the given <code>InputStream</code> with the given
     * <code>Dimension</code>.
     */
    public TSprite(InputStream is, Dimension dimension) {
        super(dimension);
        image = TImageLoader.loadImage(is);
    }

    /**
     * Draw this <code>TSprite</code> to the screen using the given <code>TGraphicsCtx</code>.
     */
    @Override
    protected void draw(TGraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, dimension);
    }
}
