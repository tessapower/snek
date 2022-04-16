import actors.Actor;
import graphics.TRect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snek extends Actor implements KeyListener {
    private static final Color headColor = new Color(119, 167, 117);
    private final Dimension dimension;
    private final double speed;
    private final World world;

    private Direction pendingDirection;
    private Direction direction;
    private GridSquare gridSquare;
    private TRect head;

    public Snek(World world, Dimension dimension, Direction initialDirection) {
        this.world = world;
        this.dimension = dimension;
        origin = new Point();
        gridSquare = new GridSquare(0, 0);
        direction = initialDirection;

        pendingDirection = null;
        speed = 1;

        head = new TRect(dimension);
        head.isFilled = true;
        head.fillColor = headColor;
        sprite = head;
        sprite.setOrigin(origin);
    }

    public void setGridSquare(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForTile(this.gridSquare));
    }

    public void update(double dt) {
        if (!finishedAnimating()) {
            continueAnimation(dt);
        } else {
            direction = (pendingDirection == null) ? direction : pendingDirection;
            pendingDirection = null;
        }
    }

    private boolean finishedAnimating() {
        // ...
        return false;
    }

    private void continueAnimation(double dt) {
        Point newOrigin = origin;
        switch(direction) {
            case UP: {
                newOrigin.translate(0, (int)(-speed));
            }
            case DOWN: {
                newOrigin.translate(0, (int)(speed));
            }
            case LEFT: {
                newOrigin.translate((int)(-speed), 0);
            }
            case RIGHT: {
                newOrigin.translate((int)(speed), 0);
            }
        }

        setOrigin(newOrigin);
        System.out.println(origin.toString());
    }

    private void advanceToNextTile() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: setPendingDirection(Direction.UP);
            case KeyEvent.VK_DOWN: setPendingDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT: setPendingDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT: setPendingDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void setPendingDirection(Direction direction) {
        switch(direction) {
            case UP, DOWN: {
                if (this.direction != Direction.UP && this.direction != Direction.DOWN) {
                    pendingDirection = direction;
                }
            }
            case LEFT, RIGHT: {
                if (this.direction != Direction.UP && this.direction != Direction.DOWN) {
                    pendingDirection = direction;
                }
            }
        }
    }
}
