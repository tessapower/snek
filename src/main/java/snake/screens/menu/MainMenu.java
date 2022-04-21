package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.GameMode;
import snake.Settings;
import snake.screens.Button;
import snake.screens.ButtonGroup;
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

    private boolean infiniteEnabled = false;

   public MainMenu(SubmenuSelectionNotifier submenuSelectionNotifier) {
       super(submenuSelectionNotifier);

       TLabel title = new TLabel("snek!");
       title.setColor(Colors.Text.PRIMARY);
       title.setFont(FontBook.shared().titleFont());
       // The origin of text is unfortunately manual as we cannot query
       // the size of the text beforehand to properly align it
       title.setOrigin(new Point(100, 90));

       onePlayer = new Button("one player");
       onePlayer.setOrigin(new Point(95, 150));

       twoPlayer = new Button("two player");
       twoPlayer.setOrigin(new Point(95, 170));

       infiniteMode = new Button("infinite mode: " + (infiniteEnabled ? "on" : "off"));
       infiniteMode.setOrigin(new Point(80, 190));

       howToPlay = new Button("how to play");
       howToPlay.setOrigin(new Point(95, 210));

       credits = new Button("credits");
       credits.setOrigin(new Point(105, 230));

       buttons = new ButtonGroup(onePlayer, twoPlayer, infiniteMode, howToPlay, credits);

       addAll(title, onePlayer, twoPlayer, infiniteMode, howToPlay, credits);
   }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> buttons.previous();
            case KeyEvent.VK_DOWN -> buttons.next();
            case KeyEvent.VK_ENTER -> {
                Button focussed = buttons.getFocussed();
                if (focussed.equals(onePlayer)) {
                   submenuSelectionNotifier.notifySelection(SubmenuOption.ONE_PLAYER);
                } else if (focussed.equals(twoPlayer)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.TWO_PLAYER);
                } else if (focussed.equals(infiniteMode)) {
                    infiniteEnabled = !infiniteEnabled;
                    Settings.shared().setGameMode(infiniteEnabled ? GameMode.INFINITE : GameMode.NORMAL);
                    infiniteMode.setText("infinite mode: " + (infiniteEnabled ? "on" : "off"));
                } else if (focussed.equals(howToPlay)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.HOW_TO_PLAY);
                } else if (focussed.equals(credits)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.CREDITS);
                }
            }
        }
    }
}
