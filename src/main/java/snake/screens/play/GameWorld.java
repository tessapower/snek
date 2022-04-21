package snake.screens.play;

import snake.apple.Apple;
import snake.snek.SnekPlayer;
import snake.snek.SnekTail;
import tengine.Actor;
import tengine.world.GridSquare;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameWorld extends World {
    private static final int N_TILES = 32;

    // Future support for offsetting the world around other UI elements
    private final Point origin;
    private final Grid grid;
    // TODO: Change this for supplier?
    private final GameOverNotifier gameOverNotifier;

    // Players
    private final SnekPlayer snekPlayer;
    // player two

    // Apples
    private Apple apple;

    private int score;

    public GameWorld(Point origin, Dimension dimension, GameOverNotifier gameOverNotifier) {
        super(dimension);

        // The world is a fixed size, but the location of where it can be placed in a
        // window can differ, so we need the origin relative to the window.
        this.origin = origin;
        this.gameOverNotifier = gameOverNotifier;

        // The grid tile size is fixed, so we just specify the
        // number of tiles to create different sized grids
        grid = new Grid(N_TILES, N_TILES);

        snekPlayer = SnekPlayer.spawnAt(this, snekSpawnPosition());
        apple = Apple.spawnAt(this, randomUnoccupiedSquare());

        score = 0;
    }

    public void update(double dt) {
        snekPlayer.update(dt);

        if (hasSnekHitWall() || snekPlayer.hasHitSelf()) {
            // TODO: play BONK! noise

            gameOverNotifier.notifyGameOver();
        }

        if (hasSnekEatenApple()) {
            score++;

            if (snekPlayer.tailLength() < SnekTail.MAX_TAIL_LEN) {
                snekPlayer.growTail();
            } else {
                snekPlayer.increaseSpeed();
            }

            apple.removeFromWorld();
            apple = Apple.spawnAt(this, randomUnoccupiedSquare());
        }
    }

    public Grid grid() {
        return grid;
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> snekPlayer.handleKeyEvent(keyEvent);
        }
    }

    public int score() {
        return score;
    }

    private boolean hasSnekHitWall() {
        int row = snekPlayer.gridSquare().row();
        int col = snekPlayer.gridSquare().col();

        return col < 0 || col >= grid.numCols() || row < 0 || row >= grid.numRows();
    }

    private boolean hasSnekEatenApple() {
        return apple.gridSquare().equals(snekPlayer.gridSquare());
    }

    private GridSquare snekSpawnPosition() {
        return new GridSquare(10, 10);
    }

    private GridSquare randomUnoccupiedSquare() {
        // TODO: handle no occupied squares
        GridSquare randomSquare;

        do {
            randomSquare = grid.randomGridSquare();
        } while (getActorAtSquare(randomSquare) != null);

        return randomSquare;
    }

    private Actor getActorAtSquare(GridSquare gridSquare) {
        if (snekPlayer.occupies(gridSquare)) {
            return snekPlayer;
        }

        for (Actor actor : actors) {
            if (actor instanceof SnekPlayer) continue;
            if (((Apple) actor).gridSquare().equals(gridSquare)) {
                return actor;
            }
        }

        return null;
    }
}
