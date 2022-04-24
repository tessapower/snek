package tengine.physics.collisions.shapes;

import java.awt.*;
import java.util.Objects;

public class CollisionRect implements CollisionShape {
    protected Point origin;
    protected Dimension dimension;

    public CollisionRect(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
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

    @Override
    public boolean contains(Point point) {
        return point.x >= origin.x
                && point.y >= origin.y
                && point.x < origin.x + dimension.width
                && point.y < origin.y + dimension.height;
    }

    @Override
    public Point center() {
        return new Point((origin.x + dimension.width / 2), (origin.y + dimension.height / 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollisionRect other)) return false;
        return origin == other.origin
                && dimension == other.dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, dimension);
    }
}
