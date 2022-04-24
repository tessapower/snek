package tengine.graphics.entities;

import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;

abstract public class TGraphicObject {
    protected Dimension dimension;
    protected final TRotation rotation;
    protected final TTranslation translation;
    protected final TScale scale;
    protected TGraphicCompound parent;

    public TGraphicObject(Dimension dimension) {
        this.dimension = dimension;
        rotation = TRotation.identity();
        translation = TTranslation.identity();
        scale = TScale.identity();
        parent = null;
    }

    public Dimension dimension() {
        return new Dimension((int) (scale.xScaleFactor * dimension.width),
                (int) (scale.yScaleFactor * dimension.height));
    }

    public int width() {
        return (int) (scale.xScaleFactor * dimension.width);
    }

    public int height() {
        return (int) (scale.yScaleFactor * dimension.height);
    }

    public void setOrigin(Point origin) {
        translation.dx = origin.x;
        translation.dy = origin.y;
    }

    public Point origin() {
        return new Point(translation.dx, translation.dy);
    }

    public int x() {
        return translation.dx;
    }

    public int y() {
        return translation.dy;
    }

    public Point midPoint() {
        return new Point(dimension.width / 2, dimension.height / 2);
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    public void setRotation(double thetaDegrees, Point origin) {
        rotation.thetaDegrees = thetaDegrees;
        rotation.origin = origin;
    }

    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    void setParent(TGraphicCompound parent) {
        this.parent = parent;
    }

    public TGraphicCompound parent() {
        return parent;
    }

    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        draw(ctx);

        ctx.popTransform();
    }

    protected abstract void draw(GraphicsCtx ctx);

    public void update(double dtMillis) {
        // No-op
    }
}
