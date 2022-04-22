package snake.screens.play;

import snake.Colors;
import snake.FontBook;
import snake.apple.AppleSprite;
import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.TGraphicCompound;
import tengine.graphics.graphicsObjects.shapes.TRect;
import tengine.graphics.graphicsObjects.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {

    TLabel playerOneScore = new TLabel("000");
    TLabel playerTwoScore = null;

    public HeadsUpDisplay(Dimension dimension, Dimension playAreaDimension, Point playAreaOrigin) {
        super(dimension);

        // TODO: Add player one lives & score
//        AppleSprite playerOneApple = AppleSprite.goodApple();
//        playerOneApple.setOrigin(new Point(playAreaOrigin.x + playAreaDimension.width,
//                playAreaOrigin.y - playerOneApple.height()));
//        playerOneScore.setFont(FontBook.shared().scoreBoardFont());
//        playerOneScore.setOrigin(new Point(playAreaOrigin.x + playAreaDimension.width - 10,
//                playAreaOrigin.y - playerOneApple.height()));
//
//        addAll(playerOneApple, playerOneScore);

        // Add avatar
        AnimatedSnek avatar = AnimatedSnek.playerOneSnek();
        avatar.setState(AnimatedSnek.State.MOVING);
        avatar.setOrigin(new Point(playAreaOrigin.x + playAreaDimension.width / 2, playAreaOrigin.y - avatar.height()));

        // If two player, add player two lives and score and move avatar to center

        // Add border
        TRect border = new TRect(playAreaDimension);
        // TODO: maybe add a pixelated border?
        add(border);
        border.setOrigin(playAreaOrigin);
        border.outlineColor = Colors.SNEK_GREEN;
    }

    public void updateScore(GameState gameState) {
        // set player one label text

        // set player two label text
    }
}
