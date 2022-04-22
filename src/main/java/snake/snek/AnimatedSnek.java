package snake.snek;

import snake.utils.ResourceLoader;
import tengine.graphics.graphicsObjects.sprites.AnimatedSprite;
import tengine.graphics.graphicsObjects.sprites.SpriteSequence;
import tengine.world.GridSquare;

import java.awt.*;
import java.util.ArrayList;

public class AnimatedSnek extends AnimatedSprite {
    public static final int DEFAULT_FPS = 5;

    // We scale up the Snek otherwise he is only 32 x 32 points big
    private static final Dimension FRAME_DIMENSION = new Dimension(32, 32);
    private static final String SNEK_SPRITE_SHEET_P1 = "animated-snek-sprite-sheet.png";
    private static final String SNEK_SPRITE_SHEET_P2 = "animated-snek-sprite-sheet.png";
    private static final int NUM_FRAMES = 10;
    private static final int SCALE = 4;

    private static final SpriteSequence IDLE = new SpriteSequence(State.IDLE.id, generateRow(0), true);
    private static final SpriteSequence SPINNING = new SpriteSequence(State.SPINNING.id, generateRow(1), true);
    private static final SpriteSequence MOVING = new SpriteSequence(State.MOVING.id, generateRow(2), true);
    private static final SpriteSequence EATING = new SpriteSequence(State.EATING.id, generateRow(3), false);
    static {
       EATING.frames().remove(0);
       EATING.frames().remove(0);
    }
    private static final SpriteSequence DYING = new SpriteSequence(State.DYING.id, generateRow(4), false);
    static {
        DYING.frames().add(0, IDLE.frames().get(0));
    }

    private AnimatedSnek(String path) {
        super(ResourceLoader.load(path), FRAME_DIMENSION, DEFAULT_FPS, MOVING);
        setScale(SCALE);
    }

    public static AnimatedSnek animatedSnek() {
        return new AnimatedSnek(SNEK_SPRITE_SHEET_P1);
    }

    public static AnimatedSnek playerOneSnek() {
        return new AnimatedSnek(SNEK_SPRITE_SHEET_P1);
    }

    public static AnimatedSnek playerTwoSnek() {
        return new AnimatedSnek(SNEK_SPRITE_SHEET_P2);
    }

    private static ArrayList<GridSquare> generateRow(int row) {
        ArrayList<GridSquare> sequence = new ArrayList<>(NUM_FRAMES);
        for (int col = 0; col < NUM_FRAMES; ++col) {
            sequence.add(new GridSquare(row, col));
        }

        return sequence;
    }

    public int fps() {
        return fps;
    }

    public static int defaultFps() {
        return DEFAULT_FPS;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

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

    public State currentState() {
        return switch(currentSequence.id()) {
            case "spinning" -> State.SPINNING;
            case "moving" -> State.MOVING;
            case "eating" -> State.EATING;
            case "dying" -> State.DYING;
            default -> State.IDLE;
        };
    }

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
