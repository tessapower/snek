package tengine.world;

import java.util.Objects;

public record GridSquare(int row, int col) {
    public String toString() {
        return "row: " + row + ", col: " + col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridSquare other)) return false;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
