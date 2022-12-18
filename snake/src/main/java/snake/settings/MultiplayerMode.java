package snake.settings;

/**
 * An enum that ensures we only need to handle either single or multiplayer mode. This supports
 * having preset configurations for each mode, e.g. HUD layout, and game logic optimized for one or
 * two players.
 *
 * @author Tessa Power
 */
public enum MultiplayerMode {
    SINGLE_PLAYER, MULTIPLAYER
}
