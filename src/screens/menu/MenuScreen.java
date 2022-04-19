package screens.menu;

import graphics.graphicsObjects.TGraphicCompound;
import graphics.graphicsObjects.text.TLabel;
import main.FontBook;
import main.SnakeGame;
import screens.ScreenIdentifier;
import screens.GameScreenChangeNotifier;
import screens.Screen;
import snek.AnimatedSnek;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScreen implements Screen {
    private static final double ANIMATION_SPEED = 0.5;

    private final GameScreenChangeNotifier screenChangeNotifier;
    private final SnakeGame engine;
    private final TGraphicCompound graphic;
    private final AnimatedSnek snek;

    private final Point snekStartOrigin;

    public MenuScreen(SnakeGame snakeGame, GameScreenChangeNotifier screenChangeNotifier) {
        this.engine = snakeGame;
        this.screenChangeNotifier = screenChangeNotifier;

        // Title
        TLabel title = new TLabel("snek!");
        title.setColor(FontBook.SNEK_GREEN);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(100, 90));

        // Prompt
        TLabel prompt = new TLabel("press enter to play...");
        prompt.setColor(FontBook.SNEK_GREEN);
        prompt.setFont(FontBook.shared().promptFont());
        prompt.setOrigin(new Point(50, 250));

        // Snek
        snek = new AnimatedSnek();
        snekStartOrigin = new Point(SnakeGame.WINDOW_DIMENSION.width, SnakeGame.WINDOW_CENTER.y - snek.height());
        snek.setOrigin(snekStartOrigin);
        snek.setState(AnimatedSnek.State.MOVING);

        // Graphic
        graphic = new TGraphicCompound(SnakeGame.WINDOW_DIMENSION);
        graphic.add(title);
        graphic.add(prompt);
        graphic.add(snek);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            screenChangeNotifier.notifyScreenChange(ScreenIdentifier.PLAYING);
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
        return ScreenIdentifier.SHOWING_MENU;
    }

    @Override
    public void update(double dtMillis) {
        animateSnek(dtMillis);
        graphic.update(dtMillis);
    }

    private void animateSnek(double dtMillis) {
        if (snek.origin().x < -snek.width()) {
            snek.setOrigin(snekStartOrigin);
        } else {
            Point newOrigin = snek.origin();
            newOrigin.translate((int) (-dtMillis * snek.width() * ANIMATION_SPEED), 0);
            snek.setOrigin(newOrigin);
        }
    }
}
