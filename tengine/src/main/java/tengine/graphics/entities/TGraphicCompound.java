package tengine.graphics.entities;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TGraphicCompound extends TGraphicObject {
    private final Set<TGraphicObject> children;

    public TGraphicCompound(Dimension dimension) {
        super(dimension);
        children = new HashSet<>();
    }

    public Set<TGraphicObject> children() {
        return Collections.unmodifiableSet(children);
    }

    public int numChildren() {
        return children.size();
    }

    public void add(TGraphicObject obj) {
        if (this == obj) {
            throw new IllegalStateException("parent cannot be child of itself");
        }

        obj.setParent(this);
        children.add(obj);
    }

    public void addAll(TGraphicObject... objects) {
        for (var obj : objects) {
            add(obj);
        }
    }

    public void remove(TGraphicObject obj) {
        if (children.contains(obj)) {
            obj.setParent(null);
            children.remove(obj);
        }
    }

    public void removeAll() {
        children.forEach(child -> child.setParent(null));
        children.clear();
    }

    @Override
    public void update(double dtMillis) {
        for (TGraphicObject child : children) {
            child.update(dtMillis);
        }
    }

    @Override
    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        for (TGraphicObject child : children) {
            child.paint(ctx);
        }

        ctx.popTransform();
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        // No-op
    }
}
