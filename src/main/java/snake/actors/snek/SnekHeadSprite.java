package snake.actors.snek;

import snake.assets.Colors;
import snake.player.PlayerNumber;
import snake.screens.gameplay.GameWorld;
import snake.utils.ResourceLoader;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

// Package private
class SnekHeadSprite extends TGraphicCompound {
    private static final String SNEK_HEAD_P1 = "snek-head-p1.png";
    private static final String SNEK_HEAD_P2 = "snek-head-p2.png";

    Direction direction;
    GridSquare gridSquare;
    TGraphicCompound rotationContainer;

    public SnekHeadSprite(Dimension dimension, Direction initialDirection, Player playerNumber) {
        super(dimension);
        direction = initialDirection;

        rotationContainer = new TGraphicCompound(dimension);
        rotationContainer.setOrigin(new Point(dimension.width / 2, dimension.height / 2));

        Sprite sprite = new Sprite(
                (playerNumber == Player.PLAYER_ONE ? ResourceLoader.load(SNEK_HEAD_P1) : ResourceLoader.load(SNEK_HEAD_P2)),
                dimension
        );

        TRect tongue = new TRect(new Dimension(4, 4));
        tongue.isFilled = true;
        tongue.fillColor = Colors.SNEK_RED;

        sprite.setOrigin(new Point(-dimension.width / 2, -dimension.height / 2));
        // We want this to extend outside the snake head sprite
        tongue.setOrigin(new Point(-dimension.width / 2 + 6, -dimension.height / 2 - 4));
        rotationContainer.add(sprite);
        rotationContainer.add(tongue);

        add(rotationContainer);

        gridSquare = null;
    }

    @Override
    public void update(double dtMillis) {
        super.update(dtMillis);
        switch(direction) {
            case UP -> rotationContainer.setRotation(0);
            case RIGHT -> rotationContainer.setRotation(90);
            case DOWN -> rotationContainer.setRotation(180);
            case LEFT -> rotationContainer.setRotation(270);
        }
    }

    void setGridSquare(GridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }
}
