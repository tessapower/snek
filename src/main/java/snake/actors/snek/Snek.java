package snake.actors.snek;

import snake.actors.apple.Apple;
import snake.assets.SnekHeadSprite;
import snake.assets.SnekTailSprite;
import snake.game.GameWorld;
import snake.game.Grid;
import snake.player.Player;
import snake.settings.GameMode;
import tengine.Actor;
import tengine.graphics.entities.TGraphicCompound;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Snek extends Actor {
    public static final int MAX_TAIL_LEN = 19;

    private final GameWorld world;
    private final Dimension dimension;
    private final Player player;

    private Direction pendingDirection = null;
    private Direction direction;
    private SnekHeadSprite head;
    final List<SnekTailSprite> tailPieces = new ArrayList<>(MAX_TAIL_LEN);
    private boolean shouldGrowTail = false;

    public static Snek spawnAt(GameWorld world, GridSquare gridSquare, Direction initialDirection, Player player) {
        Snek snek = new Snek(
                world,
                gridSquare,
                new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE),
                initialDirection,
                player);

        world.add(snek);

        return snek;
    }

    private Snek(GameWorld world, GridSquare square, Dimension dimension,
                 Direction initialDirection, Player player) {
        this.world = world;
        this.dimension = dimension;
        this.player = player;
        direction = initialDirection;

        graphicEntity = initSprite(world, square);
    }

    private TGraphicCompound initSprite(GameWorld world, GridSquare square) {
        // Head
        head = new SnekHeadSprite(dimension, player.playerNumber());
        head.setGridSquare(square, world);

        // Tail
        SnekTailSprite t1 = makeTailSprite();
        SnekTailSprite t2 = makeTailSprite();

        // Set the tailpieces according to the direction
        switch(direction) {
            case UP -> {
                t1.setGridSquare(new GridSquare(head.gridSquare().row() + 1, head.gridSquare().col()), world);
                t2.setGridSquare(new GridSquare(head.gridSquare().row() + 2, head.gridSquare().col()), world);
            }
            case DOWN -> {
                t1.setGridSquare(new GridSquare(head.gridSquare().row() - 1, head.gridSquare().col()), world);
                t2.setGridSquare(new GridSquare(head.gridSquare().row() - 2, head.gridSquare().col()), world);
            }
            case LEFT -> {
                t1.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() + 1), world);
                t2.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() + 2), world);
            }
            case RIGHT -> {
                t1.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() - 1), world);
                t2.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() - 2), world);
            }
        }

        tailPieces.add(t1);
        tailPieces.add(t2);

        // Body Compound
        TGraphicCompound body = new TGraphicCompound(dimension);
        body.add(head);
        tailPieces.forEach(body::add);

        return body;
    }

    public void update() {
        direction = (pendingDirection == null) ? direction : pendingDirection;
        switch(direction) {
            case UP -> head.setRotation(0, head.midPoint());
            case RIGHT -> head.setRotation(90, head.midPoint());
            case DOWN -> head.setRotation(180, head.midPoint());
            case LEFT -> head.setRotation(270, head.midPoint());
        }
        pendingDirection = null;

        if (shouldGrowTail) {
            // Grow the tail toward the head
            SnekTailSprite newTailPiece = growTailTowardHead();
            ((TGraphicCompound) graphicEntity).add(newTailPiece);
            advanceHead();
            shouldGrowTail = false;
        } else {
            // Move the tail toward the head
            moveTailTowardHead();
            advanceHead();
        }
    }

    public boolean hasHitSelf() {
        for (var tailPiece : tailPieces) {
            if (tailPiece.gridSquare().equals(head.gridSquare())) {
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
                        if (tailPieces.size() < MAX_TAIL_LEN) {
                            growTail();
                        } else {
                            increaseSpeed();
                        }
                    }
                    case INFINITE -> growTail();
                }
            }
            case YUCK -> player.reduceLivesLeft();
        }
    }

    public GridSquare gridSquare() {
        return head.gridSquare();
    }

    public boolean occupies(GridSquare gridSquare) {
        if (head.gridSquare().equals(gridSquare)) return true;

        for (var tailPiece : tailPieces) {
            if (tailPiece.gridSquare().equals(gridSquare)) return true;
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
        // TODO: Really really implement this
    }

    private void advanceHead() {
        switch(direction) {
            case UP ->
                    head.setGridSquare(new GridSquare(head.gridSquare().row() - 1, head.gridSquare().col()), world);
            case DOWN ->
                    head.setGridSquare(new GridSquare(head.gridSquare().row() + 1, head.gridSquare().col()), world);
            case LEFT ->
                    head.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() - 1), world);
            case RIGHT ->
                    head.setGridSquare(new GridSquare(head.gridSquare().row(), head.gridSquare().col() + 1), world);
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

    private SnekTailSprite makeTailSprite() {
        return switch(player.playerNumber()) {
            case PLAYER_ONE -> SnekTailSprite.playerOneTailSprite(dimension);
            case PLAYER_TWO -> SnekTailSprite.playerTwoTailSprite(dimension);
        };
    }

    public void moveTailTowardHead() {
        // Recycle the end of the tail, so we don't have to allocate a new tailpiece
        SnekTailSprite endOfTail = popTailPiece();
        endOfTail.setGridSquare(head.gridSquare(), world);
        tailPieces.add(0, endOfTail);
    }

    public SnekTailSprite growTailTowardHead() {
        SnekTailSprite newTailPiece = makeTailSprite();

        newTailPiece.setGridSquare(head.gridSquare(), world);
        tailPieces.add(0, newTailPiece);

        return newTailPiece;
    }

    private SnekTailSprite popTailPiece() {
        return tailPieces.remove(tailPieces.size() - 1);
    }
}
