package tengine.world;

import tengine.TActor;
import tengine.graphics.entities.TGraphicCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Conceptually similar to a level or a map in a game. Used to encapsulate all of the
 * <code>TActor</code>s and (optionally) game logic for this <code>TWorld</code>. A single
 * <code>TWorld</code> can be used and maintained throughout the game by adding and removing
 * <code>TActor</code>s via the public methods. <code>TActor</code>s are added to the
 * <code>TWorld</code>'s internal canvas, which is then loaded and unloaded onto the
 * <code>TGraphicsEngine</code> through the <code>TGameEngine</code>.
 *
 * @author Tessa Power
 * @see TActor
 */
public class TWorld {
    protected TGraphicCompound canvas;
    protected List<TActor> actors;

    /**
     * Constructs a new <code>TWorld</code> and creates a canvas of the given
     * <code>Dimension</code>.
     */
    public TWorld(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
        actors = new ArrayList<>();
    }

    /**
     * Add a <code>TActor</code> to this <code>TWorld</code>.
     */
    public void add(TActor actor) {
        actors.add(actor);
        actor.addToWorld(this);
        canvas.add(actor.graphic());
    }

    /**
     * Add the list of <code>TActor</code> to this <code>TWorld</code>.
     */
    public void add(TActor... actors) {
        for (var actor : actors) {
            add(actor);
        }
    }

    /**
     * Add the list of <code>TActor</code> to this <code>TWorld</code>.
     */
    public void add(List<TActor> actors) {
        actors.forEach(this::add);
    }

    /**
     * Removes a <code>TActor</code> from this <code>TWorld</code> and as a result, destroys the
     * <code>TActor</code>. Does nothing if the given <code>TActor</code> does not belong to this
     * <code>TWorld</code>.
     */
    public void remove(TActor actor) {
        if (actors.contains(actor)) {
            actors.remove(actor);
            actor.destroy();
        }
    }

    /**
     * Remove all <code>TActor</code>s from this <code>TWorld</code>.
     */
    public void removeAll() {
        actors.forEach(this::remove);
    }

    /**
     * The canvas containing all the graphical components of the <code>TActor</code>s in this
     * <code>TWorld</code>.
     */
    public TGraphicCompound canvas() {
        return canvas;
    }
}
