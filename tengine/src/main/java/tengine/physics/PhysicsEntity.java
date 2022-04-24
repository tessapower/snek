package tengine.physics;

import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.Velocity;

import java.util.Objects;

public class PhysicsEntity {
    public PhysicsEngine system;
    public Velocity velocity;
    public boolean isStatic;
    public boolean hasCollisions;
    // TODO: Generalise to use a CollisionShape
    public CollisionRect collisionShape;

    public PhysicsEntity() {
        this(new Velocity(), true, null, false);
    }

    public PhysicsEntity(Velocity velocity, boolean isStatic,
                         CollisionRect collisionShape, boolean hasCollisions) {
        this.velocity = velocity;
        this.isStatic = isStatic;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }

    void setSystem(PhysicsEngine system) {
        this.system = system;
    }

    public void removeFromSystem() {
        // TODO: implement this when finishing PhysicsEngine
        throw new RuntimeException("implement me!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PhysicsEntity other))
            return false;
        return isStatic == other.isStatic
                && hasCollisions == other.hasCollisions
                && velocity.equals(other.velocity)
                && collisionShape == other.collisionShape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocity, isStatic, hasCollisions, collisionShape);
    }
}
