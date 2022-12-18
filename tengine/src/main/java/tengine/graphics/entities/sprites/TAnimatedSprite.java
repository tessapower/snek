package tengine.graphics.entities.sprites;

import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.entities.TGraphicObject;
import tengine.world.TGridSquare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * A <code>TSprite</code>, but it's animated!
 *
 * @author Tessa Power
 * @see TAnimatedSpriteBuilder
 * @see TSprite
 * @see TSpriteSequence
 */
public class TAnimatedSprite extends TGraphicObject {
    protected Image image;
    protected double elapsedSecs;
    protected int fps;
    protected TSpriteSequence currentSequence;
    protected int currentFrame;
    protected Consumer<TSpriteSequence> sequenceEnd = null;

    /**
     * An <code>TAnimatedSprite</code> should not be constructed directly, rather use an
     * <code>TAnimatedSpriteBuilder</code> to construct a new object.
     *
     * @see TAnimatedSpriteBuilder
     */
    protected TAnimatedSprite(InputStream is, Dimension frameDimension, int fps,
                              TSpriteSequence currentSequence) {
        super(frameDimension);
        image = TImageLoader.loadImage(is);
        this.fps = fps;
        this.currentSequence = currentSequence;
        currentFrame = 0;
        elapsedSecs = 0;
    }


    /**
     * Let this <code>TAnimatedSprite</code> update since it was last update <code>dtMillis</code>
     * ago.
     */
    @Override
    public void update(double dtMillis) {
        elapsedSecs += dtMillis;

        if (currentSequence.loops() || currentFrame != currentSequence.lastFrame()) {
            int framesToSkip = (int) (elapsedSecs / (1.0 / fps));
            if (!currentSequence.loops()) {
                framesToSkip = Math.min(framesToSkip, currentSequence.lastFrame() - currentFrame);
            }
            currentFrame = (currentFrame + framesToSkip) % currentSequence.numFrames();
        }

        if (currentFrame == currentSequence.lastFrame() && sequenceEnd != null) {
            sequenceEnd.accept(currentSequence);
        }

        elapsedSecs %= (1.0 / fps);
    }


    /** Draws this <code>TAnimatedSprite</code> to the screen using the given
     * <code>TGraphicsCtx</code>.
     */
    @Override
    protected void draw(TGraphicsCtx ctx) {
        TGridSquare gridSquare = currentSequence.frames().get(currentFrame);
        int x = gridSquare.col() * dimension.width;
        int y = gridSquare.row() * dimension.height;

        Image frame = subImage(image, new Point(x, y), dimension);

        ctx.drawImage(frame, dimension);
    }

    /**
     * Sets the callback method which notifies the receiver every time the current
     * <code>TSpriteSequence</code> ends.
     */
    public void setSequenceEndCallback(Consumer<TSpriteSequence> onSequenceEnd) {
        sequenceEnd = onSequenceEnd;
    }

    /**
     * Retrieves the frame starting at the given <code>Point</code> with the given
     * <code>Dimension</code> from the sprite sheet for this <code>TAnimatedSprite</code>.
     */
    Image subImage(Image source, Point point, Dimension dimension) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage(point.x, point.y, dimension.width, dimension.height);
    }
}
