package tengine.graphics.components.sprites;

import tengine.world.TGridSquare;

import java.util.List;

/**
 * A sequence of images used together with an <code>TAnimatedSprite</code>. A
 * <code>TSpriteSequence</code> lets you easily refer to and swap out different sequences.
 *
 * @author Tessa Power
 * @see TAnimatedSprite
 */
public record TSpriteSequence(String id, List<TGridSquare> frames, boolean loops) {
    /**
     * The number of frames in this <code>SpriteSequence</code>.
     */
    public int numFrames() {
        return frames.size();
    }

    /**
     * The (zero-based) index of the last frame in this <code>SpriteSequence</code>.
     */
    public int lastFrame() {
        return numFrames() - 1;
    }
}
