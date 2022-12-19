package snake.ui.components;

import snake.assets.Colors;
import snake.assets.FontBook;
import tengine.graphics.components.text.TLabel;

/**
 * A simple text button that can have a focussed or unfocused state.
 */
public class Button extends TLabel {
    /**
     * The two possible states that a <code>Button</code> can have, focussed or unfocused.
     */
    public enum State {
        FOCUSSED, UNFOCUSED
    }

    /**
     * Constructs a new <code>Button</code> with the given string. The initial <code>State</code>
     * is unfocused.
     */
    public Button(String text) {
        super(text);
        setState(State.UNFOCUSED);
        setFont(FontBook.shared().buttonFont());
    }

    /**
     * Set the <code>State</code> of this <code>Button</code>.
     */
    public void setState(State state) {
        setColor(
            switch(state) {
                case FOCUSSED -> Colors.Button.FOCUSSED;
                case UNFOCUSED -> Colors.Button.PRIMARY;
            }
        );
    }
}
