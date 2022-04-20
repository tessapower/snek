package snake.screens.play;

import snake.apple.Apple;
import snake.snek.Snek;
import tengine.Actor;
import tengine.world.GridSquare;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeWorld extends World {
    private static final int N_TILES = 32;

    // Future support for offsetting the world around other UI elements
    private final Point origin;
    private final Grid grid;

    private final GameOverNotifier gameOverNotifier;
    private final Snek snek;
    private Apple apple;
    private int score;

    public SnakeWorld(Point origin, Dimension dimension, GameOverNotifier gameOverNotifier) {
        super(dimension);

        // The world is a fixed size, but the location of where it can be placed in a
        // window can differ, so we need the origin relative to the window.
        this.origin = origin;
        this.gameOverNotifier = gameOverNotifier;

        // The grid tile size is fixed, so we just specify the
        // number of tiles to create different sized grids
        grid = new Grid(N_TILES, N_TILES);

        snek = Snek.spawnAt(this, snekSpawnPosition());
        apple = Apple.spawnAt(this, randomUnoccupiedSquare());

        score = 0;
    }

    public void update(double dt) {
        snek.update(dt);

        // TODO: handle collision with tail
        if (hasSnekHitWall() || snek.hasHitSelf()) {
            // TODO: play BONK! noise

            gameOverNotifier.notifyGameOver();
        }

        if (hasSnekEatenApple()) {
            score++;

            if (snek.tailLength() < Snek.SnekTail.MAX_TAIL_LEN) {
                snek.growTail();
            } else {
                snek.increaseSpeed();
            }

            apple.removeFromWorld();
            apple = Apple.spawnAt(this, randomUnoccupiedSquare());
        }
    }

    public Grid grid() {
        return grid;
    }

    private boolean hasSnekHitWall() {
        int row = snek.gridSquare().row();
        int col = snek.gridSquare().col();

        return col < 0 || col >= grid.numCols() || row < 0 || row >= grid.numRows();
    }

    private boolean hasSnekEatenApple() {
        return apple.gridSquare().equals(snek.gridSquare());
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
        if (snek.occupies(gridSquare)) {
            return snek;
        }

        for (Actor actor : actors) {
            if (actor instanceof Snek) continue;
            if (((Apple) actor).gridSquare().equals(gridSquare)) {
                return actor;
            }
        }

        return null;
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> snek.handleKeyEvent(keyEvent);
        }
    }

    public int score() {
        return score;
    }
}
