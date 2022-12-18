package snake.ui.screens.menu;

/**
 * A callback method used to notify the receiver which <code>SubmenuOption</code> has been
 * selected by the player.
 *
 * @author Tessa Power
 * @see SubmenuOption
 */
@FunctionalInterface
public interface SubmenuSelectionNotifier {
    void notifySelection(SubmenuOption submenu);
}
