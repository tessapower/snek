package snake.ui.screens.gameplay;

import snake.assets.Colors;
import snake.assets.FontBook;
import snake.game.GameState;
import snake.player.PlayerNumber;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

/**
 * A HUD that shows the player <code>Scoreboard</code>s and <code>Avatar</code>s. Updated during
 * the game and animates the <code>Avatar</code>s in response to players scoring points.
 *
 * @author Tessa Power
 * @see Avatar
 * @see Scoreboard
 * @see PlayGameScreen
 */
public class HeadsUpDisplay extends TGraphicCompound {
    // Padding to keep the avatars a certain distance away from the scoreboards
    private static final int AVATAR_X_PAD = -2;
    private static final int AVATAR_Y_PAD = -7;

    private final Avatar p1Avatar;
    private Avatar p2Avatar = null;

    /**
     * Constructs a new HUD of the given <code>Dimension</code>, taking into account the
     * dimension and origin of where the game is being played.
     */
    public HeadsUpDisplay(Dimension dimension, Dimension playAreaDimension, Point playAreaOrigin, GameState state) {
        super(dimension);

        Scoreboard p1Scoreboard = Scoreboard.playerOneScoreboard(state.playerOne(), state.gameConfig());
        int scoreboardX = playAreaOrigin.x + playAreaDimension.width - p1Scoreboard.width();
        int scoreboardY = playAreaOrigin.y - p1Scoreboard.height();
        p1Scoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

        // Add avatar
        p1Avatar = new Avatar(PlayerNumber.PLAYER_ONE);
        int avatarY = playAreaOrigin.y - p1Avatar.height() + AVATAR_Y_PAD;

        switch(state.gameConfig().multiplayerMode()) {
            case SINGLE_PLAYER ->
                p1Avatar.setOrigin(new Point(AVATAR_X_PAD, avatarY));
            case MULTIPLAYER -> {
                p2Avatar = new Avatar(PlayerNumber.PLAYER_TWO);

                Scoreboard p2Scoreboard = Scoreboard.playerTwoScoreboard(state.playerTwo());
                scoreboardX = playAreaOrigin.x;
                scoreboardY = playAreaOrigin.y - p1Scoreboard.height();
                p2Scoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

                p1Avatar.setOrigin(new Point(p1Scoreboard.x() - p1Avatar.width() + 20, avatarY));
                p2Avatar.setOrigin(new Point(p2Scoreboard.x() + p2Scoreboard.width() - 20, avatarY));

                addAll(p2Scoreboard, p2Avatar.snek);
            }
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

        addAll(border, p1Avatar.snek, p1Scoreboard, pauseLabel);
    }

    /**
     * Animates the <code>Avatar</code> of the given player.
     *
     * @see Avatar
     * @see PlayerNumber
     */
    public void animateAvatar(PlayerNumber playerNumber) {
        switch(playerNumber) {
            case PLAYER_ONE -> p1Avatar.eat();
            case PLAYER_TWO -> {
                if (p2Avatar != null) {
                    p2Avatar.eat();
                }
            }
        }
    }
}
