package snake.screens.play;

import snake.Game;
import snake.Settings;
import snake.SoundEffects;
import snake.screens.Screen;
import snake.screens.ScreenChangeRequestCallback;
import snake.screens.ScreenIdentifier;

import java.awt.event.KeyEvent;

public class PlayGameScreen implements Screen {
    private final ScreenChangeRequestCallback screenChangeCallback;
    private final Game engine;
    private final GameWorld world;
    private final GameState gameState;
    private boolean paused;

    public PlayGameScreen(Game game, ScreenChangeRequestCallback screenChangeCallback) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;
        paused = false;
        gameState = new GameState(Settings.shared().config());
        world = new GameWorld(
                Game.WINDOW_DIMENSION,
                this::onGameOver,
                gameState);
    }

    public void onGameOver() {
        SoundEffects.shared().backgroundMusic().stopPlayingLoop();
        SoundEffects.shared().gameOver().play();
        screenChangeCallback.requestScreenChange(ScreenIdentifier.SHOWING_GAME_OVER);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            // display paused label
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

    public GameState gameState() {
        return gameState;
    }
}
