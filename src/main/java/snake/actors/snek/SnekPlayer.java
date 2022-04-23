package snake.actors.snek;

import snake.GameMode;
import snake.actors.apple.Apple;
import snake.player.Action;
import snake.player.Player;
import snake.screens.gameplay.GameWorld;
import snake.screens.gameplay.Grid;
import tengine.Actor;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class SnekPlayer extends Actor {
    private final GameWorld world;
    private final Dimension dimension;
    private final Player player;

    private Direction pendingDirection;
    private Direction direction;
    private SnekHeadSprite head;
    private SnekTail tail;
    private boolean shouldGrowTail;

    public static SnekPlayer spawnAt(GameWorld world,
                                     GridSquare gridSquare,
                                     Direction initialDirection,
                                     Player player) {
        SnekPlayer snekPlayer = new SnekPlayer(
                world,
                gridSquare,
                new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE),
                initialDirection,
                player);

        world.add(snekPlayer);

        return snekPlayer;
    }

    private SnekPlayer(GameWorld world, GridSquare square, Dimension dimension,
                       Direction initialDirection, Player player) {
        this.world = world;
        this.dimension = dimension;

        direction = initialDirection;
        pendingDirection = null;

        this.player = player;

        graphicObject = initSprite(world, square);
        shouldGrowTail = false;
    }

    private TGraphicCompound initSprite(GameWorld world, GridSquare square) {
        // Head
        head = new SnekHeadSprite(dimension, direction, player.playerNumber());
        head.setGridSquare(square, world);

        // Tail
        tail = new SnekTail(dimension,
                head.gridSquare,
                direction,
                world,
                player.playerNumber());

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

    public boolean hasHitWall() {
        return !world.grid().contains(gridSquare());
    }

    public void eat(Apple apple, GameMode gameMode) {
        switch(apple.appleType()) {
            case CROMCHY -> {
                player.increaseScore();

                switch(gameMode) {
                    case NORMAL -> {
                        if (tail.length() < SnekTail.MAX_TAIL_LEN) {
                            growTail();
                        } else {
                            increaseSpeed();
                        }
                    }
                    case INFINITE -> growTail();
                }
            }
            case YUCK -> {
                player.reduceLivesLeft();
            }
        }
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

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::performAction);

        return action.isPresent();
    }

    public void performAction(Action action) {
        switch(action) {
            case MOVE_UP -> setPendingDirection(Direction.UP);
            case MOVE_DOWN -> setPendingDirection(Direction.DOWN);
            case MOVE_LEFT -> setPendingDirection(Direction.LEFT);
            case MOVE_RIGHT -> setPendingDirection(Direction.RIGHT);
        }
    }

    /**
     * Mark the tail to grow by one on the next update. Should only be called <i>once</i>
     * per update cycle, multiple calls will still only grow the tail by one on the next draw.
     */
    private void growTail() {
        shouldGrowTail = true;
    }

    private void increaseSpeed() {
        // TODO: Implement this
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
