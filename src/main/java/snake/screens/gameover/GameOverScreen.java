package snake.screens.gameover;

import snake.*;
import snake.player.Player;
import snake.screens.Button;
import snake.screens.*;
import snake.screens.play.GameState;
import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreen implements Screen {
    private final ScreenChangeRequestCallback screenChangeCallback;
    private final Game engine;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button playAgain;
    private final Button quit;

    public GameOverScreen(Game game, ScreenChangeRequestCallback screenChangeCallback, GameState gameState) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("game over");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(150, 180));

        // Snek
        AnimatedSnek snek = AnimatedSnek.animatedSnek();
        snek.setFps(7);
        snek.setOrigin(new Point(Game.WINDOW_CENTER.x - (snek.width() / 2), Game.WINDOW_CENTER.y - snek.height()));
        snek.setState(AnimatedSnek.State.DYING);

        // Score
        TLabel score = new TLabel("apples eaten: " + gameState.playerOneState().score());
        score.setColor(Colors.Text.PRIMARY);
        score.setFont(FontBook.shared().titleFont());
        score.setOrigin(new Point(95, 300));

        // Display results based on Multiplayer Mode
        if (Settings.shared().playerMode() == MultiplayerMode.MULTIPLAYER) {
            if (gameState.playerOneState().score() == gameState.playerTwoState().score()) {
                title.setText("it's a draw!");
                title.setOrigin(new Point(140, 180));
            } else {
                Player winner = gameState.playerOneState().score() > gameState.playerTwoState().score() ? Player.PLAYER_ONE : Player.PLAYER_TWO;
                switch (winner) {
                    case PLAYER_ONE -> {
                        title.setText("green wins!");
                        title.setOrigin(new Point(140, 180));
                        score.setText("apples eaten: " + gameState.playerOneState().score());
                    }
                    case PLAYER_TWO -> {
                        title.setText("blue wins!");
                        title.setOrigin(new Point(150, 180));
                        score.setText("apples eaten: " + gameState.playerTwoState().score());
                    }
                }
            }
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

    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(graphic);
    }

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

    }
}
