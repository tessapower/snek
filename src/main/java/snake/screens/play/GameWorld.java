package snake.screens.play;

import snake.GameConfig;
import snake.GameResult;
import snake.MultiplayerMode;
import snake.apple.Apple;
import snake.player.Player;
import snake.player.PlayerConfig;
import snake.snek.SnekPlayer;
import tengine.Actor;
import tengine.world.GridSquare;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GameWorld extends World {
    private static final int N_TILES = 32;

    // Future support for offsetting the world around other UI elements
    private final Point origin;
    private final Grid grid;
    // TODO: Change this for supplier?
    private final GameOverNotifier gameOverNotifier;
    private final GameConfig gameConfig;

    // Players
    private SnekPlayer playerOne;
    private SnekPlayer playerTwo = null;

    // Apples
    private final Set<Apple> apples;

    public GameWorld(Point origin, Dimension dimension, GameOverNotifier gameOverNotifier, GameConfig gameConfig) {
        super(dimension);

        // The world is a fixed size, but the location of where it can be placed in a
        // window can differ, so we need the origin relative to the window.
        this.origin = origin;
        this.gameOverNotifier = gameOverNotifier;
        this.gameConfig = gameConfig;

        // The grid tile size is fixed, so we just specify the
        // number of tiles to create different sized grids
        grid = new Grid(N_TILES, N_TILES);

        initPlayers(gameConfig);

        apples = new HashSet<>();
        apples.add(Apple.spawnAt(this, randomUnoccupiedSquare()));
    }

    private void initPlayers(GameConfig gameConfig) {
        playerOne = SnekPlayer.spawnAt(this, playerOneSpawnSquare(), PlayerConfig.configFor(Player.PLAYER_ONE));

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SnekPlayer.spawnAt(this, playerTwoSpawnSquare(), PlayerConfig.configFor(Player.PLAYER_TWO));
        }
    }

    public void update(double dt) {
        playerOne.update(dt);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.update(dt);
        }

        checkCollisions(playerOne);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            checkCollisions(playerTwo);
        }
    }

    public void checkCollisions(SnekPlayer player) {
        if (player.hasHitWall() || player.hasHitSelf() || playersCollided()) {
            // TODO: play BONK! noise
            setGameOver();
        }

        Apple maybeApple = checkForEatenApples(player);

        if (maybeApple != null) {
            player.eat(maybeApple, gameConfig.gameMode());

            apples.remove(maybeApple);
            maybeApple.removeFromWorld();
            apples.add(Apple.spawnAt(this, randomUnoccupiedSquare()));
        }
    }

    public boolean playersCollided() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            return playerOne.occupies(playerTwo.gridSquare()) || playerTwo.occupies(playerOne.gridSquare());
        }

        return false;
    }

    private void setGameOver() {
        // TODO: Implement life bonus!
        int playerOneScore = playerOne.state().score();
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            int playerTwoScore = playerTwo.state().score();

            if (playerOneScore < playerTwoScore) {
                GameResult.shared().setWinner(Player.PLAYER_TWO);
                GameResult.shared().setWinningScore(playerTwo.state().score());
                GameResult.shared().setLosingScore(playerOne.state().score());
            } else if (playerOneScore > playerTwoScore) {
                GameResult.shared().setWinner(Player.PLAYER_ONE);
                GameResult.shared().setWinningScore(playerOne.state().score());
                GameResult.shared().setLosingScore(playerTwo.state().score());
            } else {
                GameResult.shared().setWinningScore(playerOneScore);
                GameResult.shared().setWinner(null);
            }
        } else {
            GameResult.shared().setWinningScore(playerOneScore);
        }

        gameOverNotifier.notifyGameOver();
    }

    public Grid grid() {
        return grid;
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (playerOne.handleKeyEvent(keyEvent)) return;

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.handleKeyEvent(keyEvent);
        }
    }

    private Apple checkForEatenApples(SnekPlayer player) {
        for (var apple : apples) {
            if (apple.gridSquare().equals(player.gridSquare())) {
                return apple;
            }
        }

        return null;
    }

    private GridSquare playerOneSpawnSquare() {
        return new GridSquare(4, 4);
    }

    private GridSquare playerTwoSpawnSquare() {
        return new GridSquare(8, 4);
    }

    private GridSquare randomUnoccupiedSquare() {
        // TODO: handle no occupied squares
        GridSquare randomSquare;

        do {
            randomSquare = grid.randomGridSquare();
        } while (getActorAtSquare(randomSquare) != null);

        return randomSquare;
    }

    private Actor getActorAtSquare(GridSquare gridSquare) {
        if (playerOne.occupies(gridSquare)) {
            return playerOne;
        }

        for (Actor actor : actors) {
            if (actor instanceof SnekPlayer) continue;
            if (((Apple) actor).gridSquare().equals(gridSquare)) {
                return actor;
            }
        }

        return null;
    }
}
