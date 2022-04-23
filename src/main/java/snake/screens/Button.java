package snake.screens;

import snake.assets.Colors;
import snake.assets.FontBook;
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
        setColor(
            switch(state) {
                case FOCUSSED -> Colors.Button.FOCUSSED;
                case UNFOCUSED -> Colors.Button.PRIMARY;
            }
        );
        this.state = state;
    }
}
