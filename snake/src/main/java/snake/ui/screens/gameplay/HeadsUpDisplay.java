package snake.ui.screens.gameplay;

import snake.assets.AppleSprite;
import snake.assets.AssetLoader;
import snake.assets.Colors;
import snake.assets.FontBook;
import snake.game.GameConfig;
import snake.game.GameState;
import snake.player.Player;
import snake.player.PlayerNumber;
import snake.settings.MultiplayerMode;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.shapes.TRect;
import tengine.graphics.entities.sprites.Sprite;
import tengine.graphics.entities.text.TLabel;

import java.awt.*;
import java.util.ArrayList;

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

    static class Scoreboard extends TGraphicCompound {
        private static final String HEART = "heart.png";
        private static final String HEART_P1 = "heart-p1.png";
        private static final String HEART_P2 = "heart-p2.png";
        private static final Dimension HEART_DIMENSION = new Dimension(16, 16);
        private static final Dimension DIMENSION = new Dimension(80, 50);

        private final Player player;
        private final TLabel appleCount;
        private final ArrayList<Sprite> hearts;

        public static Scoreboard playerOneScoreboard(Player player, GameConfig gameConfig) {
            String heart = switch(gameConfig.multiplayerMode()) {
                case SINGLE_PLAYER -> HEART;
                case MULTIPLAYER -> HEART_P1;
            };

            return new Scoreboard(player, heart);
        }

        public static Scoreboard playerTwoScoreboard(Player player) {
            return new Scoreboard(player, HEART_P2);
        }

        private Scoreboard(Player player, String heart) {
            super(DIMENSION);

            this.player = player;

            AppleSprite apple = AppleSprite.goodApple();
            apple.setOrigin(new Point(0, DIMENSION.height - (int)(apple.height() * 1.5)));

            appleCount = new TLabel("");
            appleCount.setFont(FontBook.shared().scoreBoardFont());
            appleCount.setColor(Colors.Text.HIGHLIGHTED);
            appleCount.setOrigin(new Point((int) (apple.width() * 1.5), apple.y() + apple.height()));

            hearts = new ArrayList<>(player.livesLeft());
            for (var i = 0; i < player.livesLeft(); i++) {
                Sprite heartSprite = new Sprite(AssetLoader.load(heart), HEART_DIMENSION);
                heartSprite.setOrigin(new Point(appleCount.x() - 2 + (i * (heartSprite.width() + 2)),
                        apple.y() - apple.height() - 5));
                hearts.add(heartSprite);
                add(heartSprite);
            }

            addAll(apple, appleCount);
        }

        private String padScoreText(int score) {
            if (score < 10) {
                return "00" + score;
            } else if (score < 100) {
                return "0" + score;
            }

            return "" + score;
        }

        @Override
        public void update(double dtMillis) {
            appleCount.setText(padScoreText(player.score()));

            if (player.livesLeft() < hearts.size()) {
                Sprite heart = hearts.remove(0);
                heart.removeFromParent();
            }

            super.update(dtMillis);
        }
    }
}
