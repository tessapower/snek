package snake.ui.screens.menu;

import snake.assets.AnimatedSnek;
import snake.game.Game;
import snake.settings.MultiplayerMode;
import snake.settings.Settings;
import snake.ui.screens.Screen;
import snake.ui.screens.ScreenIdentifier;
import tengine.graphics.entities.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The main menu screen and one of the sub-controllers in the program. Responsible for managing the
 * displayed (sub)menu content, and setting the <code>MultiplayerMode</code> in the
 * <code>GameConfig</code> before starting a game.
 *
 * @author Tessa Power
 */
public class MenuScreen implements Screen {
    // Link to main controller
    private final Game engine;

    // Graphical components
    private final TGraphicCompound container;
    private Menu displayedMenu;

    // Menus
    private final Menu mainMenu;
    private final Menu howToPlay;
    private final Menu credits;

    // Snek animation
    private static final double ANIMATION_SPEED = 0.5;
    private final AnimatedSnek snek;
    private final Point snekStartOrigin;

    /**
     * Constructs a new <code>MenuScreen</code> linked to the given main program controller
     * (<code>Game</code>).
     */
    public MenuScreen(Game game) {
        this.engine = game;

        // Menus
        mainMenu = new MainMenu(this::onSubmenuSelection);
        howToPlay = new HowToPlay(this::onSubmenuSelection);
        credits = new Credits(this::onSubmenuSelection);

        // Snek
        snek = AnimatedSnek.animatedSnek();
        snekStartOrigin = new Point(Game.WINDOW_DIMENSION.width, Game.WINDOW_CENTER.y - snek.height());
        snek.setOrigin(snekStartOrigin);
        snek.setState(AnimatedSnek.State.MOVING);

        // Graphic
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        displayedMenu = mainMenu;
        container.addAll(displayedMenu, snek);
    }

    /**
     * Propagates the given <code>KeyEvent</code> to the currently displayed menu.
     */
    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        displayedMenu.handleKeyEvent(keyEvent);
    }

    /**
     * Adds this <code>MenuScreen</code> to the window to be displayed.
     */
    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(container);
    }

    /**
     * Removes this <code>MenuScreen</code> from the window.
     */
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

    /**
     * Animates the little snek moving across the screen.
     */
    private void animateSnek(double dtMillis) {
        if (snek.origin().x < -snek.width()) {
            snek.setOrigin(snekStartOrigin);
        } else {
            Point newOrigin = snek.origin();
            newOrigin.translate((int) (-dtMillis * snek.width() * ANIMATION_SPEED), 0);
            snek.setOrigin(newOrigin);
        }
    }

    /**
     * Callback method used to indicate that a submenu was selected. Handles swapping out the
     * displayed menu, setting the player mode to single player or multiplayer depending on the
     * player's selection, and requesting the main controller to change to a different screen.
     */
    private void onSubmenuSelection(SubmenuOption submenuOption) {
        displayedMenu.removeFromParent();

        switch(submenuOption) {
            case ONE_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.SINGLE_PLAYER);
                engine.requestScreenChange(ScreenIdentifier.PLAYING);
            }
            case TWO_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.MULTIPLAYER);
                engine.requestScreenChange(ScreenIdentifier.PLAYING);
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
