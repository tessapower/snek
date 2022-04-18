package apple;

import actors.Actor;
import actors.GridSquare;
import org.jetbrains.annotations.NotNull;
import screens.SnakeWorld;

import java.awt.*;

public class Apple extends Actor {
    private final SnakeWorld world;
    private GridSquare gridSquare;
    private final AppleType appleType;

    public static @NotNull
    Apple spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Apple apple = new Apple(world, gridSquare, AppleType.CROMCHY);
        world.add(apple);

        return apple;
    }

    public GridSquare gridSquare() {
        return gridSquare;
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

    public AppleType appleType() {
        return appleType;
    }
}
