package snake.screens.gameplay;

import snake.GameConfig;
import snake.MultiplayerMode;
import snake.actors.apple.AppleSprite;
import snake.assets.Colors;
import snake.assets.FontBook;
import snake.player.Player;
import snake.utils.ResourceLoader;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.util.ArrayList;

public class HeadsUpDisplay extends TGraphicCompound {
    private static final int AVATAR_X_PAD = -2;
    private static final int AVATAR_Y_PAD = -7;

    private final Avatar avatar;
    private final Scoreboard playerOneScoreboard;
    private Scoreboard playerTwoScoreboard = null;
    private final GameState state;
    
    public HeadsUpDisplay(Dimension dimension, Dimension playAreaDimension, Point playAreaOrigin, GameState state) {
        super(dimension);

        this.state = state;

        playerOneScoreboard = Scoreboard.playerOneScoreboard(state.playerOneState(), state.gameConfig());
        int scoreboardX = playAreaOrigin.x + playAreaDimension.width - playerOneScoreboard.width();
        int scoreboardY = playAreaOrigin.y - playerOneScoreboard.height();
        playerOneScoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

        // Add avatar
        // TODO: Pause updating HUD when game is paused
        avatar = new Avatar();
        avatar.setOrigin(new Point(AVATAR_X_PAD, playAreaOrigin.y - avatar.height() + AVATAR_Y_PAD));

        // If two player, add player two lives and score and move avatar to center
        if (state.gameConfig().multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwoScoreboard = Scoreboard.playerTwoScoreboard(state.playerTwoState());
            scoreboardX = playAreaOrigin.x;
            scoreboardY = playAreaOrigin.y - playerOneScoreboard.height();
            playerTwoScoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

            avatar.setOrigin(new Point((int) ((dimension.width - avatar.width()) * 0.5), avatar.snek.y()));

            add(playerTwoScoreboard);
        }

        // Add border
        TRect border = new TRect(playAreaDimension);
        // TODO: maybe add a pixelated border?
        border.setOrigin(playAreaOrigin);
        border.outlineColor = Colors.SNEK_GREEN;

        // Add pause instruction
        TLabel pauseLabel = new TLabel("p: pause");
        pauseLabel.setFont(FontBook.shared().bodyFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new Point(playAreaOrigin.x + playAreaDimension.width - 85,
                playAreaOrigin.y + playAreaDimension.height + 15));

        addAll(border, avatar.snek, playerOneScoreboard, pauseLabel);
    }

    @Override
    public void update(double dtMillis) {
        playerOneScoreboard.updateScoreboard(state.playerOneState());

        if (state.gameConfig().multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwoScoreboard.updateScoreboard(state.playerTwoState());
        }

        super.update(dtMillis);
    }

    public void animateAvatar() {
        avatar.eat();
    }

    static class Scoreboard extends TGraphicCompound {
        private static final String HEART = "heart.png";
        private static final String HEART_P1 = "heart-p1.png";
        private static final String HEART_P2 = "heart-p2.png";
        private static final Dimension HEART_DIMENSION = new Dimension(16, 16);
        private static final Dimension DIMENSION = new Dimension(80, 50);

        private final TLabel appleCount;
        private final ArrayList<Sprite> hearts;

        public static Scoreboard playerOneScoreboard(PlayerState playerState, GameConfig gameConfig) {
            String heart = (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) ? HEART_P1 : HEART;

            return new Scoreboard(playerState, heart);
        }

        public static Scoreboard playerTwoScoreboard(PlayerState playerState) {
            return new Scoreboard(playerState, HEART_P2);
        }

        private Scoreboard(PlayerState playerState, String heart) {
            super(DIMENSION);

            AppleSprite apple = AppleSprite.goodApple();
            apple.setOrigin(new Point(0, DIMENSION.height - (int)(apple.height() * 1.5)));

            appleCount = new TLabel("");
            appleCount.setFont(FontBook.shared().scoreBoardFont());
            appleCount.setColor(Colors.Text.HIGHLIGHTED);
            appleCount.setOrigin(new Point((int) (apple.width() * 1.5), apple.y() + apple.height()));

            hearts = new ArrayList<>(playerState.livesLeft());

            for (var i = 0; i < playerState.livesLeft(); i++) {
                Sprite heartSprite = new Sprite(ResourceLoader.load(heart), HEART_DIMENSION);
                heartSprite.setOrigin(new Point(appleCount.x() - 2 + (i * (heartSprite.width() + 2)),
                        apple.y() - apple.height() - 5));
                hearts.add(heartSprite);
                add(heartSprite);
            }

            updateScoreboard(playerState);

            addAll(apple, appleCount);
        }

        public void updateScoreboard(PlayerState playerState) {
            appleCount.setText(padScoreText(playerState.score()));

            if (playerState.livesLeft() < hearts.size()) {
                Sprite heart = hearts.remove(0);
                heart.removeFromParent();
            }
        }

        private String padScoreText(int score) {
            if (score < 10) {
                return "00" + score;
            } else if (score < 100) {
                return "0" + score;
            }

            return "" + score;
        }
    }
}
