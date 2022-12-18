package tengine.graphics.context;

import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;

/**
 * A graphical context which can draw things onto the window.
 *
 * @author Tessa Power
 * @see MasseyGraphicsCtx
 */
public interface TGraphicsCtx {
    /**
     * Draws an outlined rectangle with the given <code>Dimension</code> and <code>Color</code>.
     */
    void drawRect(Dimension dimension, Color color);

    /**
     * Draws an outlined circle with the given <code>Dimension</code> and <code>Color</code>.
     */
    void drawCircle(Dimension dimension, Color color);

    /**
     * Draws a filled rectangle with the given <code>Dimension</code> and <code>Color</code>.
     */
    void drawFilledRect(Dimension dimension, Color color);

    /**
     * Draws a filled circle with the given <code>Dimension</code> and <code>Color</code>.
     */
    void drawFilledCircle(Dimension dimension, Color color);

    /**
     * Draws text at the given origin in the given <code>Font</code> and <code>Color</code>.
     */
    void drawText(Point origin, String text, Font font, Color color);

    /**
     * Draws an image with original dimensions.
     *
     * @see Image
     */
    void drawImage(Image image);

    /**
     * Draws an image with the given <code>Dimension</code>.
     */
    void drawImage(Image image, Dimension dimension);

    /**
     * Applies the given transforms to this <code>TGraphicsCtx</code>.
     *
     * @see TTranslation
     * @see TRotation
     * @see TScale
     */
    void applyTransforms(TTranslation translation, TRotation rotation, TScale scale);

    /**
     * Saves the current state of transforms applied to this <code>TGraphicsCtx</code>, which can
     * later be popped to restore to that state.
     *
     * @see TGraphicsCtx#popTransform()
     */
    void pushCurrentTransform();

    /**
     * Applies the last saved state of transforms applied to this <code>TGraphicsCtx</code>.
     */
    void popTransform();
}
