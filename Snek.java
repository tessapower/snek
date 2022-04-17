import actors.Actor;
import graphics.TCompound;
import graphics.TOval;
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
    private SnekPiece head;
    private int score;

    public static @NotNull
    Snek spawnAt(SnakeWorld world, GridSquare gridSquare) {
        Snek snek = new Snek(world, gridSquare, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        world.add(snek);

        return snek;
    }

    private Snek(SnakeWorld world, GridSquare gridSquare, Dimension dimension, Direction initialDirection) {
        this.world = world;
        this.dimension = dimension;
        score = 0;

        direction = initialDirection;
        pendingDirection = null;

        sprite = initSprite(gridSquare, world);
    }

    private TCompound initSprite(GridSquare gridSquare, SnakeWorld world) {
        // Head
        head = new SnekPiece(dimension, SNEK_RED);
        head.setGridSquare(gridSquare, world);

        // Tail
        tail = new SnekTail(dimension, new GridSquare(head.gridSquare.row(), head.gridSquare.col() - 1), world);

        // Sprite
        TCompound body = new TCompound(dimension);
        body.add(head);
        tail.tailPieces.forEach(body::add);

        return body;
    }

    public void update(double dt) {
        direction = (pendingDirection == null) ? direction : pendingDirection;
        pendingDirection = null;
        advanceToNextGridSquare(dt);
    }

    public GridSquare gridSquare() {
        return head.gridSquare;
    }

    public boolean occupies(GridSquare gridSquare) {
        return gridSquare == head.gridSquare;
    }

    public int score() {
        return score;
    }

    public void increaseScore() {
        System.out.println("Score: " + ++score);
    }

    private void advanceToNextGridSquare(double dt) {
        // Move the tail toward the head
        tail.moveToward(head.gridSquare, world);

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

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> setPendingDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> setPendingDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> setPendingDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> setPendingDirection(Direction.RIGHT);
        }
    }

    static class SnekTail {
        private static final Color SNEK_GREEN = new Color(119, 167, 117);
        private static final int STARTING_LEN = 2;
        public static final int MAX_TAIL_LEN = 19;
        List<SnekPiece> tailPieces;
        int length;

        public SnekTail(Dimension dimension, GridSquare gridSquare, SnakeWorld world) {
            tailPieces = new ArrayList<>(MAX_TAIL_LEN);
            length = STARTING_LEN;

            SnekPiece t1 = new SnekPiece(dimension, SNEK_GREEN);
            t1.setGridSquare(gridSquare, world);

            SnekPiece t2 = new SnekPiece(dimension, SNEK_GREEN);
            t2.setGridSquare(new GridSquare(gridSquare.row(), gridSquare.col() - 1), world);

            tailPieces.add(t1);
            tailPieces.add(t2);
        }

        public void moveToward(GridSquare gridSquare, SnakeWorld world) {
            SnekPiece endOfTail = tailPieces.get(length - 1);
            endOfTail.setGridSquare(gridSquare, world);
            shiftTailPiecesDown();
            tailPieces.set(0, endOfTail);
        }

        private void shiftTailPiecesDown() {
            for(var i = length - 1; i > 0; --i) {
                tailPieces.set(i, tailPieces.get(i - 1));
            }
        }

        public void grow() {
            // tailLen++;
            //
            // tail.add(new SnekPiece(dimension, snekGreen));
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
