package snake.actors.snek;

import snake.assets.AssetLoader;
import snake.assets.Colors;
import snake.game.GameWorld;
import snake.player.PlayerNumber;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

// Package private
public class SnekHead extends TGraphicCompound {
    private static final String SNEK_HEAD_P1 = "snek-head-p1.png";
    private static final String SNEK_HEAD_P2 = "snek-head-p2.png";

    GridSquare gridSquare;

    public SnekHead(Dimension dimension, PlayerNumber playerNumber) {
        super(dimension);

        Sprite sprite = new Sprite(
                AssetLoader.load(
                    switch(playerNumber) {
                        case PLAYER_ONE -> SNEK_HEAD_P1;
                        case PLAYER_TWO -> SNEK_HEAD_P2;
                    }
                ),
                dimension
        );

        TRect tongue = new TRect(new Dimension(4, 4));
        tongue.isFilled = true;
        tongue.fillColor = Colors.SNEK_RED;

        // We want this to extend outside the snake head sprite
        tongue.setOrigin(new Point(6, -4));
        add(sprite);
        add(tongue);

        gridSquare = null;
    }

    void setGridSquare(GridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }
}
