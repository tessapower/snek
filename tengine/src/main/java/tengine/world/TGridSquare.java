package tengine.world;

import java.util.Objects;

/**
 * A single square in a 2D grid of squares, well suited for tile-based or board games.
 *
 * @author Tessa Power
 * @see TWorld
 */
public record TGridSquare(int row, int col) {
    /**
     * Returns a string representation of this <code>TGridSquare</code>.
     */
    public String toString() {
        return "row: " + row + ", col: " + col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TGridSquare other)) return false;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
