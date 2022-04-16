import actors.Actor;
import engine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeGame extends GameEngine {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private World world;

    public static void main(String[] args) {
        createGame(new SnakeGame(), 60);
    }

    public void init() {
        setupWindow(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), "Snek!");
        world = new World(WINDOW_WIDTH, 20);
        for (Actor a : world.actors()) {
            addActor(a);
        }
    }

    @Override
    public void update(double dt) {
        if (!isGameOver()) {
            world.update(dt);
        }
    }

    public boolean isGameOver() {

        return false;
    }

}
