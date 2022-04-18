package apple;

import graphics.GraphicsCtx;
import graphics.sprites.Sprite;
import screens.Grid;

import java.awt.*;

public class AppleSprite extends Sprite {
    private static final String GOOD_APPLE = "res/apple-good.png";
    private static final String BAD_APPLE = "res/apple-bad.png";
    private static final Dimension DIMENSION = new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE);

    protected AppleSprite(String filename, Dimension dimension) {
        super(filename, dimension);
    }

    public static AppleSprite badApple() {
        return new AppleSprite(BAD_APPLE, DIMENSION);
    }

    public static AppleSprite goodApple() {
        return new AppleSprite(GOOD_APPLE, DIMENSION);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, DIMENSION);
    }
}
