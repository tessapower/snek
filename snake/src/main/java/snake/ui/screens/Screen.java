package snake.ui.screens;

import java.awt.event.KeyEvent;

/**
 * Represents a screen in the game, e.g. Menu screen, Game over screen, etc.
 */
public interface Screen {
    /**
     * Handle the given <code>KeyEvent</code>, usually propagated by the main controller of the
     * program.
     */
    void handleKeyEvent(KeyEvent event);

    /**
     * Add this <code>Screen</code> to the <code>GameWorld</code> canvas.
     */
    void addToCanvas();

    /**
     * Remove this <code>Screen</code> from the <code>GameWorld</code> canvas.
     */
    void removeFromCanvas();

    /**
     * The identifier for this <code>Screen</code>.
     *
     * @see ScreenIdentifier
     */
    ScreenIdentifier screen();

    /**
     * Allow this <code>Screen</code> to update itself since it was last updated
     * <code>dtMillis</code> ago.
     */
    void update(double dtMillis);
}
