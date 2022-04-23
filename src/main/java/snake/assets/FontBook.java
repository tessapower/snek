package snake.assets;

import com.google.inject.Singleton;
import snake.utils.ResourceLoader;

import java.awt.*;
import java.io.IOException;

@Singleton
public final class FontBook {
    private static final String FONT_NAME = "RetroGaming.ttf";

    private static FontBook singleton = null;

    private Font gameFont;

    // TODO: Check standard singleton pattern in Java, maybe plain old statics can be used for this?
    public static FontBook shared() {
        if (singleton == null) {
            singleton = new FontBook();
        }

        return singleton;
    }

    private FontBook() {
        loadFonts();
    }

    private void loadFonts() {
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.load(FONT_NAME));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Font titleFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 32);
    }

    public Font scoreBoardFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 20);
    }

    public Font buttonFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 18);
    }

    public Font bodyFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 16);
    }
}
