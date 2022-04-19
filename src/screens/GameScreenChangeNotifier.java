package screens;

@FunctionalInterface
public interface GameScreenChangeNotifier {
    void notifyScreenChange(GameScreen screen);
}
