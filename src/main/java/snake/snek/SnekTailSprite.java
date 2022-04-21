package snake.snek;

import snake.screens.play.GameWorld;
import snake.utils.ResourceLoader;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

class SnekTailSprite extends Sprite {
    private static final String SNEK_TAIL_P1 = "snek-body-p1.png";
    private static final String SNEK_TAIL_P2 = "snek-body-p1.png";

    GridSquare gridSquare;

    private SnekTailSprite(Dimension dimension, String path) {
        super(ResourceLoader.load(path), dimension);

        gridSquare = null;
    }

   public static SnekTailSprite playerOneTailSprite(Dimension dimension) {
        return new SnekTailSprite(dimension, SNEK_TAIL_P1);
   }

   public static SnekTailSprite playerTwoTailSprite(Dimension dimension) {
        return new SnekTailSprite(dimension, SNEK_TAIL_P2);
   }

    void setGridSquare(GridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        // set origin to world.origin() + position for square
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }
}
