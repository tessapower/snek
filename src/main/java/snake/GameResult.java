package snake;

import com.google.inject.Singleton;
import snake.player.Player;

@Singleton
public class GameResult {
    private static GameResult singleton = null;

    private static Player winner;
    private static int winningScore;
    private static int losingScore;

    public static GameResult shared() {
        if (singleton == null) {
            singleton = new GameResult();
        }

        return singleton;
    }

    private GameResult()  {
        winner = null;
        winningScore = 0;
        losingScore = 0;
    }

    public void setWinner(Player winner) {
        GameResult.winner = winner;
    }

    public void setWinningScore(int score) {
        GameResult.winningScore = score;
    }

    public void setLosingScore(int score) {
        GameResult.losingScore = score;
    }

    public Player winner() {
        return winner;
    }

    public int winningScore() {
        return winningScore;
    }

    public int losingScore() {
        return winningScore;
    }
}
