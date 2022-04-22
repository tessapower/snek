package snake.screens.play;

import snake.GameConfig;
import snake.MultiplayerMode;
import snake.player.PlayerState;
import snake.snek.SnekPlayer;

public class GameState {
    private final GameConfig gameConfig;
    private final PlayerState playerOneState;
    private PlayerState playerTwoState = null;

    public GameState(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        playerOneState = new PlayerState(SnekPlayer.NUM_STARTING_LIVES);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwoState = new PlayerState(SnekPlayer.NUM_STARTING_LIVES);
        }
    }

    public GameConfig gameConfig() {
        return gameConfig;
    }

    public PlayerState playerOneState() {
        return playerOneState;
    }

    public PlayerState playerTwoState() {
        return playerTwoState;
    }
}
