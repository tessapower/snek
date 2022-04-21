package snake.apple;

import snake.screens.play.Grid;
import snake.utils.ResourceLoader;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.graphicsObjects.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

class AppleSprite extends Sprite {
    private static final String GOOD_APPLE = "apple-good.png";
    private static final String BAD_APPLE = "apple-bad.png";
    private static final Dimension DIMENSION = new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE);

    public AppleSprite(InputStream is, Dimension dimension) {
        super(is, dimension);
    }

    public static AppleSprite badApple() {
        return new AppleSprite(ResourceLoader.load(BAD_APPLE), DIMENSION);
    }

    public static AppleSprite goodApple() {
        return new AppleSprite(ResourceLoader.load(GOOD_APPLE), DIMENSION);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, DIMENSION);
    }
}
