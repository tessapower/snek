package snake.game;

import tengine.world.GridSquare;

import java.awt.*;
import java.util.Random;

public record Grid(Point origin, int numRows, int numCols) {
    private static final Random RANDOM = new Random();
    public static final int TILE_SIZE = 16;

    public Point positionForSquare(GridSquare gridSquare) {
        Point point = new Point(gridSquare.col() * TILE_SIZE, gridSquare.row() * TILE_SIZE);
        point.translate(origin.x, origin.y);

        return point;
    }

    public GridSquare randomGridSquare() {
        return new GridSquare(RANDOM.nextInt(0, numRows), RANDOM.nextInt(0, numCols));
    }

    public boolean contains(GridSquare gridSquare) {
        return gridSquare.col() >= 0 && gridSquare.col() < numCols
                && gridSquare.row() >= 0 && gridSquare.row() < numRows;
    }
}
