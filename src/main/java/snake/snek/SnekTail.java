package snake.snek;

import snake.screens.play.SnakeWorld;
import tengine.world.GridSquare;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnekTail {
    public static final int MAX_TAIL_LEN = 19;
    List<SnekTailSprite> tailPieces;

    public SnekTail(Dimension dimension, GridSquare gridSquare, SnakeWorld world) {
        tailPieces = new ArrayList<>(MAX_TAIL_LEN);

        SnekTailSprite t1 = new SnekTailSprite(dimension);
        t1.setGridSquare(gridSquare, world);

        SnekTailSprite t2 = new SnekTailSprite(dimension);
        t2.setGridSquare(new GridSquare(gridSquare.row(), gridSquare.col() - 1), world);

        tailPieces.add(t1);
        tailPieces.add(t2);
    }

    public int length() {
        return tailPieces.size();
    }

    public void moveToward(GridSquare gridSquare, SnakeWorld world) {
        // Recycle the end of the tail, so we don't have to allocate a new tailpiece
        SnekTailSprite endOfTail = pop();
        endOfTail.setGridSquare(gridSquare, world);
        tailPieces.add(0, endOfTail);
    }

    public SnekTailSprite growToward(GridSquare gridSquare, SnakeWorld world) {
        SnekTailSprite newTailPiece = new SnekTailSprite(tailPieces.get(0).dimension());
        newTailPiece.setGridSquare(gridSquare, world);
        tailPieces.add(0, newTailPiece);

        return newTailPiece;
    }

    private SnekTailSprite pop() {
        return tailPieces.remove(tailPieces.size() - 1);
    }
}
