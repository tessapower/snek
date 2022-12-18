package snake.actors.apple;

import snake.assets.AppleSprite;
import snake.game.GameWorld;
import tengine.Actor;
import tengine.world.GridSquare;

import java.awt.*;

/**
 * An <code>Apple</code> that <code>snek</code> has to eat to gain points. Could be nice and
 * cromchy, or sour and mushy! Spawn different types of <code>Apple</code>s in the
 * <code>GameWorld</code> using the static factory methods <code>spawnGoodApple</code> and
 * <code>spawnBadApple</code>.
 *
 * @author Tessa Power
 * @see AppleType
 * @see AppleSprite
 * @see Apple#spawnGoodApple(GameWorld, TGridSquare)
 * @see Apple#spawnBadApple(GameWorld, TGridSquare)
 */
public class Apple extends Actor {
    private final GameWorld world;
    private GridSquare gridSquare;
    private final AppleType appleType;

    /**
     * Spawns a delicious <code>Apple</code> at the given <code>GridSquare</code> in the given
     * <code>GameWorld</code>.
     *
     * @return The new cromchy <code>Apple</code> spawned.
     * @see GameWorld
     * @see GridSquare
     */
    public static Apple spawnGoodApple(GameWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.CROMCHY);
        world.add(apple);

        return apple;
    }

    /**
     * Spawns a bad poisonous <code>Apple</code> at the given <code>GridSquare</code> in the given
     * <code>GameWorld</code>.
     *
     * @return The new poisonous <code>Apple</code> spawned.
     * @see GameWorld
     * @see GridSquare
     */
    public static Apple spawnBadApple(GameWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.YUCK);
        world.add(apple);

        return apple;
    }

    /**
     * The <code>GridSquare</code> where this <code>Apple</code> is located.
     *
     * @see GridSquare
     */
    public GridSquare gridSquare() {
        return gridSquare;
    }

    /**
     * The type of this <code>Apple</code>.
     *
     * @see AppleType
     */
    public AppleType appleType() {
        return appleType;
    }

    /**
     * Private constructor for an <code>Apple</code>. Adds itself to the given
     * <code>GameWorld</code> at the given <code>GridSquare</code> location. Can be of any
     * valid <code>AppleType</code>.
     *
     * @see GameWorld
     * @see GridSquare
     * @see AppleType
     */
    private Apple(GameWorld world, GridSquare gridSquare, AppleType appleType) {
        this.world = world;
        this.appleType = appleType;
        switch(appleType) {
            case CROMCHY -> graphicEntity = AppleSprite.goodApple();
            case YUCK -> graphicEntity = AppleSprite.badApple();
        }

        setGridSquare(gridSquare);
    }

    /**
     * Set the <code>GridSquare</code> for this <code>Apple</code>. Doesn't usually change
     * throughout the lifetime of this <code>Apple</code>, so this method is private.
     *
     * @see GridSquare
     */
    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;

        // Inset the Apple's origin slightly so the graphic is centered nicely
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);
        newOrigin.x += 1;
        newOrigin.y += 1;
        setOrigin(newOrigin);
    }
}
