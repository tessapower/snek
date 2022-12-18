package tengine.graphics.transforms;

import java.awt.*;
import java.util.Objects;

/**
 * Represents a rotation transform in degrees.
 *
 * @author Tessa Power
 * @see TTranslation
 * @see TScale
 */
public class TRotation {
    public double thetaDegrees;
    public Point origin;

    /**
     * Constructs a rotation of <code>thetaDegrees</code> around <code>rotationOrigin</code>.
     */
    public TRotation(double thetaDegrees, Point rotationOrigin) {
        // Adjust rotations that are negative and greater than 360 to be within the range 0 - 359
        this.thetaDegrees = (thetaDegrees < 0) ? thetaDegrees + 360 : thetaDegrees;
        this.thetaDegrees %= 360;
        this.origin = rotationOrigin;
    }

    /**
     * The identity rotation of 0 degrees.
     */
    public static TRotation identity() {
        return new TRotation(0, new Point(0, 0));
    }

    /**
     * Compares two <code>TRotation</code>s to see if they are equal. Rotations that are negative
     *  or greater than 360 are first adjusted to be within 0 - 359.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TRotation other)) return false;

        return this.thetaDegrees == other.thetaDegrees && origin == other.origin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thetaDegrees, origin);
    }
}
