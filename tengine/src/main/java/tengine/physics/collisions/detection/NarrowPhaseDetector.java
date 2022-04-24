package tengine.physics.collisions.detection;

import tengine.physics.PhysicsEntity;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.physics.collisions.shapes.CollisionCircle;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.Vector;

import java.awt.*;

public class NarrowPhaseDetector {
    // Pre-conditions:
    //   p1: static = false, hasCollisions = true
    //   p2: static = true, hasCollisions = true
    public static CollisionEvent detect(PhysicsEntity movingObj, PhysicsEntity staticObj) {
        // TODO: find the closest corner of moving object to the center of static object
        Point closestCorner = closestPointToCenter(movingObj.collisionShape, staticObj.collisionShape);

        // TODO: find impact side = nearest edge of static object to closest corner
        Point impactSide = new Point(1, 0);

        // TODO: find scale
        Vector velocityUnitVector = new Vector(movingObj.velocity.dx(), movingObj.velocity.dy()).normalize();

        // TODO: find impact vector
        Vector impactVector = new Vector();

        return new CollisionEvent(movingObj, staticObj, impactVector, impactSide);
    }

    /**
     * Finds the corner of <code>c1</code> that is the closest to the center of <code>c2</code>
     *
     * @return The corner of <code>c1</code> that is closest to the center of <code>c2</code> as a point.
     */
    private static Point closestPointToCenter(CollisionShape c1, CollisionShape c2) {
        Point c2Center = c2.center();

        int c1Width = c1.dimension().width;
        int c1Height = c1.dimension().height;
        Point c1TopLeft = c1.origin();
        Point c1TopRight = new Point(c1TopLeft.x + c1Width, c1TopLeft.y);
        Point c1BottomLeft = new Point(c1TopLeft.x, c1TopLeft.y + c1Height);
        Point c1BottomRight = new Point(c1TopRight.x, c1BottomLeft.y);

        // figure out shortest distance

        return new Point();
    }

    // Generic vs. Generic
    private static Vector impactVector(CollisionShape a, CollisionShape b) {
        String aClass = a.getClass().getSimpleName();
        String bClass = b.getClass().getSimpleName();

        switch(aClass) {
            case "CollisionRect": {
                switch(bClass) {
                    case "CollisionRect", "CollisionShape": return impactVector((CollisionRect)a, (CollisionRect)b);
                    case "CollisionCircle": return impactVector((CollisionRect) a, (CollisionCircle)b);
                }
            }

            case "CollisionCircle":
                switch(bClass) {
                    case "CollisionCircle": return impactVector((CollisionCircle)a , (CollisionCircle)b);
                    case "CollisionRect", "CollisionShape": return impactVector((CollisionRect) b, (CollisionCircle) a);
                }

            case "CollisionShape": {
                switch(bClass) {
                    case "CollisionShape", "CollisionRect": return impactVector((CollisionRect)a, (CollisionRect)b);
                    case "CollisionCircle": return impactVector((CollisionRect) a, (CollisionCircle) b);
                }
            }
        }

        return null;
    }

    // Rect vs. Rect
    private static Vector impactVector(CollisionRect a, CollisionRect b) {
        // TODO: find impact vector

        return null;
    }
//
//    // Rect vs. Circle
//    private static Vector impactVector(CollisionRect a, CollisionCircle b) {
//
//        return null;
//    }
//
//    // Circle vs. Circle
//    private static Vector impactVector(CollisionCircle a, CollisionCircle b) {
//        // check if distance between radii is less than a.radius + b.radius
//        if (Math.pow(a.radius() + b.radius(), 2) >= Math.pow(b.x() - a.x(), 2) + Math.pow(b.y() - a.y(), 2)) {
//            return null;
//        }
//
//        return null;
//    }
}
