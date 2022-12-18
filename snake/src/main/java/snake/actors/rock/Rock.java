package snake.actors.rock;

import snake.assets.AssetLoader;
import snake.game.GameWorld;
import snake.game.Grid;
import tengine.graphics.entities.sprites.Sprite;
import tengine.world.GridSquare;
import tengine.TActor;

import java.awt.*;

/**
 * An obstacle that if <code>snek</code> crashes into will end the game. Use the
 * <code>spawnRockAt</code> factory method to spawn a new rock in the <code>GameWorld</code>.
 *
 * @author Tessa Power
 * @see Rock#spawnRockAt(GameWorld, GridSquare)
 */
public class Rock extends TActor {
    private static final String ROCK = "rock.png";
    private static final Dimension DIMENSION = new Dimension(Grid.SQUARE_SIZE, Grid.SQUARE_SIZE);

    private final GameWorld world;
    private GridSquare gridSquare;

    /**
     * Spawns a nasty <code>Rock</code> at the given <code>GridSquare</code> in the given
     * <code>GameWorld</code>.
     *
     * @return The new <code>Rock</code> spawned.
     * @see GameWorld
     * @see GridSquare
     */
    public static Rock spawnRockAt(GameWorld world, GridSquare gridSquare) {
        Rock rock = new Rock(world, gridSquare);
        world.add(rock);

        return rock;
    }

    /**
     * The <code>GridSquare</code> where this <code>Rock</code> is located.
     *
     * @see GridSquare
     */
    public GridSquare gridSquare() {
        return gridSquare;
    }

    /**
     * Private constructor for a <code>Rock</code>. Adds itself to the given
     * <code>GameWorld</code> at the given <code>GridSquare</code> location.
     *
     * @see GameWorld
     * @see GridSquare
     */
    private Rock(GameWorld world, GridSquare gridSquare) {
        this.world = world;
        graphicEntity = new Sprite(AssetLoader.load(ROCK), DIMENSION);

        setGridSquare(gridSquare);
    }

    /**
     * Set the <code>GridSquare</code> for this <code>Apple</code>. Doesn't usually change
     * throughout the lifetime of this <code>Rock</code>, so this method is private.
     *
     * @see GridSquare
     */
    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);

        setOrigin(newOrigin);
    }
}
