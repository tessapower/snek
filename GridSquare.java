import java.util.Objects;

public record GridSquare(int row, int col) {
    public String toString() {
        return "[" + col + ", " + row + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GridSquare that = (GridSquare) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
