package snake.game;

import snake.settings.GameMode;
import snake.settings.MultiplayerMode;

/**
 * A simple record (C/C++ struct) to store the configuration for a given game.
 *
 * @see MultiplayerMode
 * @see GameMode
 */
public record GameConfig(MultiplayerMode multiplayerMode, GameMode gameMode) {}

