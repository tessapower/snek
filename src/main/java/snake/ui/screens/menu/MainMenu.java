package snake.ui.screens.menu;

import snake.assets.Colors;
import snake.assets.FontBook;
import snake.assets.SoundEffects;
import snake.settings.Settings;
import snake.ui.components.Button;
import snake.ui.components.ButtonGroup;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

class MainMenu extends Menu {
    private final ButtonGroup buttons;
    private final Button onePlayer;
    private final Button twoPlayer;
    private final Button infiniteMode;
    private final Button howToPlay;
    private final Button credits;

    public MainMenu(SubmenuSelectionNotifier submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("snek!");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(200, 180));

        onePlayer = new Button("one player");
        onePlayer.setOrigin(new Point(190, 300));

        twoPlayer = new Button("two player");
        twoPlayer.setOrigin(new Point(190, 340));

        infiniteMode = new Button("");
        infiniteMode.setOrigin(new Point(160, 380));

        howToPlay = new Button("how to play");
        howToPlay.setOrigin(new Point(190, 420));

        credits = new Button("credits");
        credits.setOrigin(new Point(210, 460));

        buttons = new ButtonGroup(onePlayer, twoPlayer, infiniteMode, howToPlay, credits);

        addAll(title, onePlayer, twoPlayer, infiniteMode, howToPlay, credits);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> buttons.previous();
            case KeyEvent.VK_DOWN -> buttons.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                Button focussed = buttons.getFocussed();
                if (focussed.equals(onePlayer)) {
                   submenuSelectionNotifier.notifySelection(SubmenuOption.ONE_PLAYER);
                } else if (focussed.equals(twoPlayer)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.TWO_PLAYER);
                } else if (focussed.equals(infiniteMode)) {
                    Settings settings = Settings.shared();
                    settings.setGameMode(settings.gameMode().toggle());
                } else if (focussed.equals(howToPlay)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.HOW_TO_PLAY);
                } else if (focussed.equals(credits)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.CREDITS);
                }
            }
        }
    }

    @Override
    public void update(double dtMillis) {
        infiniteMode.setText("infinite mode: " +
                switch(Settings.shared().gameMode()) {
                    case NORMAL -> "off";
                    case INFINITE -> "on";
                });

        super.update(dtMillis);
    }
}
