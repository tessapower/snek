package snek;

import graphics.graphicsObjects.TGraphicCompound;
import graphics.graphicsObjects.shapes.TRect;
import graphics.graphicsObjects.sprites.Sprite;
import main.Actor;
import org.jetbrains.annotations.NotNull;
import screens.play.Grid;
import screens.play.SnakeWorld;
import world.GridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snek extends Actor {
    public static final Color SNEK_GREEN = new Color(119, 167, 117);
    public static final Color SNEK_RED = new Color(230, 82, 83);
    private final SnakeWorld world;
    private final Dimension dimension;

    private Direction pendingDirection;
    private Direction direction;
    private SnekTail tail;
    private boolean shouldGrowTail;
    private SnekHeadSprite head;

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

    public static class SnekTail {
        private static final String SNEK_TAIL_FILE = "src/main/resources/snek-body-p1.png";
        public static final int MAX_TAIL_LEN = 19;
        List<SnekTailSprite> tailPieces;

        public SnekTail(Dimension dimension, GridSquare gridSquare, SnakeWorld world) {
            tailPieces = new ArrayList<>(MAX_TAIL_LEN);

            SnekTailSprite t1 = new SnekTailSprite(dimension, SNEK_TAIL_FILE);
            t1.setGridSquare(gridSquare, world);

            SnekTailSprite t2 = new SnekTailSprite(dimension, SNEK_TAIL_FILE);
            t2.setGridSquare(new GridSquare(gridSquare.row(), gridSquare.col() - 1), world);

            tailPieces.add(t1);
            tailPieces.add(t2);
        }

        public int length() {
            return tailPieces.size();
        }

        public void moveToward(GridSquare gridSquare, SnakeWorld world) {
            // Recycle the end of the tail, so we don't have to allocate a new tailpiece
            SnekTailSprite endOfTail = pop();
            endOfTail.setGridSquare(gridSquare, world);
            tailPieces.add(0, endOfTail);
        }

        public SnekTailSprite growToward(GridSquare gridSquare, SnakeWorld world) {
            SnekTailSprite newTailPiece = new SnekTailSprite(tailPieces.get(0).dimension(), SNEK_TAIL_FILE);
            newTailPiece.setGridSquare(gridSquare, world);
            tailPieces.add(0, newTailPiece);

            return newTailPiece;
        }

        private SnekTailSprite pop() {
            return tailPieces.remove(tailPieces.size() - 1);
        }
    }

    static class SnekTailSprite extends Sprite {
        GridSquare gridSquare;

        public SnekTailSprite(Dimension dimension, String spriteFile) {
            super(spriteFile, dimension);

            gridSquare = null;
        }

        void setGridSquare(GridSquare gridSquare, SnakeWorld world) {
            this.gridSquare = gridSquare;
            // set origin to world.origin() + position for square
            setOrigin(world.grid().positionForSquare(this.gridSquare));
        }
    }

    static class SnekHeadSprite extends TGraphicCompound {
        private static final String SNEK_HEAD_FILE = "src/main/resources/snek-head.png";

        Direction direction;
        GridSquare gridSquare;
        TGraphicCompound rotationContainer;

        public SnekHeadSprite(Dimension dimension, Direction initialDirection) {
            super(dimension);
            direction = initialDirection;

            rotationContainer = new TGraphicCompound(dimension);
            rotationContainer.setOrigin(new Point(dimension.width / 2, dimension.height / 2));

            Sprite sprite = new Sprite(SNEK_HEAD_FILE, dimension);

            TRect mlem = new TRect(new Dimension(4, 4));
            mlem.isFilled = true;
            mlem.fillColor = Snek.SNEK_RED;

            sprite.setOrigin(new Point(-dimension.width / 2, -dimension.height / 2));
            // We want this to extend outside the snake head sprite
            mlem.setOrigin(new Point(-dimension.width / 2 + 6, -dimension.height / 2 - 4));
            rotationContainer.add(sprite);
            rotationContainer.add(mlem);

            add(rotationContainer);

            gridSquare = null;
        }

        void setGridSquare(GridSquare gridSquare, SnakeWorld world) {
            this.gridSquare = gridSquare;
            // set origin to world.origin() + position for square
            setOrigin(world.grid().positionForSquare(this.gridSquare));
        }

        @Override
        public void update(double dtMillis) {
            super.update(dtMillis);
            switch(direction) {
                case UP -> rotationContainer.setRotation(0);
                case RIGHT -> rotationContainer.setRotation(90);
                case DOWN -> rotationContainer.setRotation(180);
                case LEFT -> rotationContainer.setRotation(270);
            }
        }
    }
}
