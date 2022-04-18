package snek;

import actors.Actor;
import actors.GridSquare;
import graphics.TGraphicCompound;
import graphics.TOval;
import screens.Grid;
import screens.SnakeWorld;

import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snek extends Actor {
    private static final Color SNEK_RED = new Color(230, 82, 83);
    private final SnakeWorld world;
    private final Dimension dimension;

    private Direction pendingDirection;
    private Direction direction;
    private SnekTail tail;
    private boolean shouldGrowTail;
    private SnekPiece head;

    public static @NotNull
    Snek spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Snek snek = new Snek(world, gridSquare, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        world.add(snek);

        return snek;
    }

    private Snek(SnakeWorld world, GridSquare gridSquare, Dimension dimension, Direction initialDirection) {
        this.world = world;
        this.dimension = dimension;

        direction = initialDirection;
        pendingDirection = null;

        graphicObject = initSprite(gridSquare, world);
        shouldGrowTail = false;
    }

    private TGraphicCompound initSprite(GridSquare gridSquare, SnakeWorld world) {
        // Head
        head = new SnekPiece(dimension, SNEK_RED);
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
        pendingDirection = null;
        if (shouldGrowTail) {
            // Grow the tail toward the head
            SnekPiece newTailPiece = tail.growToward(head.gridSquare, world);
            ((TGraphicCompound) graphicObject).add(newTailPiece);
            advanceHead();
            shouldGrowTail = false;
        } else {
            // Move the tail toward the head
            tail.moveToward(head.gridSquare, world);
            advanceHead();
        }
    }

    /**
     * Mark the tail to grow by one on the next update. Should only be called <i>once</i>
     * per update cycle, multiple calls will still only grow the tail by one on the next draw.
     */
    public void growTail() {
        shouldGrowTail = true;
    }

    public GridSquare gridSquare() {
        return head.gridSquare;
    }

    public boolean occupies(GridSquare gridSquare) {
        // TODO: Extend this to checking if the tail pieces, either inclusive or exclusive of the head
        return gridSquare == head.gridSquare;
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

    public void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> setPendingDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> setPendingDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> setPendingDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> setPendingDirection(Direction.RIGHT);
        }
    }

    static class SnekTail {
        private static final Color SNEK_GREEN = new Color(119, 167, 117);
        public static final int MAX_TAIL_LEN = 19;
        List<SnekPiece> tailPieces;

        public SnekTail(Dimension dimension, GridSquare gridSquare, SnakeWorld world) {
            tailPieces = new ArrayList<>(MAX_TAIL_LEN);

            SnekPiece t1 = new SnekPiece(dimension, SNEK_GREEN);
            t1.setGridSquare(gridSquare, world);

            SnekPiece t2 = new SnekPiece(dimension, SNEK_GREEN);
            t2.setGridSquare(new GridSquare(gridSquare.row(), gridSquare.col() - 1), world);

            tailPieces.add(t1);
            tailPieces.add(t2);
        }

        public void moveToward(GridSquare gridSquare, SnakeWorld world) {
            // Recycle the end of the tail, so we don't have to allocate a new tailpiece
            SnekPiece endOfTail = pop();
            endOfTail.setGridSquare(gridSquare, world);
            tailPieces.add(0, endOfTail);
        }

        public SnekPiece growToward(GridSquare gridSquare, SnakeWorld world) {
            SnekPiece newTailPiece = new SnekPiece(tailPieces.get(0).dimension(), SNEK_GREEN);
            newTailPiece.setGridSquare(gridSquare, world);
            tailPieces.add(0, newTailPiece);

            return newTailPiece;
        }

        private SnekPiece pop() {
            return tailPieces.remove(tailPieces.size() - 1);
        }
    }

    static class SnekPiece extends TOval {
        GridSquare gridSquare;

        public SnekPiece(Dimension dimension, Color color) {
            super(dimension);
            isFilled = true;
            fillColor = color;
            gridSquare = null;
        }

        void setGridSquare(GridSquare gridSquare, SnakeWorld world) {
            this.gridSquare = gridSquare;
            // set origin to world.origin() + position for square
            setOrigin(world.grid().positionForSquare(this.gridSquare));
        }
    }
}
