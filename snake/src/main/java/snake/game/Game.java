package snake.game;

import snake.ui.screens.Screen;
import snake.ui.screens.ScreenIdentifier;
import snake.ui.screens.gameover.GameOverScreen;
import snake.ui.screens.gameplay.PlayGameScreen;
import snake.ui.screens.menu.MenuScreen;
import tengine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The entry point and main controller for the program. Manages displaying and swapping out screens,
 * propagating key events to the currently displayed screen, and updating the game logic and
 * graphics every tick.
 */
public class Game extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(512, 512);
    public static final Point WINDOW_CENTER = new Point(WINDOW_DIMENSION.width / 2, WINDOW_DIMENSION.height / 2);
    private static final String TITLE = "Snek!";

    private Screen activeScreen;

    /**
     * Entry point to the program.
     */
    public static void main(String[] args) {
        createGame(new Game(), 10);
    }

    /**
     * Initializes the game and game engine properties before starting the game.
     */
    @Override
    public void init() {
        setWindowProperties(WINDOW_DIMENSION, TITLE);
        activeScreen = null;
        requestScreenChange(ScreenIdentifier.SHOWING_MENU);
    }

    /**
     * Enables other parts of the game to update every tick after last updating
     * <code>dtMillis</code> ago.
     */
    @Override
    public void update(double dtMillis) {
        activeScreen.update(dtMillis);
        graphicsEngine().update(dtMillis);
    }

    /**
     * Propagates <code>KeyEvent</code>s to the currently active screen.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        activeScreen.handleKeyEvent(keyEvent);
    }

    /**
     * Handles requests to change to the new <code>Screen</code> corresponding to the given
     * <code>ScreenIdentifier</code> and displaying it in the window.
     */
    public void requestScreenChange(ScreenIdentifier newScreen) {
        if (activeScreen != null && activeScreen.screen() == newScreen) return;
        if (activeScreen != null) activeScreen.removeFromCanvas();

        switch(newScreen) {
            case SHOWING_MENU -> activeScreen = new MenuScreen(this);
            case PLAYING -> activeScreen = new PlayGameScreen(this);
            case SHOWING_GAME_OVER -> {
                assert activeScreen != null;
                activeScreen = new GameOverScreen(this, ((PlayGameScreen) activeScreen).gameState());
            }
        }

        activeScreen.addToCanvas();
    }
}
