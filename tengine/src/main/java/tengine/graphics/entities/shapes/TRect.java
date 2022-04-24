package tengine.graphics.entities.shapes;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

public class TRect extends TShape {
    public TRect() {
        this(new Dimension());
    }

    public TRect(Dimension dimension) {
        super(dimension);
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledRect(dimension, fillColor);
        } else {
            ctx.drawRect(dimension, outlineColor);
        }
    }
}
