import actors.Actor;
import graphics.TRect;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snek extends Actor {
    private final Color snekGreen = new Color(119, 167, 117);
    private final Color snekRed = new Color(230, 82, 83);
    private static final Color headColor = new Color(119, 167, 117);
    private final Dimension dimension;
    private static final double speed = 1;
    private final World world;

    private Direction pendingDirection;
    private Direction direction;
    private GridSquare gridSquare;
    private TRect head;

    public Snek(World world, Dimension dimension, Direction initialDirection) {
        this.world = world;
        this.dimension = dimension;
        direction = initialDirection;

        origin = new Point();
        gridSquare = new GridSquare(0, 0);
        pendingDirection = null;

        head = new TRect(dimension);
        head.isFilled = true;
        head.fillColor = snekGreen;
        sprite = head;
        sprite.setOrigin(origin);
    }

    public void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    public void update(double dt) {
        if (!finishedAnimating()) {
            continueAnimation(dt);
        } else {
            // TODO: Remove this :)
            toggleColor();
            direction = (pendingDirection == null) ? direction : pendingDirection;
            pendingDirection = null;
            advanceToNextGridSquare();
        }
    }

    private boolean finishedAnimating() {
        boolean hasFinished = false;

        switch (direction) {
            case UP, LEFT -> {
                Point bottomRight = new Point(origin.x + dimension.width, origin.y + dimension.height);
                hasFinished = gridSquare.equals(world.grid().squareForPosition(bottomRight));
            }
            case DOWN, RIGHT -> {
                Point topLeft = new Point(origin);
                hasFinished = gridSquare.equals(world.grid().squareForPosition(topLeft));
            }
        }

        return hasFinished;
    }

    private void toggleColor() {
        head.fillColor = (head.fillColor == snekGreen) ? snekRed : snekGreen;
    }

    private void continueAnimation(double dt) {
        Point newOrigin = origin;
        switch(direction) {
            case UP -> newOrigin.translate(0, (int)(-speed));
            case DOWN -> newOrigin.translate(0, (int)(speed));
            case LEFT -> newOrigin.translate((int)(-speed), 0);
            case RIGHT -> newOrigin.translate((int)(speed), 0);
        }

        setOrigin(newOrigin);
    }

    private void advanceToNextGridSquare() {
        switch(direction) {
            case UP -> gridSquare = new GridSquare(gridSquare.row() - 1, gridSquare.col());
            case DOWN -> gridSquare = new GridSquare(gridSquare.row() + 1, gridSquare.col());
            case LEFT -> gridSquare = new GridSquare(gridSquare.row(), gridSquare.col() - 1);
            case RIGHT -> gridSquare = new GridSquare(gridSquare.row(), gridSquare.col() + 1);
        }
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
