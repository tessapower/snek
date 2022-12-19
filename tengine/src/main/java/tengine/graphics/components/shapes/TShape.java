package tengine.graphics.components.shapes;

import tengine.graphics.components.TGraphicObject;

import java.awt.*;

/**
 * A graphical representation of a generic polygonal shape.
 *
 * @author Tessa Power
 * @see TGraphicObject
 */
abstract public class TShape extends TGraphicObject {
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    /**
     * <code>TShape</code> is an abstract class and should not be instantiated directly.
     */
    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }
}
