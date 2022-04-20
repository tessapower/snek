package snake.screens.gameover;

import snake.Colors;
import snake.FontBook;
import snake.SnakeGame;
import snake.screens.Screen;
import snake.screens.ScreenChangeRequestCallback;
import snake.screens.ScreenIdentifier;
import snake.snek.AnimatedSnek;
import snake.screens.Button;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreen implements Screen {
    private final ScreenChangeRequestCallback screenChangeCallback;
    private final SnakeGame engine;
    private final TGraphicCompound graphic;

    private final Button playAgain;
    private final Button quit;

    public GameOverScreen(SnakeGame snakeGame, ScreenChangeRequestCallback screenChangeCallback, int finalScore) {
        this.engine = snakeGame;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("game over");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(75, 90));

        // Snek
        AnimatedSnek snek = new AnimatedSnek();
        snek.setOrigin(new Point(SnakeGame.WINDOW_CENTER.x - (snek.width() / 2), SnakeGame.WINDOW_CENTER.y - snek.height()));
        snek.setState(AnimatedSnek.State.DYING);

        // Score
        TLabel score = new TLabel("apples eaten: " + finalScore);
        score.setColor(Colors.Text.PRIMARY);
        score.setFont(FontBook.shared().titleFont());
        score.setOrigin(new Point(45, 160));

        // Buttons
        playAgain = new Button("play again");
        playAgain.setState(Button.ButtonState.FOCUSSED);
        playAgain.setOrigin(new Point(40, 245));

        quit = new Button("quit to menu");
        quit.setState(Button.ButtonState.UNFOCUSED);
        quit.setOrigin(new Point(145, 245));

        // Graphic
        graphic = new TGraphicCompound(SnakeGame.WINDOW_DIMENSION);

        graphic.add(title);
        graphic.add(snek);
        graphic.add(score);
        graphic.add(playAgain);
        graphic.add(quit);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                playAgain.setState(Button.ButtonState.FOCUSSED);
                quit.setState(Button.ButtonState.UNFOCUSED);
            }

            case KeyEvent.VK_RIGHT -> {
                playAgain.setState(Button.ButtonState.UNFOCUSED);
                quit.setState(Button.ButtonState.FOCUSSED);
            }

            case KeyEvent.VK_ENTER -> {
                if (playAgain.state() == Button.ButtonState.FOCUSSED) {
                    screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
                } else {
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
