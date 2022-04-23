package snake.ui.components;

import snake.assets.Colors;
import snake.assets.FontBook;
import tengine.graphics.graphicsObjects.text.TLabel;

public class Button extends TLabel {
    public enum State {
        FOCUSSED, UNFOCUSED
    }

    public Button(String text) {
        super(text);
        setState(State.UNFOCUSED);
        setFont(FontBook.shared().buttonFont());
    }

    public void setState(State state) {
        setColor(
            switch(state) {
                case FOCUSSED -> Colors.Button.FOCUSSED;
                case UNFOCUSED -> Colors.Button.PRIMARY;
            }
        );
    }
}
