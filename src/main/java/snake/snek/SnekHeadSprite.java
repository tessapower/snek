package snake.snek;

import snake.Colors;
import snake.screens.play.SnakeWorld;
import snake.utils.ResourceLoader;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

// Package private
class SnekHeadSprite extends TGraphicCompound {
    private static final String SNEK_HEAD = "snek-head-p1.png";

    Direction direction;
    GridSquare gridSquare;
    TGraphicCompound rotationContainer;

    public SnekHeadSprite(Dimension dimension, Direction initialDirection) {
        super(dimension);
        direction = initialDirection;

        rotationContainer = new TGraphicCompound(dimension);
        rotationContainer.setOrigin(new Point(dimension.width / 2, dimension.height / 2));

        Sprite sprite = new Sprite(ResourceLoader.load(SNEK_HEAD), dimension);

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

    void setGridSquare(GridSquare gridSquare, SnakeWorld world) {
        this.gridSquare = gridSquare;
        // set origin to world.origin() + position for square
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }
}
