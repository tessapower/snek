package snake.actors.apple;

import snake.assets.AppleSprite;
import snake.game.GameWorld;
import tengine.Actor;
import tengine.world.GridSquare;

import java.awt.*;

public class Apple extends Actor {
    private final GameWorld world;
    private GridSquare gridSquare;
    private final AppleType appleType;

    public static Apple spawnGoodApple(GameWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.CROMCHY);
        world.add(apple);

        return apple;
    }

    public static Apple spawnBadApple(GameWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.YUCK);
        world.add(apple);

        return apple;
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }

    public AppleType appleType() {
        return appleType;
    }

    private Apple(GameWorld world, GridSquare gridSquare, AppleType appleType) {
        this.world = world;
        this.appleType = appleType;
        switch(appleType) {
            case CROMCHY -> graphicEntity = AppleSprite.goodApple();
            case YUCK -> graphicEntity = AppleSprite.badApple();
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