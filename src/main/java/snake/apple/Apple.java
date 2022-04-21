package snake.apple;

import snake.screens.play.SnakeWorld;
import tengine.Actor;
import tengine.world.GridSquare;

import java.awt.*;

public class Apple extends Actor {
    private final SnakeWorld world;
    private GridSquare gridSquare;
    private final AppleType appleType;

    public static Apple spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.CROMCHY);
        world.add(apple);

        return apple;
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }

    public AppleType appleType() {
        return appleType;
    }

    private Apple(SnakeWorld world, GridSquare gridSquare, AppleType appleType) {
        this.world = world;
        this.appleType = appleType;
        switch(appleType) {
            case CROMCHY -> graphicObject = AppleSprite.goodApple();
            case YUCK -> graphicObject = AppleSprite.badApple();
        }

        setGridSquare(gridSquare);
    }

    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        Point newOrigin = world.grid().positionForSquare(this.gridSquare);
        newOrigin.x += 1;
        newOrigin.y += 1;

        setOrigin(newOrigin);
    }
}
