package screens.play;

import main.SnakeGame;
import screens.ScreenIdentifier;
import screens.GameScreenChangeNotifier;
import screens.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayGameScreen implements Screen {
    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;
    private final SnakeWorld world;
    private boolean paused;

    public PlayGameScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;
        paused = false;
        world = new SnakeWorld(new Point(0, 0), SnakeGame.WINDOW_DIMENSION, this::onGameOver);
    }

    public void onGameOver() {
        System.out.println("Game over! Your score was " + score());
        screenChangeNotifier.notifyScreenChange(ScreenIdentifier.SHOWING_GAME_OVER);
    }

    public int score() {
        return world.score();
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        } else if (!paused) {
            world.handleKeyEvent(keyEvent);
        }
    }

    @Override
    public void addToCanvas() {
        engine.loadWorld(world);
    }

    @Override
    public void removeFromCanvas() {
        engine.unloadWorld(world);
    }

    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update(dtMillis);
        }
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.PLAYING;
    }
}
