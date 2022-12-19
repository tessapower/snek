package tengine;

import tengine.graphics.components.TGraphicObject;
import tengine.world.TWorld;

import java.awt.*;

/**
 * A <code>TActor</code>, AKA "Game Object", "Game Entity", or "Node", is a general purpose
 * object that interacts with your game, and responds to player input or other
 * <code>TActor</code>s in the world. <code>TActor</code>s are modelled on an "Entity Component
 * System" (ECS). Currently, an <code>TActor</code> comprises only a graphical component, but can
 * be extended to support other types of components, e.g. collisions or AI.
 *
 * @author Tessa Power
 * @see TGraphicObject
 * @see TWorld
 */
public abstract class TActor {
    protected Point origin;
    protected TGraphicObject graphicEntity;
    protected TWorld world;

    /**
     * Constructor for a <code>TActor</code>. <code>TActor</code> is an abstract class and should
     * be extended instead of instantiated directly.
     */
    public TActor() {
        origin = new Point();
        graphicEntity = null;
        world = null;
    }

    /**
     * Sets the origin of this <code>TActor</code> and it's components.
     */
    public void setOrigin(Point origin) {
        this.origin = origin;

        if (graphicEntity != null) {
            graphicEntity.setOrigin(origin);
        }
    }

    /**
     * The origin of this <code>TActor</code>.
     */
    public Point origin() {
        return origin;
    }

    /**
     * The x component of the origin of this <code>TActor</code>.
     */
    public int x() {
        return origin.x;
    }

    /**
     * The y component of the origin of this <code>TActor</code>.
     */
    public int y() {
        return origin.y;
    }

    /**
     * Remove this <code>TActor</code> from the <code>TWorld</code> it belongs to. Does nothing if
     * this <code>TActor</code> does not belong to any <code>TWorld</code>.
     */
    public void removeFromWorld() {
        world.remove(this);
    }

    /**
     * Add this <code>TActor</code> to the given <code>TWorld</code>. WARNING: this method
     * <strong>should not be manually called</strong>, instead call <code>TWorld::add(TActor)</code>. The
     * <code>TWorld</code> will call this method itself.
     * 
     * @see TWorld#add(TActor)
     */
    public void addToWorld(TWorld world) {
        this.world = world;
    }

    /**
     * Destroy this <code>TActor</code>. WARNING: this method <strong>should not be manually
     * called</strong>, instead call <code>TWorld::remove(TActor)</code>. The <code>TWorld</code>
     * will call this method itself.
     * 
     * @see TWorld#remove(TActor)
     */
    public void destroy() {
        graphicEntity.removeFromParent();
        world = null;
    }

    /**
     * The graphic for this <code>TActor</code>. Returns null if the graphic has not been
     * initialized.
     */
    public TGraphicObject graphic() {
        return graphicEntity;
    }
}
