import java.awt.*;
import java.util.Random;

public class Grid {
    private static final Random RANDOM = new Random();
    public static final int TILE_SIZE = 10;
    private final int numRows;
    private final int numCols;

    public Grid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int numRows() {
        return numRows;
    }

    public int numCols() {
        return numCols;
    }

    public GridSquare squareForPosition(Point point) {
        return new GridSquare(point.y / TILE_SIZE, point.x / TILE_SIZE);
    }

    public Point positionForSquare(GridSquare gridSquare) {
        return new Point(gridSquare.col() * TILE_SIZE, gridSquare.row() * TILE_SIZE);
    }

    public GridSquare randomGridSquare() {
        return new GridSquare(RANDOM.nextInt(0, numCols), RANDOM.nextInt(0, numRows));
    }
}
