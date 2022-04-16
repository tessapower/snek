import actors.Actor;
import graphics.TOval;
import graphics.TRect;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snek extends Actor {
    private final Color snekGreen = new Color(119, 167, 117);
    private final Color snekRed = new Color(230, 82, 83);
    private final World world;

    private Direction pendingDirection;
    private Direction direction;
    private GridSquare gridSquare;
    private final TOval head;

    public Snek(World world, Dimension dimension, Direction initialDirection) {
        this.world = world;
        direction = initialDirection;

        origin = new Point();
        gridSquare = new GridSquare(0, 0);
        pendingDirection = null;

        head = new TOval(dimension);
        head.isFilled = true;
        head.fillColor = snekRed;
        sprite = head;
        sprite.setOrigin(origin);
    }

    public void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    public void update(double dt) {
        // TODO: Remove this :)
        // toggleColor();
        direction = (pendingDirection == null) ? direction : pendingDirection;
        pendingDirection = null;
        advanceToNextGridSquare(dt);
    }

    private void toggleColor() {
        head.fillColor = (head.fillColor == snekGreen) ? snekRed : snekGreen;
    }

    private void advanceToNextGridSquare(double dt) {
        switch(direction) {
            case UP -> gridSquare = new GridSquare(gridSquare.row() - 1, gridSquare.col());
            case DOWN -> gridSquare = new GridSquare(gridSquare.row() + 1, gridSquare.col());
            case LEFT -> gridSquare = new GridSquare(gridSquare.row(), gridSquare.col() - 1);
            case RIGHT -> gridSquare = new GridSquare(gridSquare.row(), gridSquare.col() + 1);
        }

        setOrigin(world.grid().positionForSquare(gridSquare));
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
