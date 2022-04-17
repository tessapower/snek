import actors.Actor;
import graphics.TOval;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Apple extends Actor {
    private static final Color COLOR_GOOD = new Color(111, 210, 110);
    // private static final Color COLOR_BAD = new Color(217, 197, 105);
    private final SnakeWorld world;
    private GridSquare gridSquare;

    public static @NotNull
    Apple spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), gridSquare, true);
        world.add(apple);

        return apple;
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }

    private Apple(SnakeWorld world, Dimension dimension, GridSquare gridSquare, boolean isGood) {
        this.world = world;

        TOval apple = new TOval(new Dimension(dimension.width - 2, dimension.height - 2));
        apple.isFilled = true;
        apple.fillColor = COLOR_GOOD;
        sprite = apple;

        setGridSquare(gridSquare);
    }

    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);
        newOrigin.x += 1;
        newOrigin.y += 1;

        setOrigin(newOrigin);
    }
}
