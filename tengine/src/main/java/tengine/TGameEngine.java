package tengine;

import tengine.graphics.TGraphicsEngine;
import tengine.graphics.context.TGraphicsCtx;
import tengine.graphics.context.MasseyGraphicsCtx;
import tengine.world.TWorld;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Stack;

/**
 * The central engine that coordinates the various systems and runs the core game loop. Extend
 * this class to create your own game and program entry point. For further documentation on
 * creating your own game using <code>TEngine</code>, see the
 * <a href="https://github.com/tessapower/tengine.git">README</a>.
 *
 * @author Tessa Power
 * @see TGraphicsEngine
 */
public abstract class TGameEngine implements KeyListener, MouseListener, MouseMotionListener {
    private static final Dimension DEFAULT_WINDOW_DIMENSION = new Dimension(500, 500);
    private static final int DEFAULT_FRAMERATE = 30;

    // Window related
    private JFrame jFrame;
    private GamePanel gamePanel;
    private int width;
    private int height;
    private boolean initialized = false;

    // Graphics related
    private Graphics2D graphics2D;
    private TGraphicsEngine graphicsEngine;
    private final Stack<AffineTransform> transforms;

    private long lastUpdateMillis = 0;

    //-------------------------------------------------------------------------- Core game loop --//
    private final GameTimer timer = new GameTimer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            if (lastUpdateMillis == 0) {
                lastUpdateMillis = now;
            }
            double elapsedSecs = (now - lastUpdateMillis) / 1000.0;
            lastUpdateMillis = now;

            update(elapsedSecs);

