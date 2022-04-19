package main;

import screens.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeGame extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(512, 512);
    public static final Point WINDOW_CENTER = new Point(WINDOW_DIMENSION.width / 2, WINDOW_DIMENSION.height / 2);
    private static final String TITLE = "Snek!";

    private final MenuScreen menu = new MenuScreen(this, this::onScreenChange);

    private Screen activeScreen = menu;
    private PlayGameScreen game;

    public static void main(String[] args) {
        createGame(new SnakeGame(), 10);
    }

    public void init() {
        setWindowProperties(WINDOW_DIMENSION, TITLE);
        activeScreen.addToCanvas();
    }

    @Override
    public void update(double dtMillis) {
        activeScreen.update(dtMillis);
        graphicsEngine().update(dtMillis);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        activeScreen.handleKeyEvent(keyEvent);
    }

    public void onScreenChange(GameScreen screen) {
        if (activeScreen.screen() == screen) return;

        if (activeScreen != null) {
            activeScreen.removeFromCanvas();
        }

        switch(screen) {
            case SHOWING_MENU -> activeScreen = menu;
            case PLAYING -> {
                game = new PlayGameScreen(this, this::onScreenChange);
                activeScreen = game;
            }
            case SHOWING_GAME_OVER -> {
                GameOverScreen gameOver = new GameOverScreen(this, this::onScreenChange, game.score());

                activeScreen = gameOver;
            }
        }

        activeScreen.addToCanvas();
    }
}
