package tengine.graphics;

import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.entities.TGraphicCompound;
import tengine.graphics.entities.TGraphicObject;

import java.awt.*;

public class GraphicsEngine {
    private final TGraphicCompound canvas;
//    private final Color backgroundColor;

    public GraphicsEngine(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
//        backgroundColor = null;
    }

    public void paint(GraphicsCtx ctx) {
        canvas.paint(ctx);
    }

    //--------------------------------------------------------------------------------------------- Graphic Objects --//

    public void add(TGraphicObject tGraphicObject) {
        canvas.add(tGraphicObject);
    }

    public void addAll(TGraphicObject... tGraphicObjects) {
        for (var tObject : tGraphicObjects) {
            add(tObject);
        }
    }

    public void remove(TGraphicObject tGraphicObject) {
        tGraphicObject.removeFromParent();
    }

    public void removeAll() {
        canvas.removeAll();
    }

    public void update(double dtMillis) {
        canvas.update(dtMillis);
    }
}
