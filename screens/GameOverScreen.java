package screens;

import main.SnakeGame;

import java.awt.event.KeyEvent;

public class GameOverScreen implements Screen {
    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;

    public GameOverScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {

    }

    @Override
    public void addToCanvas() {

    }

    @Override
    public void removeFromCanvas() {

    }

    @Override
    public GameScreen screen() {
        return GameScreen.SHOWING_GAME_OVER;
    }

    @Override
    public void update(double dtMillis) {

    }
}
