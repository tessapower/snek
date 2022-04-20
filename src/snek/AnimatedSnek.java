package snek;

import graphics.graphicsObjects.sprites.AnimatedSprite;
import graphics.graphicsObjects.sprites.SpriteSequence;
import utils.ResourceLoader;
import world.GridSquare;

import java.awt.*;
import java.util.ArrayList;

public class AnimatedSnek extends AnimatedSprite {
    // We scale up the Snek otherwise he is only 32 x 32 points big
    private static final Dimension FRAME_DIMENSION = new Dimension(32, 32);
    private static final String SNEK_SPRITE_SHEET = "animated-snek-sprite-sheet.png";
    private static final int NUM_FRAMES = 10;
    private static final int SCALE = 4;
    private static final int FPS = 5;

    private static final SpriteSequence IDLE = new SpriteSequence(generateRow(0), true);
    private static final SpriteSequence SPINNING = new SpriteSequence(generateRow(1), true);
    private static final SpriteSequence MOVING = new SpriteSequence(generateRow(2), true);
    private static final SpriteSequence EATING = new SpriteSequence(generateRow(3), false);
    private static final SpriteSequence DYING = new SpriteSequence(generateRow(4), false);

    public AnimatedSnek() {
        super(ResourceLoader.load(SNEK_SPRITE_SHEET), FRAME_DIMENSION, FPS, MOVING);
        setScale(SCALE);
    }

    private static ArrayList<GridSquare> generateRow(int row) {
        ArrayList<GridSquare> sequence = new ArrayList<>(NUM_FRAMES);
        for (int col = 0; col < NUM_FRAMES; ++col) {
            sequence.add(new GridSquare(row, col));
        }

        return sequence;
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

    public enum State {
        IDLE,
        SPINNING,
        MOVING,
        EATING,
        DYING
    }
}
