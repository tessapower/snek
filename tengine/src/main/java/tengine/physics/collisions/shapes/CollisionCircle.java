package tengine.physics.collisions.shapes;

import java.awt.*;

public class CollisionCircle implements CollisionShape {
    protected Point origin;
    protected final Dimension dimension;

    public CollisionCircle(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }

    public int radius() {
        return dimension.width / 2;
    }

    public int x() {
       return origin.x;
    }

    public int y() {
        return origin.y;
    }

    @Override
    public Dimension dimension() {
        return dimension;
    }

    @Override
    public Point origin() {
        return origin;
    }

    @Override
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public boolean contains(Point point) {
        return center().distance(point) >= radius();
    }

    @Override
    public Point center() {
        return new Point((origin.x + radius()), (origin.y + radius()));
    }
}
