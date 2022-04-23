package snake.ui.screens.menu;

import snake.assets.Colors;
import snake.assets.FontBook;
import snake.assets.SoundEffects;
import snake.ui.components.Button;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

class HowToPlay extends Menu {
    public HowToPlay(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("how to play");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(140, 180));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(220, 490));

        addAll(title, close);
    }

    public void initContent() {
        TLabel onePlayer = bodyText("PLAYER 1");
        TLabel onePlayerKeys = bodyText("use the arrow keys to move snek");
        onePlayer.setOrigin(new Point(210, 300));
        onePlayerKeys.setOrigin(new Point(90, 320));

        TLabel twoPlayer = bodyText("PLAYER 2");
        TLabel twoPlayerKeys = bodyText("use the W-A-S-D keys to move snek");
        twoPlayer.setOrigin(new Point(210, 350));
        twoPlayerKeys.setOrigin(new Point(80, 370));

        TLabel infiniteMode = bodyText("INFINITE MODE");
        TLabel infiniteModeText = bodyText("turn on to let your tail grow forever!");
        infiniteMode.setOrigin(new Point(180, 400));
        infiniteModeText.setOrigin(new Point(70, 420));

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
            SoundEffects.shared().menuSelect().play();
            submenuSelectionNotifier.notifySelection(SubmenuOption.CLOSE);
        }
    }
}
