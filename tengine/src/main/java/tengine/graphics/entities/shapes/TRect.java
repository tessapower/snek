package tengine.graphics.entities.shapes;

import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.entities.TGraphicObject;

import java.awt.*;

/**
 * A graphical representation of a Rectangle.
 *
 * @author Tessa Power
 * @see TShape
 * @see TGraphicObject
 */
public class TRect extends TShape {
    public TRect() {
        this(new Dimension());
    }

    public TRect(Dimension dimension) {
        super(dimension);
    }

    /**
     * Draw this <code>TRect</code> using the given <code>TGraphicsCtx</code>.
     */
    @Override
    protected void draw(TGraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledRect(dimension, fillColor);
        } else {
            ctx.drawRect(dimension, outlineColor);
        }
    }
}