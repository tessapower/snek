package tengine.physics.collisions.shapes;

import java.awt.*;

public interface CollisionShape {
    Dimension dimension();
    Point origin();
    void setOrigin(Point origin);
    boolean contains(Point point);
    Point center();
    boolean equals(Object o);
    int hashCode();
}
