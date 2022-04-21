package snake.player;

public class PlayerState {
    private int score;
    private int livesLeft;

    public PlayerState(int numStartingLives) {
        livesLeft = numStartingLives;
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
}
