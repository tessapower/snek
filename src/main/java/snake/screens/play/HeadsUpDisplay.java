package snake.screens.play;

import snake.Colors;
import snake.FontBook;
import snake.apple.AppleSprite;
import snake.player.PlayerState;
import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.sprites.Sprite;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;
import java.util.ArrayList;

public class HeadsUpDisplay extends TGraphicCompound {
    Scoreboard playerOneScoreboard;
    Scoreboard playerTwoScoreboard = null;
    GameState state;

    public HeadsUpDisplay(Dimension dimension, Dimension playAreaDimension, Point playAreaOrigin, GameState state) {
        super(dimension);

        playerOneScoreboard = new Scoreboard(state.playerOneState());
        int scoreboardX = playAreaOrigin.x + playAreaDimension.width - playerOneScoreboard.width();
        int scoreboardY = playAreaOrigin.y - playerOneScoreboard.height();
        playerOneScoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

        // Add avatar
        // TODO: Pause updating HUD when game is paused
        AnimatedSnek avatar = AnimatedSnek.playerOneSnek();
        avatar.setState(AnimatedSnek.State.IDLE);
        avatar.setOrigin(new Point(-2, playAreaOrigin.y - avatar.height() - 7));

        // If two player, add player two lives and score and move avatar to center

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

        addAll(border, avatar, playerOneScoreboard, pauseLabel);
    }

    public void updateScore() {
        // set player one label text
        playerOneScoreboard.setScore(state.playerOneState().score());

        // set player two label text

    }

    static class Scoreboard extends TGraphicCompound {
        private static final Dimension DIMENSION = new Dimension(80, 50);
        TLabel appleCount;
        ArrayList<Sprite> hearts;

        public Scoreboard(PlayerState playerState) {
            super(DIMENSION);

            hearts = new ArrayList<>(playerState.livesLeft());

            AppleSprite apple = AppleSprite.goodApple();
            apple.setOrigin(new Point(0, DIMENSION.height - (int)(apple.height() * 1.5)));

            appleCount = new TLabel("");
            setScore(playerState.score());
            appleCount.setFont(FontBook.shared().scoreBoardFont());
            appleCount.setColor(Colors.Text.HIGHLIGHTED);
            appleCount.setOrigin(new Point((int) (apple.width() * 1.5), apple.y() + apple.height()));

            addAll(apple, appleCount);
        }

        public void setScore(int score) {
            appleCount.setText(padScoreText(score));
        }

        public void removeLife() {

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
