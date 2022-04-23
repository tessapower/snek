package snake.screens.menu;

import tengine.graphics.graphicsObjects.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Menu extends TGraphicCompound {
    final SubmenuSelectionNotifier submenuSelectionNotifier;

    public Menu(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(new Dimension());

        this.submenuSelectionNotifier = submenuSelectionNotifier;
    }

    abstract public void handleKeyEvent(KeyEvent keyEvent);
}
