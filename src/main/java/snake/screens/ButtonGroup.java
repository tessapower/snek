package snake.screens;

import snake.assets.SoundEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a quick and dirty "radio-group-like" management for buttons, where only a single
 * button can be focussed at a time. This suffices for all the use cases in snek.
 */
public class ButtonGroup {
    private final List<Button> buttons;
    private int focusIdx;

    public ButtonGroup(Button... buttons) {
        this.buttons = new ArrayList<>(buttons.length);
        this.buttons.addAll(Arrays.asList(buttons));
        setFocussed(this.buttons.get(0));
        focusIdx = 0;
    }

    public void next() {
        if (focusIdx < buttons.size() - 1) {
            SoundEffects.shared().menuMove().play();
            buttons.get(focusIdx).setState(Button.State.UNFOCUSED);
            focusIdx++;
            buttons.get(focusIdx).setState(Button.State.FOCUSSED);
        }
    }

    public void previous() {
        if (focusIdx > 0) {
            SoundEffects.shared().menuMove().play();
            buttons.get(focusIdx).setState(Button.State.UNFOCUSED);
            focusIdx--;
            buttons.get(focusIdx).setState(Button.State.FOCUSSED);
        }
    }

    public Button getFocussed() {
        return buttons.get(focusIdx);
    }

    public void setFocussed(Button button) {
        for (var b : buttons) {
            if (b.equals(button)) {
                b.setState(Button.State.FOCUSSED);
            } else {
                b.setState(Button.State.UNFOCUSED);
            }
        }
    }
}
