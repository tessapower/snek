package tengine.graphics.components.text;

import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.components.TGraphicObject;

import java.awt.*;

/**
 * Text that can be drawn to the screen. Currently, the <code>TLabel</code> does not support
 * accessing the dimension of the label itself. Placing the text within the window is
 * unfortunately a manual job.
 *
 * @author Tessa Power
 */
public class TLabel extends TGraphicObject {
    private static final int DEFAULT_SIZE = 12;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, DEFAULT_SIZE);

    private String text;
    private Font font = DEFAULT_FONT;
    private Color color = DEFAULT_COLOR;

    /**
     * Creates a <code>TLabel</code> containing the given text.
     */
    public TLabel(String text) {
        this(text, new Point(0, 0));
    }

    /**
     * Creates a <code>TLabel</code> at the given <code>Point</code> with the given text.
     */
    public TLabel(String text, Point origin) {
        super(new Dimension());
        setText(text);
        setOrigin(origin);
    }

    /**
     * The text that this <code>TLabel</code> contains.
     */
    public String text() {
        return text;
    }

    /**
     * Sets the text that this <code>TLabel</code> contains. Note that the origin of the text
     * does not change.
     */
    public void setText(String text) {
        // TODO: calculate and update dimension based on width and height of text
        //   Font.getStringBounds(String str, FontRenderContext frc) would let us easily
        //   get the bounds of the text and set the TLabel dimension. To get the FontRenderContext
        //   we need to be able to call Graphics2D.getFontRenderContext() on the current context.
        this.text = text;
    }

    /**
     * The <code>Font</code> used for this <code>TLabel</code>.
     */
    public Font font() {
        return font;
    }

    /**
     * Sets the <code>Font</code> used for this <code>TLabel</code>.
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * The font size for this <code>TLabel</code>.
     */
    public int fontSize() {
        return font.getSize();
    }

    /**
     * Sets the font size used for this <code>TLabel</code>.
     */
    public void setFontSize(int fontSize) {
        font = new Font(font.getFontName(), font.getStyle(), fontSize);
    }

    /**
     * The <code>Color</code> used for this <code>TLabel</code>.
     */
    public Color color() {
        return color;
    }

    /**
     * Sets the <code>Color</code> used for this <code>TLabel</code>.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Draws this <code>TLabel</code> onto the screen using the given <code>TGraphicsCtx</code>.
     */
    @Override
    protected void draw(TGraphicsCtx ctx) {
        ctx.drawText(new Point(0, 0), text, font, color);
    }

    /**
     * WARNING: this method is currently not supported and will throw a
     * <code>RuntimeException</code>.
     */
    @Override
    public final Dimension dimension() {
        throw new RuntimeException("TLabel dimension not supported");
    }

    /**
     * WARNING: this method is currently not supported and will throw a
     * <code>RuntimeException</code>.
     */
    @Override
    public final int width() {
        throw new RuntimeException("TLabel width not supported");
    }

    /**
     * WARNING: this method is currently not supported and will throw a
     * <code>RuntimeException</code>.
     */
    @Override
    public final int height() {
        throw new RuntimeException("TLabel height not supported");
    }
}
