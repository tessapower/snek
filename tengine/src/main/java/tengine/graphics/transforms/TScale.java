package tengine.graphics.transforms;

import java.util.Objects;

public class TScale {
    public double xScaleFactor;
    public double yScaleFactor;

    public TScale(double xScaleFactor, double yScaleFactor) {
        this.xScaleFactor = xScaleFactor;
        this.yScaleFactor = yScaleFactor;
    }

    public static TScale identity() {
        return new TScale(1, 1);
    }

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
