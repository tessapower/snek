package screens;

import java.awt.event.KeyEvent;

public interface Screen {
    void handleKeyEvent(KeyEvent event);
    void addToCanvas();
    void removeFromCanvas();
    GameScreen screen();
    void update(double dtMillis);
}
