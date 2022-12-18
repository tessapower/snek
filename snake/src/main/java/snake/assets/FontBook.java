package snake.assets;

import com.google.inject.Singleton;

import java.awt.*;
import java.io.IOException;

@Singleton
/**
 * A Singleton for accessing fonts for different UI elements in <code>snek!</code>
 *
 * @author Tessa Power
 */
public final class FontBook {
    // Retro Gaming font by Daymarius
    private static final String FONT_NAME = "RetroGaming.ttf";

    private static FontBook singleton = null;

    private Font gameFont;

    /**
     * Offers access to the single instance of the <code>FontBook</code>.
     */
    public static FontBook shared() {
        if (singleton == null) {
            singleton = new FontBook();
        }

        return singleton;
    }

    /**
     * Private constructor for the Singleton.
     */
    private FontBook() {
        loadFonts();
    }

    private void loadFonts() {
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, AssetLoader.load(FONT_NAME));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * The font used for titles.
     */
    public Font titleFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 32);
    }

    /**
     * The font used for scoreboard scores.
     */
    public Font scoreBoardFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 20);
    }

    /**
     * The font used for buttons.
     */
    public Font buttonFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 18);
    }

    /**
     * The font used for body text.
     */
    public Font bodyFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 16);
    }
}
