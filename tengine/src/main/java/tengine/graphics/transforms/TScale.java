package tengine.graphics.transforms;

import java.util.Objects;

/**
 * Represents a scaling transform.
 *
 * @author Tessa Power
 * @see TTranslation
 * @see TRotation
 */
public class TScale {
    public double xScaleFactor;
    public double yScaleFactor;

    /**
     * Constructs a scaling transform.
     */
    public TScale(double xScaleFactor, double yScaleFactor) {
        this.xScaleFactor = xScaleFactor;
        this.yScaleFactor = yScaleFactor;
    }

    /**
     * Represents the identity scaling of 1.
     */
    public static TScale identity() {
        return new TScale(1, 1);
    }

    /**
     * Compares two <code>TScale</code>s to see if they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TScale other)) return false;

        return this.xScaleFactor == other.xScaleFactor && this.yScaleFactor == other.yScaleFactor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xScaleFactor, yScaleFactor);
    }
}
