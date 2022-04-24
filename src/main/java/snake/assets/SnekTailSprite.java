package snake.assets;

import snake.game.GameWorld;
import snake.player.PlayerNumber;
import tengine.graphics.entities.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

public class SnekTailSprite extends Sprite {
    private static final String SNEK_TAIL_P1 = "snek-tail-p1.png";
    private static final String SNEK_TAIL_P2 = "snek-tail-p2.png";

    private GridSquare gridSquare = null;

    public SnekTailSprite(Dimension dimension, PlayerNumber playerNumber) {
        super(
                AssetLoader.load(
                        switch(playerNumber) {
                            case PLAYER_ONE -> SNEK_TAIL_P1;
                            case PLAYER_TWO -> SNEK_TAIL_P2;
                        }
                ),
                dimension
        );
    }

    public void setGridSquare(GridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    public GridSquare gridSquare() {
        return gridSquare;
    }
}
