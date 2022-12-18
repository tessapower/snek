package snake.player;

/**
 * Encapsulates the player model, including the <code>PlayerNumber</code> and
 * <code>PlayerControls</code>.
 *
 * @author Tessa Power
 * @see PlayerNumber
 * @see PlayerControls
 */
public class Player {
    public static final int NUM_STARTING_LIVES = 3;

    private final PlayerNumber playerNumber;
    private final PlayerControls playerControls;
    private int score;
    private int livesLeft;

    /**
     * Constructs a new <code>Player</code> with the given <code>PlayerNumber</code>.
     *
     * @see PlayerNumber
     */
    public Player(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        this.playerControls = PlayerControls.controlsForPlayer(playerNumber);
        livesLeft = NUM_STARTING_LIVES;
        score = 0;
    }

    /**
     * Increases the score for this <code>Player</code>.
     */
    public void increaseScore() {
        score++;
    }

    /**
     * Reduces the number of lives left this <code>Player</code> has.
     */
    public void reduceLivesLeft() {
        livesLeft--;
    }

    /**
     * The <code>Player</code>'s current score.
     */
    public int score() {
        return score;
    }

    /**
     * The number of lives left this <code>Player</code> has.
     */
    public int livesLeft() {
        return livesLeft;
    }

    /**
     * The <code>PlayerNumber</code> for this <code>Player</code>.
     */
    public PlayerNumber playerNumber() {
        return playerNumber;
    }

    /**
     * The <code>PlayerControls</code> for this <code>Player</code>.
     */
    public PlayerControls controls() {
        return playerControls;
    }
}
