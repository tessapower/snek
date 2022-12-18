package snake.settings;

import snake.game.GameConfig;

/**
 * A Singleton for configuring the settings for <code>snek!</code>
 */
public class Settings {
    private static Settings singleton = null;

    private static MultiplayerMode multiplayerMode;
    private static GameMode gameMode;

    /**
     * Offers access to the single instance of the <code>Settings</code> class.
     */
    public static Settings shared() {
        if (singleton == null) {
            singleton = new Settings();
        }

        return singleton;
    }

    /**
     * Private constructor for the Singleton. Sets the initial <code>GameMode</code> to
     * <code>NORMAL</code> and the initial <code>MultiplayerMode</code> to
     * <code>SINGLE_PLAYER</code>.
     */
    private Settings()  {
        gameMode = GameMode.NORMAL;
        multiplayerMode = MultiplayerMode.SINGLE_PLAYER;
    }

    /**
     * The current configuration for <code>snek!</code> with the set <code>MultiplayerMode</code>
     * and <code>>GameMode</code>.
     *
     * @see MultiplayerMode
     * @see GameMode
     */
    public GameConfig config() {
        return new GameConfig(Settings.multiplayerMode, Settings.gameMode);
    }

    /**
     * Sets the <code>GameMode</code> for <code>snek!</code> Retrieving the <code>GameConfig</code>
     * from <code>Settings</code> after calling this method will use the newly set
     * <code>GameMode</code>.
     */
    public void setGameMode(GameMode gameMode) {
        Settings.gameMode = gameMode;
    }

    /**
     * Sets the <code>MultiplayerMode</code> for <code>snek!</code> Retrieving the
     * <code>GameConfig</code> from <code>Settings</code> after calling this method will use the
     * newly set <code>MultiplayerMode</code>.
     */
    public void setPlayerMode(MultiplayerMode multiplayerMode) {
        Settings.multiplayerMode = multiplayerMode;
    }

    /**
     * The current <code>GameMode</code> for <code>snek!</code>
     */
    public GameMode gameMode() {
        return gameMode;
    }
}
