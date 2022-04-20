package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.SnakeGame;
import snake.screens.*;
import snake.screens.Button;
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
    Button onePlayer;
    Button twoPlayer;
    Button infiniteMode;
    Button howToPlay;
    Button credits;

    private final ButtonGroup menuButtons;

    public MenuScreen(SnakeGame snakeGame, ScreenChangeRequestCallback screenChangeCallback) {
        this.engine = snakeGame;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("snek!");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(100, 90));

        // Snek
        snek = new AnimatedSnek();
        snekStartOrigin = new Point(SnakeGame.WINDOW_DIMENSION.width, SnakeGame.WINDOW_CENTER.y - snek.height());
        snek.setOrigin(snekStartOrigin);
        snek.setState(AnimatedSnek.State.MOVING);


        // Menu Buttons
        onePlayer = new Button("one player");
        onePlayer.setOrigin(new Point(95, 150));

        twoPlayer = new Button("two player");
        twoPlayer.setOrigin(new Point(95, 170));

        infiniteMode = new Button("infinite mode");
        infiniteMode.setOrigin(new Point(90, 190));

        howToPlay = new Button("how to play");
        howToPlay.setOrigin(new Point(95, 210));

        credits = new Button("credits");
        credits.setOrigin(new Point(105, 230));

        menuButtons = new ButtonGroup(onePlayer, twoPlayer, infiniteMode, howToPlay, credits);

        // Graphic
        graphic = new TGraphicCompound(SnakeGame.WINDOW_DIMENSION);
        graphic.addAll(title, snek, onePlayer, twoPlayer, infiniteMode, howToPlay, credits);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> menuButtons.previous();
            case KeyEvent.VK_DOWN -> menuButtons.next();
            case KeyEvent.VK_ENTER -> {
                Button focussed = menuButtons.getFocussed();
                if (focussed.equals(onePlayer)) {
                    screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
                } else if (focussed.equals(twoPlayer)) {
                    System.out.println("two player");
                } else if (focussed.equals(infiniteMode)) {
                    System.out.println("infinite mode");
                } else if (focussed.equals(howToPlay)) {
                    System.out.println("how to play");
                } else if (focussed.equals(credits)) {
                    System.out.println("credits");
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
