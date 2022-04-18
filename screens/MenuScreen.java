package screens;

import graphics.TGraphicCompound;
import main.SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScreen implements Screen {
    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;
    private TGraphicCompound graphic;

    public MenuScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;
        graphic = new TGraphicCompound(SnakeGame.WINDOW_DIMENSION);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            screenChangeNotifier.notifyScreenChange(GameScreen.PLAYING);
        }
    }

    @Override
    public void addToCanvas() {
    }

    @Override
    public void removeFromCanvas() {

    }

    @Override
    public GameScreen screen() {
        return GameScreen.SHOWING_MENU;
    }

    @Override
    public void update(double dtMillis) {
        engine.changeColor(Color.BLACK);
        engine.drawText(100, 100, "Hello, world!");
    }
}
