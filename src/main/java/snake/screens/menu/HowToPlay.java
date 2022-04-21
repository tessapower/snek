package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.screens.Button;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

class HowToPlay extends Menu {
    public HowToPlay(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("how to play");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(70, 90));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(110, 245));

        addAll(title, close);
    }

    public void initContent() {
        TLabel onePlayer = bodyText("PLAYER 1");
        TLabel onePlayerKeys = bodyText("use the arrow keys to move snek");
        onePlayer.setOrigin(new Point(105, 150));
        onePlayerKeys.setOrigin(new Point(45, 160));

        TLabel twoPlayer = bodyText("PLAYER 2");
        TLabel twoPlayerKeys = bodyText("use the W-A-S-D keys to move snek");
        twoPlayer.setOrigin(new Point(105, 175));
        twoPlayerKeys.setOrigin(new Point(40, 185));

        TLabel infiniteMode = bodyText("INFINITE MODE");
        TLabel infiniteModeText = bodyText("turn on to let your tail grow forever!");
        infiniteMode.setOrigin(new Point(90, 200));
        infiniteModeText.setOrigin(new Point(15, 210));

        addAll(onePlayer, onePlayerKeys, twoPlayer, twoPlayerKeys, infiniteMode, infiniteModeText);
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
