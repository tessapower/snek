package tengine.graphics.components.sprites;

import java.awt.*;
import java.io.InputStream;

/**
 * A builder for a <code>TAnimatedSprite</code>. Use this to create a <code>TAnimatedSprite</code>
 * in a simple and readable way. E.g.
 * <br />
 * <pre>
 * var monsterBuilder = new TAnimatedSpriteBuilder();
 * monsterBuilder.inputStream(monsterInputStream)
 *               .frameDimension(monsterDim)
 *               .fps(25)
 *               .currentSequence(patrolling);
 *
 * var monster = monsterBuilder.build();
 * </pre>
 *
 * @author Tessa Power
 * @see TAnimatedSprite
 * @see TSpriteSequence
 */
public class TAnimatedSpriteBuilder {
    private InputStream is;
    private Dimension frameDimension;
    private int fps;
    private TSpriteSequence currentSequence;

    /**
     * The <code>InputStream</code> to use to build an <code>TAnimatedSprite</code>.
     */
    public TAnimatedSpriteBuilder inputStream(InputStream is) {
        this.is = is;

        return this;
    }

    /**
     * The <code>Dimension</code> of the frames of the <code>TAnimatedSprite</code>.
     */
    public TAnimatedSpriteBuilder frameDimension(Dimension frameDimension) {
        this.frameDimension = frameDimension;

        return this;
    }

    /**
     * The frames per second for the <code>TAnimatedSprite</code>.
     */
    public TAnimatedSpriteBuilder fps(int fps) {
        this.fps = fps;

        return this;
    }

    /**
     * The current <code>TSpriteSequence</code> for the <code>TAnimatedSprite</code>.
     */
    public TAnimatedSpriteBuilder currentSequence(TSpriteSequence currentSequence) {
        this.currentSequence = currentSequence;

        return this;
    }

    /**
     * Constructs a new <code>TAnimatedSprite</code>.
     *
     * @throws IllegalArgumentException if any of the constructor parameters are not initialized.
     */
    public TAnimatedSprite build() {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        if (frameDimension == null) {
            throw new IllegalArgumentException("Frame dimension cannot be null");
        }

        if (fps <= 0) {
            throw new IllegalArgumentException("FPS must be a positive, non-zero integer");
        }

        if (currentSequence == null) {
            throw new IllegalArgumentException("TSpriteSequence cannot be null");
        }

        return new TAnimatedSprite(is, frameDimension, fps, currentSequence);
    }
}
