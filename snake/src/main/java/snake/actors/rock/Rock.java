package snake.actors.rock;

import snake.assets.AssetLoader;
import snake.game.GameWorld;
import snake.game.Grid;
import tengine.TActor;
import tengine.graphics.components.sprites.TSprite;
import tengine.world.TGridSquare;

import java.awt.*;

/**
 * An obstacle that if <code>snek</code> crashes into will end the game. Use the
 * <code>spawnRockAt</code> factory method to spawn a new rock in the <code>GameWorld</code>.
 *
 * @author Tessa Power
 * @see Rock#spawnRockAt(GameWorld, TGridSquare)
 */
public class Rock extends TActor {
    private static final String ROCK = "rock.png";
    private static final Dimension DIMENSION = new Dimension(Grid.SQUARE_SIZE, Grid.SQUARE_SIZE);

    private final GameWorld world;
    private TGridSquare gridSquare;

    /**
     * Spawns a nasty <code>Rock</code> at the given <code>TGridSquare</code> in the given
     * <code>GameWorld</code>.
     *
     * @return The new <code>Rock</code> spawned.
     * @see GameWorld
     * @see TGridSquare
     */
    public static Rock spawnRockAt(GameWorld world, TGridSquare gridSquare) {
        Rock rock = new Rock(world, gridSquare);
        world.add(rock);

        return rock;
    }

    /**
     * The <code>TGridSquare</code> where this <code>Rock</code> is located.
     *
     * @see TGridSquare
     */
    public TGridSquare gridSquare() {
        return gridSquare;
    }

    /**
     * Private constructor for a <code>Rock</code>. Adds itself to the given
     * <code>GameWorld</code> at the given <code>TGridSquare</code> location.
     *
     * @see GameWorld
     * @see TGridSquare
     */
    private Rock(GameWorld world, TGridSquare gridSquare) {
        this.world = world;
        graphicEntity = new TSprite(AssetLoader.load(ROCK), DIMENSION);

        setGridSquare(gridSquare);
    }

    /**
     * Set the <code>TGridSquare</code> for this <code>Apple</code>. Doesn't usually change
     * throughout the lifetime of this <code>Rock</code>, so this method is private.
     *
     * @see TGridSquare
     */
    private void setGridSquare(TGridSquare gridSquare) {
        this.gridSquare = gridSquare;
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);

        setOrigin(newOrigin);
    }
}
