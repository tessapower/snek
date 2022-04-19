package screens;

@FunctionalInterface
public interface ScreenChangeRequestCallback {
    void requestScreenChange(ScreenIdentifier screen);
}
