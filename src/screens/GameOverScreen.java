package screens;

import graphics.graphicsObjects.TGraphicCompound;
import graphics.graphicsObjects.text.TLabel;
import main.FontBook;
import main.SnakeGame;
import snek.Snek;
import snek.SnekSprite;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreen implements Screen {
    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;
    private final TGraphicCompound graphic;

    public GameOverScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier, int finalScore) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;

        // Title
        TLabel title = new TLabel("game over");
        title.setColor(Snek.SNEK_RED);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(75, 90));

        // Snek
        SnekSprite snek = new SnekSprite();
        snek.setOrigin(new Point(SnakeGame.WINDOW_CENTER.x - (snek.width() / 2), SnakeGame.WINDOW_CENTER.y - snek.height()));
        snek.setState(SnekSprite.State.DYING);

        // Score
        TLabel score = new TLabel("apples eaten: " + finalScore);
        score.setColor(Snek.SNEK_GREEN);
        score.setFont(FontBook.shared().titleFont());
        score.setOrigin(new Point(45, 160));

        // Prompt
        TLabel prompt = new TLabel("enter: play again     esc: go to menu");
        prompt.setColor(Snek.SNEK_GREEN);
        prompt.setFont(FontBook.shared().instructionFont());
        prompt.setOrigin(new Point(40, 250));

        // Graphic
        graphic = new TGraphicCompound(SnakeGame.WINDOW_DIMENSION);
        graphic.add(title);
        graphic.add(snek);
        graphic.add(score);
        graphic.add(prompt);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> screenChangeNotifier.notifyScreenChange(GameScreen.SHOWING_MENU);
            case KeyEvent.VK_ENTER -> screenChangeNotifier.notifyScreenChange(GameScreen.PLAYING);
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
    public GameScreen screen() {
        return GameScreen.SHOWING_GAME_OVER;
    }

    @Override
    public void update(double dtMillis) {

    }
}
