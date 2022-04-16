import actors.Actor;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Grid:
 *   0 1 2 ... N
 * 0 . . . ... .
 * 1 . . . ... .
 * 2 . . . ... .
 * | | | |  |  |
 * N . . . ... .
 */
public class World {
    private final Dimension dimension;
    private final Grid grid;
    private List<Actor> actors;
    private final Snek snek;

    public World(int size, int numTiles) {
        dimension = new Dimension(size, size);
        grid = new Grid(numTiles, numTiles);
        actors = new ArrayList<>();

        snek = new Snek(this, new Dimension(Grid.TILE_SIZE, Grid.TILE_SIZE), Direction.RIGHT);
        // set grid square
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
        return new GridSquare(1, 1);
    }

    private Actor getActorAtTile(Point point) {

        return null;
    }
}
