package snake.settings;

public enum GameMode {
    NORMAL, INFINITE;

    public GameMode toggle() {
        return switch(this) {
            case NORMAL -> INFINITE;
            case INFINITE -> NORMAL;
        };
    }
}
