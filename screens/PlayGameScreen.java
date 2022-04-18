package screens;

import main.SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayGameScreen implements Screen {
    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;
    private SnakeWorld world;
    private boolean paused;

    public PlayGameScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;
        paused = false;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        } else {
            world.handleKeyEvent(keyEvent);
        }
    }

    @Override
    public void addToCanvas() {
        world = new SnakeWorld(new Point(0, 0), engine);
    }

    @Override
    public void removeFromCanvas() {

    }

    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update(dtMillis);
        }
    }

    @Override
    public GameScreen screen() {
        return GameScreen.PLAYING;
    }
}
