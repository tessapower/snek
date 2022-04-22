package snake.snek;

import snake.player.Player;
import snake.screens.play.GameWorld;
import tengine.world.GridSquare;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnekTail {
    public static final int MAX_TAIL_LEN = 19;
    List<SnekTailSprite> tailPieces;
    Player playerNumber;

    public SnekTail(Dimension dimension, GridSquare headGridSquare, Direction direction, GameWorld world,
                    Player playerNumber) {
        tailPieces = new ArrayList<>(MAX_TAIL_LEN);
        this.playerNumber = playerNumber;

        SnekTailSprite t1 = null, t2 = null;

        switch(playerNumber) {
            case PLAYER_ONE -> {
                t1 = SnekTailSprite.playerOneTailSprite(dimension);
                t2 = SnekTailSprite.playerOneTailSprite(dimension);
            }
            case PLAYER_TWO -> {
                t1 = SnekTailSprite.playerTwoTailSprite(dimension);
                t2 = SnekTailSprite.playerTwoTailSprite(dimension);
            }
        }

        // Set the tailpieces according to the direction
        switch(direction) {
            case UP -> {
                t1.setGridSquare(new GridSquare(headGridSquare.row() + 1, headGridSquare.col()), world);
                t2.setGridSquare(new GridSquare(headGridSquare.row() + 2, headGridSquare.col()), world);
            }
            case DOWN -> {
                t1.setGridSquare(new GridSquare(headGridSquare.row() - 1, headGridSquare.col()), world);
                t2.setGridSquare(new GridSquare(headGridSquare.row() - 2, headGridSquare.col()), world);
            }
            case LEFT -> {
                t1.setGridSquare(new GridSquare(headGridSquare.row(), headGridSquare.col() + 1), world);
                t2.setGridSquare(new GridSquare(headGridSquare.row(), headGridSquare.col() + 2), world);
            }
            case RIGHT -> {
                t1.setGridSquare(new GridSquare(headGridSquare.row(), headGridSquare.col() - 1), world);
                t2.setGridSquare(new GridSquare(headGridSquare.row(), headGridSquare.col() - 2), world);
            }
        }

        tailPieces.add(t1);
        tailPieces.add(t2);
    }

    public int length() {
        return tailPieces.size();
    }

    public void moveToward(GridSquare gridSquare, GameWorld world) {
        // Recycle the end of the tail, so we don't have to allocate a new tailpiece
        SnekTailSprite endOfTail = pop();
        endOfTail.setGridSquare(gridSquare, world);
        tailPieces.add(0, endOfTail);
    }

    public SnekTailSprite growToward(GridSquare gridSquare, GameWorld world) {
        SnekTailSprite newTailPiece = null;

        switch(playerNumber) {
            case PLAYER_ONE -> newTailPiece = SnekTailSprite.playerOneTailSprite(tailPieces.get(0).dimension());
            case PLAYER_TWO -> newTailPiece = SnekTailSprite.playerTwoTailSprite(tailPieces.get(0).dimension());
        }

        newTailPiece.setGridSquare(gridSquare, world);
        tailPieces.add(0, newTailPiece);

        return newTailPiece;
    }

    private SnekTailSprite pop() {
        return tailPieces.remove(tailPieces.size() - 1);
    }
}
