package snake.ui.screens;

@FunctionalInterface
public interface ScreenChangeRequestCallback {
    void requestScreenChange(ScreenIdentifier screen);
}
