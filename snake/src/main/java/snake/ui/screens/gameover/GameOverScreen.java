package snake.ui.screens.gameover;

import snake.assets.AnimatedSnek;
import snake.assets.Colors;
import snake.assets.FontBook;
import snake.assets.SoundEffects;
import snake.game.Game;
import snake.game.GameState;
import snake.ui.components.Button;
import snake.ui.components.ButtonGroup;
import snake.ui.screens.Screen;
import snake.ui.screens.ScreenChangeRequestCallback;
import snake.ui.screens.ScreenIdentifier;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The game over screen and one of the sub-controllers in the program. Handles displaying the
 * outcome of the game, in particular who won a multiplayer game, and either returning to the main
 * menu screen or starting a new game.
 *
 * @author Tessa Power
 */
public class GameOverScreen implements Screen {
    private final ScreenChangeRequestCallback screenChangeCallback;
    private final Game engine;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button playAgain;
    private final Button quit;

    /**
     * Constructs a new <code>GameOverScreen</code> linked to the given main program controller
     * (<code>Game</code>).
     */
    public GameOverScreen(Game game, ScreenChangeRequestCallback screenChangeCallback, GameState gameState) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());

        // Snek
        AnimatedSnek snek = AnimatedSnek.animatedSnek();
        snek.setFps(7);
        snek.setOrigin(new Point(Game.WINDOW_CENTER.x - (snek.width() / 2), Game.WINDOW_CENTER.y - snek.height()));
        snek.setState(AnimatedSnek.State.DYING);

        // Score
        TLabel score = new TLabel("apples eaten: " + gameState.maxApplesEaten());
        score.setColor(Colors.Text.PRIMARY);
        score.setFont(FontBook.shared().titleFont());
        score.setOrigin(new Point(95, 300));

        switch(gameState.gameConfig().multiplayerMode()) {
            case SINGLE_PLAYER -> {
                title.setText("game over");
                title.setOrigin(new Point(150, 180));
            }
            case MULTIPLAYER -> gameState.winner().ifPresentOrElse(winner -> {
                switch (winner.playerNumber()) {
                    case PLAYER_ONE -> {
                        title.setText("green wins!");
                        title.setOrigin(new Point(140, 180));
                    }
                    case PLAYER_TWO -> {
                        title.setText("blue wins!");
                        title.setOrigin(new Point(150, 180));
                    }
                }
            },
            () -> {
                title.setText("it's a draw!");
                title.setOrigin(new Point(140, 180));
            });
        }

        // Buttons
        playAgain = new Button("play again");
        playAgain.setOrigin(new Point(80, 490));

        quit = new Button("quit to menu");
        quit.setOrigin(new Point(290, 490));

        buttonGroup = new ButtonGroup(playAgain, quit);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);

        graphic.addAll(title, snek, score, playAgain, quit);
    }

    /**
     * Handles the given <code>KeyEvent</code> appropriately.
     */
    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> buttonGroup.previous();
            case KeyEvent.VK_RIGHT -> buttonGroup.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                if (buttonGroup.getFocussed() == playAgain) {
                    screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
                } else if (buttonGroup.getFocussed() == quit){
                    screenChangeCallback.requestScreenChange(ScreenIdentifier.SHOWING_MENU);
                }
            }
        }
    }

    /**
     * Adds this <code>GameOverScreen</code> to the window to be displayed.
     */
    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(graphic);
    }

    /**
     * Removes this <code>MenuScreen</code> from the window.
     */
    @Override
    public void removeFromCanvas() {
        graphic.removeFromParent();
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.SHOWING_GAME_OVER;
    }

    @Override
    public void update(double dtMillis) {
        // No-op
    }
}
