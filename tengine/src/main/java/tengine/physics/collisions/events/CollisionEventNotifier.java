package tengine.physics.collisions.events;

@FunctionalInterface
public interface CollisionEventNotifier {
    void notifyCollision(CollisionEvent event);
}
