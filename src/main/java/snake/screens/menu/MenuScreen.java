package snake.screens.menu;

import snake.Game;
import snake.MultiplayerMode;
import snake.Settings;
import snake.screens.Screen;
import snake.screens.ScreenChangeRequestCallback;
import snake.screens.ScreenIdentifier;
import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScreen implements Screen {
    private static final double ANIMATION_SPEED = 0.5;

    private final ScreenChangeRequestCallback screenChangeCallback;
    private final Game engine;

    private final TGraphicCompound container;
    private Menu displayedMenu;

    private final Menu mainMenu;
    private final Menu howToPlay;
    private final Menu credits;

    private final AnimatedSnek snek;
    private final Point snekStartOrigin;

    public MenuScreen(Game game, ScreenChangeRequestCallback screenChangeCallback) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Menus
        mainMenu = new MainMenu(this::onSubmenuSelection);
        howToPlay = new HowToPlay(this::onSubmenuSelection);
        credits = new Credits(this::onSubmenuSelection);

        // Snek
        snek = new AnimatedSnek();
        snekStartOrigin = new Point(Game.WINDOW_DIMENSION.width, Game.WINDOW_CENTER.y - snek.height());
        snek.setOrigin(snekStartOrigin);
        snek.setState(AnimatedSnek.State.MOVING);

        // Graphic
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        displayedMenu = mainMenu;
        container.addAll(displayedMenu, snek);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        displayedMenu.handleKeyEvent(keyEvent);
    }

    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(container);
    }

    @Override
    public void removeFromCanvas() {
        container.removeFromParent();
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.SHOWING_MENU;
    }

    @Override
    public void update(double dtMillis) {
        animateSnek(dtMillis);
        container.update(dtMillis);
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

    private void onSubmenuSelection(SubmenuOption submenuOption) {
        displayedMenu.removeFromParent();

        switch(submenuOption) {
            case ONE_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.SINGLE_PLAYER);
                screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
            }
            case TWO_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.MULTIPLAYER);
                screenChangeCallback.requestScreenChange(ScreenIdentifier.PLAYING);
            }
            case CREDITS -> {
                displayedMenu = credits;
                container.add(credits);
            }
            case HOW_TO_PLAY -> {
                displayedMenu = howToPlay;
                container.add(howToPlay);
            }
            case CLOSE -> {
                displayedMenu = mainMenu;
                container.add(mainMenu);
            }
        }
    }
}
