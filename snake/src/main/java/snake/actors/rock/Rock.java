package snake.actors.rock;

import snake.assets.AssetLoader;
import snake.game.GameWorld;
import snake.game.Grid;
import tengine.Actor;
import tengine.graphics.entities.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

public class Rock extends Actor {
    private static final String ROCK = "rock.png";
    private static final Dimension DIMENSION = new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE);

    private final GameWorld world;
    private GridSquare gridSquare;

    public static Rock spawnRockAt(GameWorld world, GridSquare gridSquare) {
        Rock rock = new Rock(world, gridSquare);
        world.add(rock);

        return rock;
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }

    private Rock(GameWorld world, GridSquare gridSquare) {
        this.world = world;
        graphicEntity = new Sprite(AssetLoader.load(ROCK), DIMENSION);

        setGridSquare(gridSquare);
    }

    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);

        setOrigin(newOrigin);
    }
}
