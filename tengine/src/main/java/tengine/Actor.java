package tengine;

import tengine.graphics.entities.TGraphicObject;
import tengine.physics.PhysicsEntity;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.world.World;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected PhysicsEntity physicsEntity;
    protected TGraphicObject graphicEntity;
    protected World world;

    public Actor() {
        origin = new Point();
        physicsEntity = new PhysicsEntity();
        graphicEntity = null;
        world = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (graphicEntity != null) {
            graphicEntity.setOrigin(origin);
        }

//        if (this.physicsEntity.collisionShape != null) {
//            physicsEntity.collisionShape.setOrigin(origin);
//        }
    }

    public Point origin() {
        return origin;
    }

    public int x() {
        return origin.x;
    }

    public int y() {
        return origin.y;
    }

    public CollisionShape bounds() {
        return physicsEntity.collisionShape;
    }

    public void removeFromWorld() {
        world.remove(this);
    }

    public void addToWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        graphicEntity.removeFromParent();
        // TODO: Eventually include physicsEntity.removeFromSystem()
        world = null;
    }

    public TGraphicObject graphic() {
        return graphicEntity;
    }
}
