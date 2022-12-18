package snake.game;

import snake.actors.apple.Apple;
import snake.player.Player;
import snake.player.PlayerNumber;
import snake.settings.MultiplayerMode;

import java.util.Optional;

/**
 * A model the state of the game, including the <code>Player</code>s involved and the
 * <code>GameConfig</code> for this game.
 *
 * @author Tessa Power
 * @see Player
 * @see GameConfig
 */
public class GameState {
    private final GameConfig gameConfig;
    private final Player playerOne;
    private Player playerTwo = null;

    /**
     * Constructs a new <code>GameState</code> using the given <code>GameConfig</code>.
     */
    public GameState(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        playerOne = new Player(PlayerNumber.PLAYER_ONE);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = new Player(PlayerNumber.PLAYER_TWO);
        }
    }

    /**
     * The <code>GameConfig</code> for this game.
     */
    public GameConfig gameConfig() {
        return gameConfig;
    }

    /**
     * The data for player one in this game.
     */
    public Player playerOne() {
        return playerOne;
    }

    /**
     * The data for player two in this game, which <strong>may be null</strong>.
     */
    public Player playerTwo() {
        return playerTwo;
    }

    /**
     * The winner of this game, or none if it was a draw.
     */
    public Optional<Player> winner() {
        return switch(gameConfig.multiplayerMode()) {
            case SINGLE_PLAYER -> Optional.of(playerOne);
            case MULTIPLAYER -> {
                if (playerOne.score() == playerTwo.score()) {
                    yield Optional.empty();
                } else {
                    yield Optional.of(playerOne.score() > playerTwo.score() ? playerOne : playerTwo);
                }
            }
        };
    }

    /**
     * The maximum number of <code>Apple</code>s eaten during this game.
     *
     * @see Apple
     */
    public int maxApplesEaten() {
        return winner().orElse(playerOne).score();
    }
}
