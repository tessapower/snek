package tengine.graphics.entities;

import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;

/**
 * A generic graphical object that can be drawn to the screen.
 *
 * @author Tessa Power
 */
abstract public class TGraphicObject {
    protected Dimension dimension;
    protected final TRotation rotation = TRotation.identity();
    protected final TTranslation translation = TTranslation.identity();
    protected final TScale scale = TScale.identity();
    protected TGraphicCompound parent = null;

    /**
     * Constructs a new <code>TGraphicObject</code> with the given <code>Dimension</code>.
     */
    public TGraphicObject(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * The <code>Dimension</code> of this <code>TGraphicObject</code>.
     */
    public Dimension dimension() {
        return new Dimension((int) (scale.xScaleFactor * dimension.width),
                (int) (scale.yScaleFactor * dimension.height));
    }

    /**
     * The width of this <code>TGraphicObject</code>.
     */
    public int width() {
        return (int) (scale.xScaleFactor * dimension.width);
    }

    /**
     * The height of this <code>TGraphicObject</code>.
     */
    public int height() {
        return (int) (scale.yScaleFactor * dimension.height);
    }

    /**
     * Sets the origin for this <code>TGraphicObject</code>.
     */
    public void setOrigin(Point origin) {
        translation.dx = origin.x;
        translation.dy = origin.y;
    }

    /**
     * The 2D point of origin for this <code>TGraphicObject</code>. Generally, this is considered
     * the upper-left corner of the bounding box. This may differ with certain objects.
     */
    public Point origin() {
        return new Point(translation.dx, translation.dy);
    }

    /**
     * The x component of the origin for this <code>TGraphicObject</code>.
     */
    public int x() {
        return translation.dx;
    }

    /**
     * The y component of the origin for this <code>TGraphicObject</code>.
     */
    public int y() {
        return translation.dy;
    }

    /**
     * The midpoint of this <code>TGraphicObject</code> based on its <code>Dimension</code>.
     */
    public Point midPoint() {
        return new Point(dimension.width / 2, dimension.height / 2);
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    /**
     * Sets the rotation of this <code>TGraphicObject</code> in degrees.
     */
    public void setRotation(double thetaDegrees, Point origin) {
        rotation.thetaDegrees = thetaDegrees;
        rotation.origin = origin;
    }

    /**
     * Sets the scaling factor for this <code>TGraphicObject</code>. The scaling is applied to
     * both the x and y components of the object's <code>Dimension</code>.
     */
    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    /**
     * WARNING: Do not call this method directly, instead call
     * <code>add</code> on the <code>TGraphicCompound</code> that this
     * <code>TGraphicObject</code> is being added to and the parent object which will handle
     * calling this method itself.
     */
    void setParent(TGraphicCompound parent) {
        this.parent = parent;
    }

    /**
     * The parent of this <code>TGraphicObject</code>, or null if it doesn't have one.
     */
    public TGraphicCompound parent() {
        return parent;
    }

    /**
     * Remove this <code>TGraphicObject</code> from its parent <code>TGraphicCompound</code> if it
     * has one.
     */
    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    /**
     * Applies the current transforms for this <code>TGraphicObject</code> and then draws it.
     * The state of transforms is restored when this method finishes executing.
     */
    public void paint(TGraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        draw(ctx);

        ctx.popTransform();
    }

    /**
     * Override this method to specify how to draw this graphical object to the screen with the
     * given <code>TGraphicsCtx</code>.
     */
    protected abstract void draw(TGraphicsCtx ctx);

    /**
     * Override this method to specify how to update this <code>TGraphicObject</code> a chance to
     * update since the last frame was updated <code>dtMillis</code> ago.
     */
    public void update(double dtMillis) {
        // No-op
    }
}
