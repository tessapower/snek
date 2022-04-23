package snake.player;

public class Player {
    public static final int NUM_STARTING_LIVES = 3;

    private PlayerNumber playerNumber;
    private PlayerControls playerControls;
    private int score;
    private int livesLeft;

    public Player(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        this.playerControls = PlayerControls.controlsForPlayer(playerNumber);
        livesLeft = NUM_STARTING_LIVES;
        score = 0;
    }

    public void increaseScore() {
        score++;
    }

    public void reduceLivesLeft() {
        livesLeft--;
    }

    public int score() {
        return score;
    }

    public int livesLeft() {
        return livesLeft;
    }

    public PlayerNumber playerNumber() {
        return playerNumber;
    }

    public PlayerControls controls() {
        return playerControls;
    }
}