            gamePanel.repaint();
        }
    });

    //--------------------------------------------------------------------------------------------//

    /**
     * Protected constructor for the <code>TGameEngine</code>. This method is protected as the class
     * should be extended and use the <code>createGame</code> methods to create a new game.
     */
    protected TGameEngine() {
        transforms = new Stack<>();

        SwingUtilities.invokeLater(this::setupWindow);
    }

    /**
     * Initializes and runs the given game at the given framerate. As soon as this method is
     * called, the game will be started.
     */
    protected static void createGame(TGameEngine game, int framerate) {
        game.init();
        game.startGameLoop(framerate);
    }

    /**
     * Initializes and runs the given game at the default framerate. As soon as this method is
     * called, the game will be started.
     */
    protected static void createGame(TGameEngine game) {
        createGame(game, DEFAULT_FRAMERATE);
    }

    /**
     * The <code>TGraphicsEngine</code> used by this <code>TGameEngine</code>.
     */
    public TGraphicsEngine graphicsEngine() {
        return graphicsEngine;
    }

    //------------------------------------------------------------------------------- GameTimer --//

    /**
     * Runs the core game loop based on the set framerate.
     *
     * @author Tessa Power
     */
    protected static class GameTimer extends Timer {
        @Serial
        private static final long serialVersionUID = 1L;
        private static final int MIN_FRAMERATE = 1;
        private static final int ONE_SECOND = 1000;
        private int framerate;

        protected GameTimer(int framerate, ActionListener listener) {
            super(1000 / framerate, listener);
            this.framerate = framerate;
        }

        protected void setFramerate(int framerate) {
            if (framerate < MIN_FRAMERATE) framerate = MIN_FRAMERATE;
            this.framerate = framerate;

            int delay = ONE_SECOND / framerate;
            setInitialDelay(0);
            setDelay(delay);
        }
    }

    //---------------------------------------------------------------- TWorld Loading/Unloading --//

    /**
     * Load the given <code>TWorld</code> into the relevant systems. This method should be called
     * prior to starting the game, ideally in <code>init</code>, and immediately after
     * <code>unloadWorld</code> to avoid putting this <code>TGameEngine</code> into an invalid
     * state. When swapping out worlds/levels/maps, always call <code>unloadWorld</code> first.
     *
     * @see TGameEngine#unloadWorld(TWorld)
     * @see TGameEngine#init()
     */
    public void loadWorld(TWorld world) {
        graphicsEngine.add(world.canvas());
    }

    /**
     * Unload the given <code>TWorld</code> from this <code>TGameEngine</code>.
     * <code>LoadWorld</code> should be called immediately after calling this method, as this
     * method puts the <code>TGameEngine</code> into an invalid state.
     *
     * @see TGameEngine#loadWorld(TWorld)
     */
    public void unloadWorld(TWorld world) {
        world.canvas().removeFromParent();
    }

    //---------------------------------------------------------------------------- Tick Methods --//

    /**
     * Allow the systems in this <code>TGameEngine</code> to update since they were last updated
     * <code>dtMillis</code> ago.
     */
    public void update(double dtMillis) {
        graphicsEngine.update(dtMillis);
    }

    /**
     * Called by the <code>paintComponent</code> each time the system repaints the window.
     */
    private void paint(TGraphicsCtx ctx) {
        clearBackground(width, height);
        graphicsEngine.paint(ctx);
    }

    //------------------------------------------------------------------------------------------------------ Window --//

    /**
     * Sets up the window and initializes all the systems related to it.
     */
    private void setupWindow() {
        // TODO: Eventually replace with graphicsEngine = new TGraphicsEngine(Graphics2D)
        graphicsEngine = new TGraphicsEngine(TGameEngine.DEFAULT_WINDOW_DIMENSION);

        jFrame = new JFrame();
        gamePanel = new GamePanel();

        width = TGameEngine.DEFAULT_WINDOW_DIMENSION.width;
        height = TGameEngine.DEFAULT_WINDOW_DIMENSION.height;

        jFrame.setSize(width, height);
        jFrame.setResizable(false);
        jFrame.setLocation(200, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setVisible(true);

        gamePanel.setDoubleBuffered(true);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        // Register a key event dispatcher to get a turn in handling all
        // key events, independent of which component currently has the focus
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            switch (e.getID()) {
                case KeyEvent.KEY_PRESSED -> {
                    TGameEngine.this.keyPressed(e);

                    return false;
                }
                case KeyEvent.KEY_RELEASED -> {
                    TGameEngine.this.keyReleased(e);

                    return false;
                }
                case KeyEvent.KEY_TYPED -> {
                    TGameEngine.this.keyTyped(e);

                    return false;
                }
                default -> { return false; } // do not consume the event
            }
        });

        Insets insets = jFrame.getInsets();
        jFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    /**
     * Sets a custom <code>Dimension</code> and title for the game window. This method should be
     * called in the <code>init</code> method prior to creating your game with 
     * <code>createGame</code>.
     * 
     * @see TGameEngine#init()
     * @see TGameEngine#createGame(TGameEngine)
     */
    public void setWindowProperties(Dimension dimension, String title) {
        SwingUtilities.invokeLater(() -> {
            Insets insets = jFrame.getInsets();
            width = dimension.width;
            height = dimension.height;
            jFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
            gamePanel.setSize(width, height);
            jFrame.setTitle(title);

            // Create a new TGraphicsEngine with the new dimension
            graphicsEngine = new TGraphicsEngine(dimension);
        });
    }

    /**
     * The width of the window.
     */
    public int windowWidth() {
        return width;
    }

    /**
     * The height of the window.
     */
    public int windowHeight() {
        return height;
    }

    /**
     * Starts the <code>GameTimer</code> with the given framerate.
     */
    private void startGameLoop(int framerate) {
        initialized = true;

        timer.setFramerate(framerate);
        timer.setRepeats(true);

        timer.start();
    }

    //------------------------------------------------------------------------------ Methods that can be overridden --//

    /**
     * Override this method to initialize anything prior to your game. This method is usually
     * used to set custom window properties and load the initial <code>TWorld</code>.
     *
     * @see TGameEngine#setWindowProperties(Dimension, String)
     * @see TGameEngine#loadWorld(TWorld)
     */
    public void init() {
        // No-op
    }

    /**
     * Override this method to handle key pressed events. This event is triggered when a player
     * presses a key.
     *
     * @see KeyEvent
     * @see TGameEngine#keyReleased(KeyEvent)
     * @see TGameEngine#keyTyped(KeyEvent)
     */
    public void keyPressed(KeyEvent event) {
        // No-op
    }

    /**
     * Override this method to handle key released events. This event is triggered when a player
     * releases a key, so usually follows a key pressed event.
     *
     * @see KeyEvent
     * @see TGameEngine#keyPressed(KeyEvent)
     * @see TGameEngine#keyTyped(KeyEvent)
     */
    public void keyReleased(KeyEvent event) {
        // No-op
    }

    /**
     * Override this method to handle key typed events. This event is triggered when a player
     * presses and releases a key in short succession.
     *
     * @see KeyEvent
     * @see TGameEngine#keyPressed(KeyEvent)
     * @see TGameEngine#keyReleased(KeyEvent)
     */
    public void keyTyped(KeyEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse pressed events. This event is triggered when a player
     * presses down on a mouse button.
     *
     * @see MouseEvent
     * @see TGameEngine#mouseReleased(MouseEvent)
     * @see TGameEngine#mouseClicked(MouseEvent)
     */
    public void mousePressed(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse released events. This event is triggered when a
     * player releases a mouse button, so usually follows a mousePressed event.
     *
     * @see MouseEvent
     * @see TGameEngine#mousePressed(MouseEvent)
     * @see TGameEngine#mouseClicked(MouseEvent)
     */
    public void mouseReleased(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse clicked events. This event is triggered when a player
     * presses and releases a mouse button in short succession.
     *
     * @see MouseEvent
     * @see TGameEngine#mousePressed(MouseEvent)
     * @see TGameEngine#mouseReleased(MouseEvent)
     */
    public void mouseClicked(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse dragged events. This event is triggered when a player
     * presses a mouse button and then moves the mouse without releasing the mouse button.
     *
     * @see MouseEvent
     * @see TGameEngine#mousePressed(MouseEvent)
     * @see TGameEngine#mouseMoved(MouseEvent)
     */
    public void mouseDragged(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse entered events. This event is triggered when a player
     * moves the mouse onto the window.
     *
     * @see MouseEvent
     * @see TGameEngine#mouseExited(MouseEvent)
     */
    public void mouseEntered(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse exited events. This event is triggered when a player
     * moves the mouse out of the window.
     *
     * @see MouseEvent
     * @see TGameEngine#mouseEntered(MouseEvent)
     */
    public void mouseExited(MouseEvent event) {
        // No-op
    }

    /**
     * Override this method to handle mouse moved events. This event is triggered any time a
     * player moves their mouse, and so generates quite a lot of events.
     *
     * @see MouseEvent
     */
    public void mouseMoved(MouseEvent event) {
        // No-op
    }

    //--------------------------------------------------------------------------------- Drawing --//

    /**
     * The window component that encapsulates and draws what the player sees on the screen.
     */
    protected class GamePanel extends JPanel {
        @Serial
        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics graphics) {
            graphics2D = (Graphics2D) graphics;

            transforms.clear();
            transforms.push(graphics2D.getTransform());

            graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

            if (initialized) {
                // TODO: We should not be wrapping the entire engine, we should only be wrapping (Java) Graphics context
                //  Step 2: Introduce a Java Graphics Context which implements
                //  my Graphics Context and wraps the passed Graphics object
                //  Step 3: Remove Massey GameEngine
                TGraphicsCtx ctx = new MasseyGraphicsCtx(TGameEngine.this);
                TGameEngine.this.paint(ctx);
            }
        }
    }

    //----------------------------------------------- Massey's Original Wrapped Drawing Methods --//

    /**
     * Changes the background color of the canvas to the specified color.
     */
    public void changeBackgroundColor(Color color) {
        graphics2D.setBackground(color);
    }

    /**
     * Clears the canvas and set the background color, if there is one.
     */
    public void clearBackground(int width, int height) {
        graphics2D.clearRect(0, 0, width, height);
    }

    /**
     * Changes the drawing color to the specified color.
     */
    public void changeColor(Color color) {
        graphics2D.setColor(color);
    }

    /**
     * Draws a line from (x1, y2) to (x2, y2).
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    /**
     * Draws a line from (x1, y2) to (x2, y2) with width l.
     */
    public void drawLine(double x1, double y1, double x2, double y2, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draws a line from <code>p1</code> to <code>p2</code>.
     */
    public void drawLine(Point p1, Point p2) {
        graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
    }

    /**
     * Draws a line from <code>p1</code> to <code>p2</code> with width l.
     */
    public void drawLine(Point p1, Point p2, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draws an outlined rectangle at (x, y) with width and height (w, h).
     */
    public void drawRectangle(double x, double y, double w, double h) {
        graphics2D.draw(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draws an outlined rectangle at (x, y) with width and height (w, h) with a line of width l.
     */
    public void drawRectangle(double x, double y, double w, double h, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Rectangle2D.Double(x, y, w, h));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draws a filled rectangle at (x, y) with width and height (w, h).
     */
    public void drawSolidRectangle(double x, double y, double w, double h) {
        graphics2D.fill(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draws an outlined circle at (x, y) with radius
     */
    public void drawCircle(double x, double y, double radius) {
        graphics2D.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draws an outlined circle at (x, y) with radius with a line of width l.
     */
    public void drawCircle(double x, double y, double radius, double l) {
        // Set the stroke
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));

        // Reset the stroke
        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draws a filled circle at (x, y) with radius.
     */
    public void drawSolidCircle(double x, double y, double radius) {
        graphics2D.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draws text on the screen at (x, y).
     */
    public void drawText(double x, double y, String s, Font font) {
        graphics2D.setFont(font);
        graphics2D.drawString(s, (int) x, (int) y);
    }

    /**
     * Loads an image from file.
     */
    public Image loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Error: could not load image " + filename);
            System.exit(1);
        }

        return null;
    }

    /**
     * Loads a sub-image out of an image.
     */
    public Image subImage(Image source, int x, int y, int w, int h) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage(x, y, w, h);
    }

    /**
     * Draws an image on the screen at position (x, y).
     */
    public void drawImage(Image image, double x, double y) {
        if (image == null) {
            System.err.println("Error: cannot draw null image.");
            return;
        }

        graphics2D.drawImage(image, (int) x, (int) y, null);
    }

    /**
     * Draws an image on the screen at position (x, y).
     */
    public void drawImage(Image image, double x, double y, double w, double h) {
        if (image == null) {
            System.err.println("Error: cannot draw null image.");
            return;
        }
        graphics2D.drawImage(image, (int) x, (int) y, (int) w, (int) h, null);
    }

    // ----------------------------------------------------------------------------- Transforms --//

    /**
     * Saves the current transform.
     */
    public void saveCurrentTransform() {
        transforms.push(graphics2D.getTransform());
    }

    /**
     * Restores the last transform.
     */
    public void restoreLastTransform() {
        graphics2D.setTransform(transforms.peek());

        if (transforms.size() > 1) {
            transforms.pop();
        }
    }

    /**
     * Translates the drawing context by (dx, dy).
     */
    public void translate(double dx, double dy) {
        graphics2D.translate(dx, dy);
    }

    /**
     * Rotates the drawing context by the given degrees around the point that is (dx, dy) from
     * the origin.
     */
    public void rotate(double thetaDegrees, int dx, int dy) {
        graphics2D.rotate(Math.toRadians(thetaDegrees), dx, dy);
    }

    /**
     * Scales the drawing context by (x, y)
     */
    public void scale(double x, double y) {
        graphics2D.scale(x, y);
    }

    /**
     * Shears the drawing context by (x, y)
     */
    public void shear(double x, double y) {
        graphics2D.shear(x, y);
    }
}
