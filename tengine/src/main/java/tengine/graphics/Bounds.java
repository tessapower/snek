package tengine.graphics;

import java.awt.*;

public class Bounds {
    protected Point origin;
    protected java.awt.Dimension dimension;

    public Bounds() {
        this(new Point(0, 0), new java.awt.Dimension(0, 0));
    }

    public Bounds(Point origin) {
        this(origin, new java.awt.Dimension(0, 0));
    }

    public Bounds(java.awt.Dimension dimension) {
        this(new Point(0, 0), dimension);
    }

    public Bounds(Point origin, java.awt.Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }
}
