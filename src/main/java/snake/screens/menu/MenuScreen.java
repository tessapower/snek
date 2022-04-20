package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.SnakeGame;
import snake.screens.Screen;
import snake.screens.ScreenChangeRequestCallback;
import snake.screens.ScreenIdentifier;
import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScreen implements Screen {
    private static final double ANIMATION_SPEED = 0.5;

    private final ScreenChangeRequestCallback screenChangeCallback;
    private final SnakeGame engine;
    private final TGraphicCompound graphic;
    private final AnimatedSnek snek;

    private final Point snekStartOrigin;

    public MenuScreen(SnakeGame snakeGame, ScreenChangeRequestCallback screenChangeCallback) {
        this.engine = snakeGame;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("snek!");
        title.setColor(Colors.SNEK_GREEN);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(100, 90));

        // Prompt
        TLabel prompt = new TLabel("press enter to play...");
        prompt.setColor(Colors.SNEK_GREEN);
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
            screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
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
