package snake.ui.screens.menu;

import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Represents a graphical container for the content of a given (sub)menu.
 *
 * @author Tessa Power
 * @see MainMenu
 */
public abstract class Menu extends TGraphicCompound {
    final SubmenuSelectionNotifier submenuSelectionNotifier;

    /**
     * Constructs a new <code>Menu</code> with the given <code>SubmenuSelectionNotifier</code>
     * callback method.
     *
     * @see SubmenuOption
     * @see SubmenuSelectionNotifier
     */
    public Menu(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(new Dimension());

        this.submenuSelectionNotifier = submenuSelectionNotifier;
    }

    /**
     * Handles the given <code>KeyEvent</code> appropriately.
     */
    abstract public void handleKeyEvent(KeyEvent keyEvent);
}
