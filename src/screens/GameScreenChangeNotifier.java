package screens;

@FunctionalInterface
public interface GameScreenChangeNotifier {
    void notifyScreenChange(ScreenIdentifier screen);
}
