package main;

import engine.GameEngine;
import screens.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeGame extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(512, 512);
    private static final String TITLE = "snek.Snek!";

    private final Screen menu = new MenuScreen(this, this::onScreenChange);
    private final Screen game = new PlayGameScreen(this, this::onScreenChange);
    private final Screen gameOver = new GameOverScreen(this, this::onScreenChange);
    private Screen activeScreen = game;

    public static void main(String[] args) {
        createGame(new SnakeGame(), 10);
    }

    public void init() {
        setupWindow(WINDOW_DIMENSION, TITLE);
        activeScreen.addToCanvas();
    }

    @Override
    public void update(double dtMillis) {
        // TODO: I don't think I should be calling this here??
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
            case PLAYING -> activeScreen = game;
            case SHOWING_GAME_OVER -> activeScreen = gameOver;
        }

        activeScreen.addToCanvas();
    }
}
