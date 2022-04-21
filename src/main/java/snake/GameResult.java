package snake;

import com.google.inject.Singleton;
import snake.player.Player;

@Singleton
public class GameResult {
    private static GameResult singleton = null;

    private static Player winner;
    private static int finalScore;

    public static GameResult shared() {
        if (singleton == null) {
            singleton = new GameResult();
        }

        return singleton;
    }

    private GameResult()  {
        winner = null;
        finalScore = 0;
    }

    public void setWinner(Player winner) {
        GameResult.winner = winner;
    }

    public void setFinalScore(int finalScore) {
        GameResult.finalScore = finalScore;
    }

    public Player winner() {
        return winner;
    }

    public int finalScore() {
        return finalScore;
    }
}
