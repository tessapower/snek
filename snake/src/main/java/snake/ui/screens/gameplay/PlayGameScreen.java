package snake.ui.screens.gameplay;

import snake.assets.SoundEffects;
import snake.game.Game;
import snake.game.GameState;
import snake.game.GameWorld;
import snake.settings.Settings;
import snake.ui.screens.Screen;
import snake.ui.screens.ScreenIdentifier;

import java.awt.event.KeyEvent;

/**
 * One of the sub-controllers in the program. Responsible for managing the displayed game play
 * content, pausing and resuming the game, starting and stopping the background music, and
 * notifying when to show the game over screen.
 *
 * @author Tessa Power
 */
public class PlayGameScreen implements Screen {
    private final Game engine;
    private final GameWorld world;
    private final GameState gameState;
    private boolean paused;

    /**
     * Constructs a new <code>PlayGameScreen</code> for the
     */
    public PlayGameScreen(Game game) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.engine = game;
        paused = false;
        gameState = new GameState(Settings.shared().config());
        world = new GameWorld(
                Game.WINDOW_DIMENSION,
                this::onGameOver,
                gameState);
    }

    /**
     * A callback method that is called when the game is over.
     */
    public void onGameOver() {
        SoundEffects.shared().backgroundMusic().stopPlayingLoop();
        SoundEffects.shared().gameOver().play();
        engine.requestScreenChange(ScreenIdentifier.SHOWING_GAME_OVER);
    }

    /**
     * Handles the given <code>KeyEvent</code> appropriately.
     */
    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            // display paused label
            paused = !paused;
        } else if (!paused) {
            world.handleKeyEvent(keyEvent);
        }
    }

    /**
     * Adds this <code>PlayGameScreen</code> to the window to be displayed.
     */
    @Override
    public void addToCanvas() {
        engine.loadWorld(world);
    }

    /**
     * Removes this <code>PlayGameScreen</code> from the window.
     */
    @Override
    public void removeFromCanvas() {
        engine.unloadWorld(world);
    }

    /**
     * Allows this <code>PlayGameScreen</code> to update since it was last updated
     * <code>dtMillis</code> ago. Doesn't perform anything while the game is paused.
     */
    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update();
        }
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.PLAYING;
    }

    /**
     * The <code>GameState</code> for this <code>PlayGameScreen</code>.
     */
    public GameState gameState() {
        return gameState;
    }
}
