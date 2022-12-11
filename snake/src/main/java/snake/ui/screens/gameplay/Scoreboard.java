package snake.ui.screens.gameplay;

import snake.assets.AppleSprite;
import snake.assets.AssetLoader;
import snake.assets.Colors;
import snake.assets.FontBook;
import snake.game.GameConfig;
import snake.player.Player;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.sprites.Sprite;
import tengine.graphics.entities.text.TLabel;

import java.awt.*;
import java.util.ArrayList;

class Scoreboard extends TGraphicCompound {
    private static final String HEART = "heart.png";
    private static final String HEART_P1 = "heart-p1.png";
    private static final String HEART_P2 = "heart-p2.png";
    private static final Dimension HEART_DIMENSION = new Dimension(16, 16);
    private static final Dimension DIMENSION = new Dimension(75, 50);

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
        int appleX = switch(player.playerNumber()) {
            case PLAYER_ONE -> 0;
            case PLAYER_TWO -> dimension.width - apple.width();
        };
        apple.setOrigin(new Point(appleX, DIMENSION.height - (int)(apple.height() * 1.5)));

        appleCount = new TLabel("");
        appleCount.setFont(FontBook.shared().scoreBoardFont());
        appleCount.setColor(Colors.Text.HIGHLIGHTED);

        int appleCountX = switch(player.playerNumber()) {
            case PLAYER_ONE -> (int) (apple.width() * 1.5);
            case PLAYER_TWO -> 0;
        };

        appleCount.setOrigin(new Point(appleCountX, apple.y() + apple.height()));

        hearts = new ArrayList<>(player.livesLeft());
        int heartXPadding = switch(player.playerNumber()) {
            case PLAYER_ONE -> -2;
            case PLAYER_TWO -> 0;
        };

        for (var i = 0; i < player.livesLeft(); i++) {
            Sprite heartSprite = new Sprite(AssetLoader.load(heart), HEART_DIMENSION);
            heartSprite.setOrigin(new Point(appleCount.x() + heartXPadding + (i * (heartSprite.width() + 2)),
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