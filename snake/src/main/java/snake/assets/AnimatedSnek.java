package snake.assets;

import snake.player.PlayerNumber;
import tengine.graphics.entities.sprites.AnimatedSprite;
import tengine.graphics.entities.sprites.SpriteSequence;
import tengine.world.TGridSquare;

import java.awt.*;
import java.util.ArrayList;

/**
 * The delightful animating snake used as an Avatar for players, and on the menu and game over
 * screens.
 *
 * @author Tessa Power
 * @see AnimatedSnek#animatedSnek()
 * @see AnimatedSnek#animatedSnek(PlayerNumber)
 * @see SpriteSequence
 * @see Avatar
 */
public class AnimatedSnek extends AnimatedSprite {
    public static final int DEFAULT_FPS = 5;

    // We scale up the Snek otherwise he is only 32 x 32 points big
    private static final Dimension FRAME_DIMENSION = new Dimension(32, 32);
    private static final String SNEK_SPRITE_SHEET_P1 = "animated-snek-sprite-sheet.png";
    private static final String SNEK_SPRITE_SHEET_P2 = "blue-animated-snek-sprite-sheet.png";
    private static final int NUM_FRAMES = 10;
    private static final int SCALE = 4;

    private static final SpriteSequence IDLE = new SpriteSequence(State.IDLE.id, generateRow(0), true);
    private static final SpriteSequence SPINNING = new SpriteSequence(State.SPINNING.id, generateRow(1), true);
    private static final SpriteSequence MOVING = new SpriteSequence(State.MOVING.id, generateRow(2), true);
    private static final SpriteSequence EATING = new SpriteSequence(State.EATING.id, generateRow(3), false);
    static {
        // Remove a few frames so that Snek starts eating faster
       EATING.frames().remove(0);
       EATING.frames().remove(0);
    }
    private static final SpriteSequence DYING = new SpriteSequence(State.DYING.id, generateRow(4), false);
    static {
        // Lengthen the dying sequence
        DYING.frames().add(0, IDLE.frames().get(0));
    }

    /**
     * The private constructor for this class.
     */
    private AnimatedSnek(String path) {
        super(AssetLoader.load(path), FRAME_DIMENSION, DEFAULT_FPS, MOVING);
        setScale(SCALE);
    }

    /**
     * Constructs an <code>AnimatedSnek</code> in the default color, which is used for single
     * player games.
     */
    public static AnimatedSnek animatedSnek() {
        return new AnimatedSnek(SNEK_SPRITE_SHEET_P1);
    }

    /**
     * Constructs an <code>AnimatedSnek</code> in the correct color for the given
     * <code>PlayerNumber</code>.
     *
     * @see PlayerNumber
     */
    public static AnimatedSnek animatedSnek(PlayerNumber playerNumber) {
        return switch(playerNumber) {
            case PLAYER_ONE -> new AnimatedSnek(SNEK_SPRITE_SHEET_P1);
            case PLAYER_TWO -> new AnimatedSnek(SNEK_SPRITE_SHEET_P2);
        };
    }

    /**
     * Generates a row of <code>TGridSquare</code>s with coordinates that correspond to where an
     * animation sequence appears in the sprite sheet. The entire animation sequence appears on a
     * single row, and all frames in the animation sequence have the same dimension.
     *
     * @see AnimatedSprite
     * @see SpriteSequence
     * @see TGridSquare
     */
    private static ArrayList<TGridSquare> generateRow(int row) {
        ArrayList<TGridSquare> sequence = new ArrayList<>(NUM_FRAMES);
        for (int col = 0; col < NUM_FRAMES; ++col) {
            sequence.add(new TGridSquare(row, col));
        }

        return sequence;
    }

    /**
     * Set the framerate for this <code>AnimatedSnek</code> in frames per second.
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * Set the <code>State</code> of this <code>AnimatedSnek</code>, e.g. eating, dying, etc.
     *
     * @see State
     */
    public void setState(State state) {
        switch(state) {
            case IDLE -> currentSequence = IDLE;
            case SPINNING -> currentSequence = SPINNING;
            case MOVING -> currentSequence = MOVING;
            case EATING -> currentSequence = EATING;
            case DYING -> currentSequence = DYING;
        }

        currentFrame = 0;
    }

    /**
     * The current state of this <code>AnimatedSnek</code>, e.g. idle, moving, eating.
     *
     * @see AnimatedSnek
     */
    public enum State {
        IDLE("IDLE"),
        SPINNING("SPINNING"),
        MOVING("MOVING"),
        EATING("EATING"),
        DYING("DYING");

        public final String id;

        State(String id) {
            this.id = id;
        }
    }
}
