package tengine.graphics.transforms;

import java.util.Objects;

public class TTranslation {
    public int dx;
    public int dy;

    public TTranslation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static TTranslation identity() {
        return new TTranslation(0, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TTranslation other)) return false;

        return this.dx == other.dx && this.dy == other.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }
}
