package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.screens.Button;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Credits extends Menu {
    public Credits(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("credits");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(90, 90));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(110, 245));

        addAll(title, close);
    }

    public void initContent() {
        // Our text drawing functions don't currently support
        // drawing multiline strings, hence the following ugliness.
        TLabel animatedSnakeAttr1 = bodyText("Animated Snake by Calciumtrice, usable");
        TLabel animatedSnakeAttr2 = bodyText("under Creative Commons Attribution 3.0");
        animatedSnakeAttr1.setOrigin(new Point(30, 150));
        animatedSnakeAttr2.setOrigin(new Point(30, 160));

        TLabel soundsAttr1 = bodyText("Sounds by 8bitlalaland, usuable under");
        TLabel soundsAttr2 = bodyText("Creative Commons Attribution 3.0");
        soundsAttr1.setOrigin(new Point(30, 175));
        soundsAttr2.setOrigin(new Point(40, 185));

        TLabel fontAttr = bodyText("Retro Gaming Font by Damyrius");
        fontAttr.setOrigin(new Point(50, 200));

        TLabel artworkByMe = bodyText("Original pixel artwork by Tessa Power");
        artworkByMe.setOrigin(new Point(35, 215));

        addAll(animatedSnakeAttr1, animatedSnakeAttr2, soundsAttr1, soundsAttr2, fontAttr, artworkByMe);
    }

    private TLabel bodyText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().bodyFont());

        return line;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            submenuSelectionNotifier.notifySelection(SubmenuOption.CLOSE);
        }
    }
}
