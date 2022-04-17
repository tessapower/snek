import actors.Actor;
import graphics.TOval;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snek extends Actor {
    private final Color snekGreen = new Color(119, 167, 117);
    private final Color snekRed = new Color(230, 82, 83);

    private Direction pendingDirection;
    private Direction direction;
    private GridSquare gridSquare;
    private final TOval head;
    private final SnakeWorld world;

    public static @NotNull
    Snek spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Snek snek = new Snek(world, gridSquare, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        world.add(snek);

        return snek;
    }

    private Snek(SnakeWorld world, GridSquare gridSquare, Dimension dimension, Direction initialDirection) {
        this.world = world;

        direction = initialDirection;
        pendingDirection = null;

        head = new TOval(dimension);
        head.isFilled = true;
        head.fillColor = snekRed;
        sprite = head;

        setGridSquare(gridSquare);
    }

    public void update(double dt) {
        direction = (pendingDirection == null) ? direction : pendingDirection;
        pendingDirection = null;
        advanceToNextGridSquare(dt);
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }

    private void advanceToNextGridSquare(double dt) {
        switch(direction) {
            case UP ->    setGridSquare(new GridSquare(gridSquare.row() - 1, gridSquare.col())    );
            case DOWN ->  setGridSquare(new GridSquare(gridSquare.row() + 1, gridSquare.col())    );
            case LEFT ->  setGridSquare(new GridSquare(gridSquare.row(),     gridSquare.col() - 1));
            case RIGHT -> setGridSquare(new GridSquare(gridSquare.row(),     gridSquare.col() + 1));
        }
    }

    private void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        // set origin to world.origin() + position for square
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    private void setPendingDirection(Direction direction) {
        switch (direction) {
            case UP, DOWN -> {
                if (this.direction != Direction.UP && this.direction != Direction.DOWN) {
                    pendingDirection = direction;
                }
            }
            case LEFT, RIGHT -> {
                if (this.direction != Direction.LEFT && this.direction != Direction.RIGHT) {
                    pendingDirection = direction;
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> setPendingDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> setPendingDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> setPendingDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> setPendingDirection(Direction.RIGHT);
        }
    }
}
