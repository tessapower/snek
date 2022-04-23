package snake.settings;

import com.google.inject.Singleton;
import snake.game.GameConfig;

@Singleton
public class Settings {
    private static Settings singleton = null;

    private static MultiplayerMode playerMode;
    private static GameMode gameMode;

    public static Settings shared() {
        if (singleton == null) {
            singleton = new Settings();
        }

        return singleton;
    }

    private Settings()  {
        gameMode = GameMode.NORMAL;
        playerMode = MultiplayerMode.SINGLE_PLAYER;
    }

    public GameConfig config() {
        return new GameConfig(Settings.playerMode, Settings.gameMode);
    }

    public void setGameMode(GameMode gameMode) {
        Settings.gameMode = gameMode;
    }

    public void setPlayerMode(MultiplayerMode playerMode) {
        Settings.playerMode = playerMode;
    }

    public GameMode gameMode() {
        return gameMode;
    }
}
