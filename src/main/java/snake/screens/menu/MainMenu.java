package snake.screens.menu;

import snake.Colors;
import snake.FontBook;
import snake.screens.Button;
import snake.screens.ButtonGroup;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;

class MainMenu extends TGraphicCompound {
    SubmenuSelectionNotifier submenuSelectionNotifier;
    TLabel mainTitle;

    ButtonGroup menuButtons;
    Button onePlayer;
    Button twoPlayer;
    Button infiniteMode;
    Button howToPlay;
    Button credits;

   public MainMenu(SubmenuSelectionNotifier submenuSelectionNotifier) {
       super(new Dimension());

       this.submenuSelectionNotifier = submenuSelectionNotifier;

       // Titles
       mainTitle = new TLabel("snek!");
       mainTitle.setColor(Colors.Text.PRIMARY);
       mainTitle.setFont(FontBook.shared().titleFont());
       // The origin of text is unfortunately manual as we cannot query
       // the size of the text beforehand to properly align it
       mainTitle.setOrigin(new Point(100, 90));

       onePlayer = new Button("one player");
       onePlayer.setOrigin(new Point(95, 150));

       twoPlayer = new Button("two player");
       twoPlayer.setOrigin(new Point(95, 170));

       infiniteMode = new Button("infinite mode");
       infiniteMode.setOrigin(new Point(90, 190));

       howToPlay = new Button("how to play");
       howToPlay.setOrigin(new Point(95, 210));

       credits = new Button("credits");
       credits.setOrigin(new Point(105, 230));

       menuButtons = new ButtonGroup(onePlayer, twoPlayer, infiniteMode, howToPlay, credits);

       addAll(mainTitle, onePlayer, twoPlayer, infiniteMode, howToPlay, credits);
   }

    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> menuButtons.previous();
            case KeyEvent.VK_DOWN -> menuButtons.next();
            case KeyEvent.VK_ENTER -> {
                Button focussed = menuButtons.getFocussed();
                if (focussed.equals(onePlayer)) {
                   submenuSelectionNotifier.notifySelection(SubmenuOption.ONE_PLAYER);
                } else if (focussed.equals(twoPlayer)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.TWO_PLAYER);
                } else if (focussed.equals(infiniteMode)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.INFINITE_MODE);
                } else if (focussed.equals(howToPlay)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.HOW_TO_PLAY);
                } else if (focussed.equals(credits)) {
                    submenuSelectionNotifier.notifySelection(SubmenuOption.CREDITS);
                }
            }
        }
    }

}
