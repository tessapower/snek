package snake.screens;

@FunctionalInterface
public interface ScreenChangeRequestCallback {
    void requestScreenChange(ScreenIdentifier screen);
}
