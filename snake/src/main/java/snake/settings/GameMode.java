package snake.settings;

/**
 * An enum that describes the possible game modes. Normal mode limits the length of the player's
 * tail to a maximum length, while infinite mode has no such limitation.
 *
 * @author Tessa Power
 */
public enum GameMode {
    NORMAL, INFINITE;

    public GameMode toggle() {
        return switch(this) {
            case NORMAL -> INFINITE;
            case INFINITE -> NORMAL;
        };
    }
}
