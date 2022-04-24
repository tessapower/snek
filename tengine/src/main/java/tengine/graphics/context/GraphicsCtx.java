package tengine.graphics.context;

import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;

public interface GraphicsCtx {
    void drawRect(Dimension dimension, Color color);
    void drawCircle(Dimension dimension, Color color);
    void drawFilledRect(Dimension dimension, Color color);
    void drawFilledCircle(Dimension dimension, Color color);
    void drawText(Point origin, String text, Font font, Color color);
    void drawImage(Image image);
    void drawImage(Image image, Dimension dimension);
    void applyTransforms(TTranslation translation, TRotation rotation, TScale scale);
    void pushCurrentTransform();
    void popTransform();
}
