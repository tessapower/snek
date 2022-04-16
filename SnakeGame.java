import actors.Actor;
import engine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeGame extends GameEngine {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private World world;

    public static void main(String[] args) {
        createGame(new SnakeGame(), 5);
    }

    public void init() {
        setupWindow(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), "Snek!");
        world = new World(WINDOW_WIDTH);
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

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> world.keyPressed(e);
        }
    }

    public boolean isGameOver() {

        return false;
    }

}
