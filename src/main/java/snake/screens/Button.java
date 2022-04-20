package snake.screens;

import snake.Colors;
import snake.FontBook;
import tengine.graphics.graphicsObjects.text.TLabel;

public class Button extends TLabel {
    public enum State {
        FOCUSSED, UNFOCUSED
    }

    private State state;

    public Button(String text) {
        super(text);
        setState(State.UNFOCUSED);
        setFont(FontBook.shared().buttonFont());
    }

    public State state() {
        return state;
    }

    public void setState(State state) {
        setColor(state == State.FOCUSSED ? Colors.Button.FOCUSSED : Colors.Button.PRIMARY);
        this.state = state;
    }
}
