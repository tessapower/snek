package snake.ui.screens.gameplay;

/**
 * A simple callback method that notifies the receiver when the game is over.
 *
 * @author Tessa Power
 */
@FunctionalInterface
public interface GameOverNotifier {
    void notifyGameOver();
}
