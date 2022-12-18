package snake.game;

import tengine.world.GridSquare;

import java.awt.*;
import java.util.Random;

/**
 * Represents a 2D grid of squares like a cartesian coordinate system, with the origin in the top
 * left corner. The size of each square is fixed to 16 pixels.
 *
 * @author Tessa Power
 * @see GridSquare
 */
public record Grid(Point origin, int numRows, int numCols) {
    private static final Random RANDOM = new Random();
    public static final int TILE_SIZE = 16;

    /**
     * Maps the given <code>GridSquare</code> to a physical pixel coordinate on the screen relative
     * to the origin of this <code>Grid</code>.
     *
     * @see GridSquare
     */
    public Point positionForSquare(GridSquare gridSquare) {
        Point point = new Point(gridSquare.col() * TILE_SIZE, gridSquare.row() * TILE_SIZE);
        point.translate(origin.x, origin.y);

        return point;
    }

    /**
     * A randomly chosen <code>GridSquare</code> in this <code>Grid</code>.
     */
    public GridSquare randomGridSquare() {
        return new GridSquare(RANDOM.nextInt(0, numRows), RANDOM.nextInt(0, numCols));
    }

    /**
     * Whether the given <code>GridSquare</code> is within the bounds of this <code>Grid</code>.
     */
    public boolean contains(GridSquare gridSquare) {
        return gridSquare.col() >= 0 && gridSquare.col() < numCols
                && gridSquare.row() >= 0 && gridSquare.row() < numRows;
    }
}
