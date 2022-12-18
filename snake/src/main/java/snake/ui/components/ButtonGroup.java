package snake.ui.components;

import snake.assets.SoundEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A quick and dirty "radio-group-like" management for buttons, where only a single button can be
 * focussed at a time. This suffices for all the use cases in snek.
 *
 * @author Tessa Power
 * @see Button
 */
public class ButtonGroup {
    private final List<Button> buttons;
    private int focusIdx;

    /**
     * Create a new <code>ButtonGroup</code> containing the given <code>Button</code>s. Buttons
     * will be navigated in the same order they are passed in, so their physical position on the
     * screen should ideally match this ordering. The first button will be automatically set as
     * the focussed button, change this by calling <code>setFocussed</code> on the
     * <code>ButtonGroup</code>.
     */
    public ButtonGroup(Button... buttons) {
        this.buttons = new ArrayList<>(buttons.length);
        this.buttons.addAll(Arrays.asList(buttons));
        setFocussed(this.buttons.get(0));
        focusIdx = 0;
    }

    /**
     * Focus the next <code>Button</code> in this <code>ButtonGroup</code>. Stops when it
     * reaches the end, i.e. does not wrap around to the beginning.
     */
    public void next() {
        if (focusIdx < buttons.size() - 1) {
            SoundEffects.shared().menuMove().play();
            buttons.get(focusIdx).setState(Button.State.UNFOCUSED);
            focusIdx++;
            buttons.get(focusIdx).setState(Button.State.FOCUSSED);
        }
    }

    /**
     * Focus the previous <code>Button</code> in this <code>ButtonGroup</code>. Stops when it
     * reaches the beginning, i.e. does not wrap around to the end.
     */
    public void previous() {
        if (focusIdx > 0) {
            SoundEffects.shared().menuMove().play();
            buttons.get(focusIdx).setState(Button.State.UNFOCUSED);
            focusIdx--;
            buttons.get(focusIdx).setState(Button.State.FOCUSSED);
        }
    }

    /**
     * Returns the currently focussed <code>Button</code> in this <code>ButtonGroup</code>.
     */
    public Button getFocussed() {
        return buttons.get(focusIdx);
    }

    /**
     * Set the currently focussed <code>Button</code> in this <code>ButtonGroup</code>. Does
     * nothing if the given <code>Button</code> doesn't belong to this <code>ButtonGroup</code>.
     */
    public void setFocussed(Button button) {
        if (buttons.contains(button)) {
            for (var b : buttons) {
                if (b.equals(button)) {
                    b.setState(Button.State.FOCUSSED);
                } else {
                    b.setState(Button.State.UNFOCUSED);
                }
            }
        }
    }
}
