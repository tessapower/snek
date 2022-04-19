package apple;


import graphics.context.GraphicsCtx;
import graphics.graphicsObjects.sprites.Sprite;
import screens.play.Grid;

import java.awt.*;

public class AppleSprite extends Sprite {
    private static final String GOOD_APPLE = "src/main/resources/apple-good.png";
    private static final String BAD_APPLE = "src/main/resources/apple-bad.png";
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
