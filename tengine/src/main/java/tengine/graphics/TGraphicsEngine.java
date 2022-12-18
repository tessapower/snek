package tengine.graphics;

import tengine.TGameEngine;
import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.TGraphicObject;

import java.awt.*;

/**
 * The system that manages all graphical elements that the user sees in the window, referred to
 * as a canvas. This class is used by the <code>TGameEngine</code> and is not intended to be
 * instantiated by other parts of the program.
 *
 * @author Tessa Power
 * @see TGameEngine
 */
public class TGraphicsEngine {
    private final TGraphicCompound canvas;

    /**
     * Constructs a new <code>TGraphicsEngine</code> with the given <code>Dimension</code>.
     */
    public TGraphicsEngine(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
    }

    /**
     * Similar to <code>paintComponent</code> for Java Swing components, this method is called
     * when the canvas should be painted.
     */
    public void paint(TGraphicsCtx ctx) {
        canvas.paint(ctx);
    }

    //--------------------------------------------------------------------------------------------- Graphic Objects --//

    /**
     * Add the given <code>TGraphicObject</code> to the canvas. Graphical objects are not drawn
     * in any guaranteed ordering.
     */
    public void add(TGraphicObject tGraphicObject) {
        canvas.add(tGraphicObject);
    }


    /**
     * Add the list of <code>TGraphicObject</code>s to the canvas. Graphical objects are not drawn
     * in any guaranteed ordering.
     */
    public void addAll(TGraphicObject... tGraphicObjects) {
        for (var tObject : tGraphicObjects) {
            add(tObject);
        }
    }

    /**
     * Remove the given <code>TGraphicObject</code> from the canvas.
     */
    public void remove(TGraphicObject tGraphicObject) {
        canvas.remove(tGraphicObject);
    }

    /**
     * Remove all graphical objects from the canvas.
     */
    public void removeAll() {
        canvas.removeAll();
    }

    /**
     * Allow the canvas to update since it was last updated <code>dtMillis</code> ago. This
     * enables graphical objects that animate or have characteristics based on time to update.
     */
    public void update(double dtMillis) {
        canvas.update(dtMillis);
    }
}
