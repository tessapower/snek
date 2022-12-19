package snake.assets;

import snake.actors.snek.Snek;
import snake.game.GameWorld;
import snake.player.PlayerNumber;
import tengine.graphics.components.sprites.TSprite;
import tengine.world.TGridSquare;

import java.awt.*;

/**
 * Represents the <code>TSprite</code> for a segment in a <code>snek</code>'s tail. This is used
 * in conjunction with the <code>SnekHeadSprite</code> to create the composed <code>TSprite</code>
 * for a <code>snek</code>.
 *
 * @author Tessa Power
 * @see Snek
 * @see SnekHeadSprite
 */
public class SnekTailSprite extends TSprite {
    private static final String SNEK_TAIL_P1 = "snek-tail-p1.png";
    private static final String SNEK_TAIL_P2 = "snek-tail-p2.png";

    private TGridSquare gridSquare = null;

    /**
     * Constructs a new <code>SnekTailSprite</code> with the given <code>Dimension</code> for the
     * given player.
     *
     * @see PlayerNumber
     */
    public SnekTailSprite(Dimension dimension, PlayerNumber playerNumber) {
        super(AssetLoader.load(
            switch(playerNumber) {
                case PLAYER_ONE -> SNEK_TAIL_P1;
                case PLAYER_TWO -> SNEK_TAIL_P2;
            }),
            dimension
        );
    }

    /**
     * Set the <code>TGridSquare</code> location in the given <code>GameWorld</code> of this
     * <code>SnekTailSprite</code>.
     */
    public void setGridSquare(TGridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        // We use the world to set the origin as worlds of different sizes and with different
        // origins will map grid squares to different screen locations
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    /**
     * The <code>TGridSquare</code> location of this <code>SnekTailSprite</code>.
     */
    public TGridSquare gridSquare() {
        return gridSquare;
    }
}
