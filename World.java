import actors.Actor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class World {
    private static final int N_TILES = 50;
    private final Dimension dimension;
    private final Grid grid;
    private List<Actor> actors;
    private final Snek snek;

    public World(int size) {
        dimension = new Dimension(size, size);
        grid = new Grid(N_TILES, N_TILES);
        actors = new ArrayList<>();

        snek = new Snek(this, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        snek.setGridSquare(snekSpawnPosition());
        actors.add(snek);
    }

    public List<Actor> actors() {
        return Collections.unmodifiableList(actors);
    }

    public void update(double dt) {
        snek.update(dt);
    }

    public Grid grid() {
        return grid;
    }

    private boolean hasSnekHitWall() {
         return false;
    }

    private GridSquare snekSpawnPosition() {
        return new GridSquare(10, 10);
    }

    private Actor getActorAtTile(Point point) {

        return null;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> snek.keyPressed(e);
        }
    }
}
