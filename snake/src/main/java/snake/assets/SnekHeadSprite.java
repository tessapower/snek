package snake.assets;

import snake.actors.snek.Snek;
import snake.game.GameWorld;
import snake.player.PlayerNumber;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.shapes.TRect;
import tengine.graphics.entities.sprites.Sprite;
import tengine.world.GridSquare;

import java.awt.*;

/**
 * Represents the <code>Sprite</code> for a <code>snek</code>'s head. This is used in conjunction
 * with the <code>SnekTailSprite</code> to create the composed <code>Sprite</code> for a
 * <code>snek</code>.
 *
 * @author Tessa Power
 * @see Snek
 * @see SnekTailSprite
 */
public class SnekHeadSprite extends TGraphicCompound {
    private static final String SNEK_HEAD_P1 = "snek-head-p1.png";
    private static final String SNEK_HEAD_P2 = "snek-head-p2.png";

    private GridSquare gridSquare = null;

    /**
     * Constructs a new <code>SnekHeadSprite</code> with the given <code>Dimension</code> for the
     * given player.
     *
     * @see PlayerNumber
     */
    public SnekHeadSprite(Dimension dimension, PlayerNumber playerNumber) {
        super(dimension);

        Sprite sprite = new Sprite(
                AssetLoader.load(
                    switch(playerNumber) {
                        case PLAYER_ONE -> SNEK_HEAD_P1;
                        case PLAYER_TWO -> SNEK_HEAD_P2;
                    }
                ),
                dimension
        );

        TRect tongue = new TRect(new Dimension(4, 4));
        tongue.isFilled = true;
        tongue.fillColor = Colors.SNEK_RED;

        // We want this to extend outside the snake head sprite
        tongue.setOrigin(new Point(6, -4));
        add(sprite);
        add(tongue);
    }

    /**
     * Set the <code>GridSquare</code> location in the given <code>GameWorld</code> of this
     * <code>SnekHeadSprite</code>.
     */
    public void setGridSquare(GridSquare gridSquare, GameWorld world) {
        this.gridSquare = gridSquare;
        // We use the world to set the origin as worlds of different sizes and with different
        // origins will map grid squares to different screen locations
        setOrigin(world.grid().positionForSquare(this.gridSquare));
    }

    /**
     * The <code>GridSquare</code> location of this <code>SnekHeadSprite</code>.
     */
    public GridSquare gridSquare() {
        return gridSquare;
    }
}
