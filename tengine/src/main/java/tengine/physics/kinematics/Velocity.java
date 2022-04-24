package tengine.physics.kinematics;

public record Velocity(int dx, int dy) {
    public Velocity() {
        this(0, 0);
    }
}
