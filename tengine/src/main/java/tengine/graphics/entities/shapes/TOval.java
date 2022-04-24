package tengine.graphics.entities.shapes;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

public class TOval extends TShape {
    public TOval() {
        this(new Dimension());
    }

    public TOval(Dimension dimension) {
        super(dimension);
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledCircle(dimension, fillColor);
        } else {
            ctx.drawCircle(dimension, outlineColor);
        }
    }
}
