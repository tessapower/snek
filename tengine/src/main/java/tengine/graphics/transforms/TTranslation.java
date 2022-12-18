package tengine.graphics.transforms;

import java.util.Objects;

/**
 * Represents a translation transform as a delta from the current origin (dx, dy).
 *
 * @author Tessa Power
 * @see TRotation
 * @see TScale
 */
public class TTranslation {
    public int dx;
    public int dy;

    /**
     * Constructs a new <code>TTranslation</code> in the form (dx, dy).
     */
    public TTranslation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Represents the identity translation of (0, 0).
     */
    public static TTranslation identity() {
        return new TTranslation(0, 0);
    }

    /**
     * Compares two <code>TTranslations</code> to see if they are equal.
     */
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
