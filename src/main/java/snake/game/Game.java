package snake.game;

import snake.ui.screens.Screen;
import snake.ui.screens.ScreenIdentifier;
import snake.ui.screens.gameover.GameOverScreen;
import snake.ui.screens.gameplay.PlayGameScreen;
import snake.ui.screens.menu.MenuScreen;
import tengine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(512, 512);
    public static final Point WINDOW_CENTER = new Point(WINDOW_DIMENSION.width / 2, WINDOW_DIMENSION.height / 2);
    private static final String TITLE = "Snek!";

    private Screen activeScreen;

    public static void main(String[] args) {
        createGame(new Game(), 10);
    }

    @Override
    public void init() {
        setWindowProperties(WINDOW_DIMENSION, TITLE);
        activeScreen = null;
        requestScreenChange(ScreenIdentifier.SHOWING_MENU);
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

    public void requestScreenChange(ScreenIdentifier newScreen) {
        if (activeScreen != null && activeScreen.screen() == newScreen) return;
        if (activeScreen != null) activeScreen.removeFromCanvas();

        switch(newScreen) {
            case SHOWING_MENU -> activeScreen = new MenuScreen(this, this::requestScreenChange);
            case PLAYING -> activeScreen = new PlayGameScreen(this, this::requestScreenChange);
            case SHOWING_GAME_OVER -> {
                assert activeScreen != null;
                activeScreen = new GameOverScreen(this, this::requestScreenChange, ((PlayGameScreen) activeScreen).gameState());
            }
        }

        activeScreen.addToCanvas();
    }
}

