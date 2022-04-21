package snake.snek;

import snake.screens.play.Grid;
import snake.screens.play.GameWorld;
import tengine.Actor;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snek extends Actor {
    private final GameWorld world;
    private final Dimension dimension;

    private Direction pendingDirection;
    private Direction direction;
    private SnekHeadSprite head;
    private SnekTail tail;
    private boolean shouldGrowTail;

    public static Snek spawnAt(GameWorld world, GridSquare gridSquare) {
        Snek snek = new Snek(world, gridSquare, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        world.add(snek);

        return snek;
    }

    private Snek(GameWorld world, GridSquare gridSquare, Dimension dimension, Direction initialDirection) {
        this.world = world;
        this.dimension = dimension;

        direction = initialDirection;
        pendingDirection = null;

        graphicObject = initSprite(gridSquare, world);
        shouldGrowTail = false;
    }

    private TGraphicCompound initSprite(GridSquare gridSquare, GameWorld world) {
        // Head
        head = new SnekHeadSprite(dimension, direction);
        head.setGridSquare(gridSquare, world);

        // Tail
        tail = new SnekTail(dimension, new GridSquare(head.gridSquare.row(), head.gridSquare.col() - 1), world);

        // Sprite
        TGraphicCompound body = new TGraphicCompound(dimension);
        body.add(head);
        tail.tailPieces.forEach(body::add);

        return body;
    }

    public void update(double dtMillis) {
        direction = (pendingDirection == null) ? direction : pendingDirection;
        head.direction = direction;
        pendingDirection = null;
        if (shouldGrowTail) {
            // Grow the tail toward the head
            SnekTailSprite newTailPiece = tail.growToward(head.gridSquare, world);
            ((TGraphicCompound) graphicObject).add(newTailPiece);
            advanceHead();
            shouldGrowTail = false;
        } else {
            // Move the tail toward the head
            tail.moveToward(head.gridSquare, world);
            advanceHead();
        }
    }

    public boolean hasHitSelf() {
        for (var tailPiece : tail.tailPieces) {
            if (tailPiece.gridSquare.equals(head.gridSquare)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Mark the tail to grow by one on the next update. Should only be called <i>once</i>
     * per update cycle, multiple calls will still only grow the tail by one on the next draw.
     */
    public void growTail() {
        shouldGrowTail = true;
    }

    public int tailLength() {
        return tail.length();
    }

    public void increaseSpeed() {

    }

    public GridSquare gridSquare() {
        return head.gridSquare;
    }

    public boolean occupies(GridSquare gridSquare) {
        if (head.gridSquare.equals(gridSquare)) return true;

        for (var tailPiece : tail.tailPieces) {
            if (tailPiece.gridSquare.equals(gridSquare)) return true;
        }

        return false;
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> setPendingDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> setPendingDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> setPendingDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> setPendingDirection(Direction.RIGHT);
        }
    }

    private void advanceHead() {
        switch(direction) {
            case UP ->
                    head.setGridSquare(new GridSquare(head.gridSquare.row() - 1, head.gridSquare.col()), world);
            case DOWN ->
                    head.setGridSquare(new GridSquare(head.gridSquare.row() + 1, head.gridSquare.col()), world);
            case LEFT ->
                    head.setGridSquare(new GridSquare(head.gridSquare.row(), head.gridSquare.col() - 1), world);
            case RIGHT ->
                    head.setGridSquare(new GridSquare(head.gridSquare.row(), head.gridSquare.col() + 1), world);
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
}
