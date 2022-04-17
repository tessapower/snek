import actors.Actor;
import actors.World;
import engine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeWorld extends World {
    private static final int N_TILES = 50;
    // Future support for offsetting the world around other UI elements
    private final Point origin;
    private final Grid grid;
    private Snek snek;
    private Apple apple;

    public SnakeWorld(Point origin, GameEngine engine) {
        super(engine);

        // The world is a fixed size, but the location of where it can be placed in a
        // window can differ, so we need the origin relative to the window.
        this.origin = origin;

        // The grid tile size is fixed, so we just specify the
        // number of tiles to create different sized grids
        grid = new Grid(N_TILES, N_TILES);

        snek = Snek.spawnAt(this, snekSpawnPosition());
        apple = Apple.spawnAt(this, randomUnoccupiedSquare());
    }

    public void update(double dt) {
        snek.update(dt);

        if (hasSnekHitWall()) {
            snek.removeFromWorld();
            snek = Snek.spawnAt(this, snekSpawnPosition());
        }

        if (hasSnekEatenApple()) {
            snek.increaseScore();
            snek.growTail();
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
        for (Actor actor : actors) {
            // TODO: this is ugly because we don't have the concept of a tile actor
            //   - how do we ask for the tiles that a tile actor occupies
            //   tileActor.occupies(square) ?
            //   tileActor.occupiedSquares(): List<GridSquare>

            // TODO: this will need to change for the Snek to determine if any part of
            //   the tail is on the grid square too
            if (actor instanceof Snek && ((Snek) actor).gridSquare() == gridSquare
                    || actor instanceof Apple && ((Apple) actor).gridSquare() == gridSquare) {
                return actor;
            }
        }

        return null;
    }

    // Dispatch relevant key events to the appropriate actors
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> snek.keyPressed(e);
        }
    }
}
