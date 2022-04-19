package snek;

import graphics.graphicsObjects.sprites.Sprite;
import screens.play.SnakeWorld;
import world.GridSquare;

import java.awt.*;

class SnekTailSprite extends Sprite {
    private static final String SNEK_TAIL_FILE = "src/main/resources/snek-body-p1.png";
    GridSquare gridSquare;

    public SnekTailSprite(Dimension dimension) {
        super(SNEK_TAIL_FILE, dimension);

        gridSquare = null;
    }

    void setGridSquare(GridSquare gridSquare, SnakeWorld world) {
        this.gridSquare = gridSquare;
        // set origin to world.origin() + position for square
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }
}
