package snake.assets;

import java.awt.*;

/**
 * Container class for colors used for UI elements in <code>snek!</code>
 *
 * @author Tessa Power
 */
public class Colors {
    public static final Color SNEK_GREEN = new Color(119, 167, 117);
    public static final Color SNEK_RED = new Color(230, 82, 83);

    /**
     * Colors used for standard and highlighted text.
     *
     * @see Colors
     * @see Colors.Button
     */
    public static class Text {
        public static final Color PRIMARY = SNEK_GREEN;
        public static final Color HIGHLIGHTED = SNEK_RED;
    }

    /**
     * Colors used for standard and highlighted buttons.
     *
     * @see Colors
     * @see Colors.Text
     */
    public static class Button {
        public static final Color PRIMARY = SNEK_GREEN;
        public static final Color FOCUSSED = SNEK_RED;
    }
}
