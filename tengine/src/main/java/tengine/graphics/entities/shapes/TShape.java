package tengine.graphics.entities.shapes;

import tengine.graphics.entities.TGraphicObject;

import java.awt.*;

abstract public class TShape extends TGraphicObject {
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }
}
