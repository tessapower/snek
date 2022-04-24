package snake.ui.screens.gameplay;

import snake.assets.Colors;
import snake.assets.FontBook;
import snake.game.GameState;
import snake.player.PlayerNumber;
import snake.settings.MultiplayerMode;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.shapes.TRect;
import tengine.graphics.entities.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {
    private static final int AVATAR_X_PAD = -2;
    private static final int AVATAR_Y_PAD = -7;

    private final Avatar playerOneAvatar;
    private Avatar playerTwoAvatar = null;

    public HeadsUpDisplay(Dimension dimension, Dimension playAreaDimension, Point playAreaOrigin, GameState state) {
        super(dimension);

        Scoreboard playerOneScoreboard = Scoreboard.playerOneScoreboard(state.playerOne(), state.gameConfig());
        int scoreboardX = playAreaOrigin.x + playAreaDimension.width - playerOneScoreboard.width();
        int scoreboardY = playAreaOrigin.y - playerOneScoreboard.height();
        playerOneScoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

        // Add avatar
        playerOneAvatar = new Avatar(PlayerNumber.PLAYER_ONE);
        playerOneAvatar.setOrigin(new Point(AVATAR_X_PAD, playAreaOrigin.y - playerOneAvatar.height() + AVATAR_Y_PAD));

        // If two player, add player two lives and score and move avatar to center
        if (state.gameConfig().multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwoAvatar = new Avatar(PlayerNumber.PLAYER_TWO);

            Scoreboard playerTwoScoreboard = Scoreboard.playerTwoScoreboard(state.playerTwo());
            scoreboardX = playAreaOrigin.x;
            scoreboardY = playAreaOrigin.y - playerOneScoreboard.height();
            playerTwoScoreboard.setOrigin(new Point(scoreboardX, scoreboardY));
            playerTwoAvatar.setOrigin(new Point(playerTwoScoreboard.x() + playerTwoAvatar.width() / 2, playerOneAvatar.snek.y()));

            playerOneAvatar.setOrigin(new Point(playerOneScoreboard.x() - playerOneAvatar.width(), playerOneAvatar.snek.y()));

            addAll(playerTwoScoreboard, playerTwoAvatar.snek);
        }

        // Add border
        TRect border = new TRect(new Dimension(playAreaDimension.width + 4, playAreaDimension.height + 4));
        border.setOrigin(new Point(playAreaOrigin.x - 2, playAreaOrigin.y - 2));
        border.outlineColor = Colors.SNEK_GREEN;

        // Add pause instruction
        TLabel pauseLabel = new TLabel("p: pause");
        pauseLabel.setFont(FontBook.shared().bodyFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new Point(playAreaOrigin.x + playAreaDimension.width - 85,
                playAreaOrigin.y + playAreaDimension.height + 15));

        addAll(border, playerOneAvatar.snek, playerOneScoreboard, pauseLabel);
    }

    public void animateAvatar(PlayerNumber playerNumber) {
        switch(playerNumber) {
            case PLAYER_ONE -> playerOneAvatar.eat();
            case PLAYER_TWO -> {
                if (playerTwoAvatar != null) {
                    playerTwoAvatar.eat();
                }
            }
        }
    }
}
