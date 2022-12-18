package snake.assets;

import snake.actors.apple.Apple;
import snake.game.Grid;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.entities.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

/**
 * The <code>Sprite</code> for an <code>Apple</code>. Use the static factory methods to construct an
 * <code>AppleSprite</code> for a good or bad apple.
 *
 * @author Tessa Power
 * @see Apple
 * @see AppleSprite#goodApple()
 * @see AppleSprite#badApple()
 */
public class AppleSprite extends Sprite {
    private static final String GOOD_APPLE = "apple-good.png";
    private static final String BAD_APPLE = "apple-bad.png";
    private static final Dimension DIMENSION = new Dimension(Grid.SQUARE_SIZE, Grid.SQUARE_SIZE);

    /**
     * The private constructor for this class.
     */
    private AppleSprite(InputStream is, Dimension dimension) {
        super(is, dimension);
    }

    /**
     * A <code>Sprite</code> for a poisonous <code>Apple</code>.
     *
     * @see snake.actors.apple.Apple
     * @see Sprite
     */
    public static AppleSprite badApple() {
        return new AppleSprite(AssetLoader.load(BAD_APPLE), DIMENSION);
    }

    /**
     * A <code>Sprite</code> for a delicious <code>Apple</code>.
     *
     * @see snake.actors.apple.Apple
     * @see Sprite
     */
    public static AppleSprite goodApple() {
        return new AppleSprite(AssetLoader.load(GOOD_APPLE), DIMENSION);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, DIMENSION);
    }
}
