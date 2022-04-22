package snake.snek;

import snake.GameMode;
import snake.apple.Apple;
import snake.player.Action;
import snake.player.PlayerConfig;
import snake.player.PlayerState;
import snake.screens.play.GameWorld;
import snake.screens.play.Grid;
import tengine.Actor;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnekPlayer extends Actor {
    public static final int NUM_STARTING_LIVES = 3;

    private final GameWorld world;
    private final Dimension dimension;
    private final PlayerConfig config;

    private final PlayerState state;
    private Direction pendingDirection;
    private Direction direction;
    private SnekHeadSprite head;
    private SnekTail tail;
    private boolean shouldGrowTail;

    public static SnekPlayer spawnAt(GameWorld world, GridSquare gridSquare, PlayerConfig config,
                                     PlayerState playerState) {
        SnekPlayer snekPlayer = new SnekPlayer(world, gridSquare, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE),
                Direction.RIGHT, config, playerState);
        world.add(snekPlayer);

        return snekPlayer;
    }

    private SnekPlayer(GameWorld world, GridSquare square, Dimension dimension,
                       Direction initialDirection, PlayerConfig config, PlayerState state) {
        this.world = world;
        this.dimension = dimension;

        direction = initialDirection;
        pendingDirection = null;

        this.config = config;

        graphicObject = initSprite(world, square);
        shouldGrowTail = false;

        this.state = state;
    }

    private TGraphicCompound initSprite(GameWorld world, GridSquare square) {
        // TODO: Change color of snek depending on player number
        // Head
        head = new SnekHeadSprite(dimension, direction, config.playerNumber());
        head.setGridSquare(square, world);

        // Tail
        tail = new SnekTail(dimension, new GridSquare(head.gridSquare.row(), head.gridSquare.col() - 1),
                world, config.playerNumber());

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
                state.increaseScore();

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
                state.reduceLivesLeft();
                System.out.println(state.livesLeft());
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
        Action action = config.controls().get(keyEvent.getKeyCode());
        if (action != null) {
            performAction(action);

            return true;
        }

        return false;
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
