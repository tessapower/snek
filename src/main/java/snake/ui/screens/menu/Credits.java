package snake.ui.screens.menu;

import snake.assets.Colors;
import snake.assets.FontBook;
import snake.assets.SoundEffects;
import snake.ui.components.Button;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

class Credits extends Menu {
    public Credits(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("credits");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(180, 180));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(220, 490));

        addAll(title, close);
    }

    public void initContent() {
        // Our text drawing functions don't currently support
        // drawing multiline strings, hence the following ugliness.
        TLabel animatedSnakeAttr1 = bodyText("Animated Snake by Calciumtrice, usable");
        TLabel animatedSnakeAttr2 = bodyText("under Creative Commons Attribution 3.0");
        animatedSnakeAttr1.setOrigin(new Point(60, 280));
        animatedSnakeAttr2.setOrigin(new Point(60, 300));

        TLabel soundsAttr1 = bodyText("Sound Effects by Juhani Junkala,");
        TLabel soundsAttr2 = bodyText("usable under CC0 Creative Commons License");
        soundsAttr1.setOrigin(new Point(80, 330));
        soundsAttr2.setOrigin(new Point(40, 350));

        TLabel musicAttr1 = bodyText("Music by Steven Melin, usable");
        TLabel musicAttr2 = bodyText("under CC0 Creative Commons License");
        musicAttr1.setOrigin(new Point(100, 380));
        musicAttr2.setOrigin(new Point(60, 400));

        TLabel fontAttr = bodyText("Retro Gaming Font by Damyrius");
        fontAttr.setOrigin(new Point(100, 430));

        TLabel artworkByMe = bodyText("Original pixel artwork by Tessa Power");
        artworkByMe.setOrigin(new Point(70, 460));

        addAll(animatedSnakeAttr1, animatedSnakeAttr2, soundsAttr1, soundsAttr2, musicAttr1, musicAttr2, fontAttr, artworkByMe);
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
            SoundEffects.shared().menuSelect().play();
            submenuSelectionNotifier.notifySelection(SubmenuOption.CLOSE);
        }
    }
}
