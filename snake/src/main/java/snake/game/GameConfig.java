package snake.game;

import snake.settings.GameMode;
import snake.settings.MultiplayerMode;

public record GameConfig(MultiplayerMode multiplayerMode, GameMode gameMode) {
}

