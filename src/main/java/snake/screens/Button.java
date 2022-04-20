package snake.screens;

import snake.Colors;
import snake.FontBook;
import tengine.graphics.graphicsObjects.text.TLabel;

public class Button extends TLabel {
    public enum ButtonState {
        FOCUSSED, UNFOCUSED
    }

    private ButtonState state;

    public Button(String text) {
        super(text);
        setState(ButtonState.UNFOCUSED);
        setFont(FontBook.shared().buttonFont());
    }

    public ButtonState state() {
        return state;
    }

    public void setState(ButtonState state) {
        setColor(state == ButtonState.FOCUSSED ? Colors.Button.FOCUSSED : Colors.Button.PRIMARY);
        this.state = state;
    }
}
