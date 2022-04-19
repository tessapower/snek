package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontBook {
    private static final String RETRO_FONT_FILE = "src/main/resources/RetroGaming.ttf";

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
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File(RETRO_FONT_FILE));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Font titleFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 32);
    }

    public Font promptFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 24);
    }

    public Font instructionFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 16);
    }
}