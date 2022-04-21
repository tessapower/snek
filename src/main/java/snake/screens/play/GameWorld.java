package snake.screens.play;

import snake.GameConfig;
import snake.MultiplayerMode;
import snake.apple.Apple;
import snake.player.Player;
import snake.player.PlayerConfig;
import snake.player.PlayerState;
import snake.snek.SnekPlayer;
import tengine.Actor;
import tengine.world.GridSquare;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

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
    private Apple apple;

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

        // TODO: init players based on gameConfig
        initPlayers(gameConfig);

        apple = Apple.spawnAt(this, randomUnoccupiedSquare());
    }

    private void initPlayers(GameConfig gameConfig) {
        // Player one config
        playerOne = SnekPlayer.spawnAt(this, playerOneSpawnSquare(), PlayerConfig.configFor(Player.PLAYER_ONE));

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SnekPlayer.spawnAt(this, playerTwoSpawnSquare(), PlayerConfig.configFor(Player.PLAYER_TWO));
        }
    }

    public void update(double dt) {
        playerOne.update(dt);

        // TODO: check for number of players and if two player check second player
        if (playerOne.hasHitWall() || playerOne.hasHitSelf()) {
            // TODO: play BONK! noise

            gameOverNotifier.notifyGameOver();
        }

        Apple maybeApple = checkForEatenApples();

        if (maybeApple != null) {
            playerOne.eat(apple, gameConfig.gameMode());

            apple.removeFromWorld();
            apple = Apple.spawnAt(this, randomUnoccupiedSquare());
        }
    }

    public Grid grid() {
        return grid;
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyEvent(KeyEvent keyEvent) {
        // TODO: check key events on player config first
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> playerOne.handleKeyEvent(keyEvent);
        }
    }

    public PlayerState finalGameState() {
        return playerOne.state();
    }

    private Apple checkForEatenApples() {
        return apple.gridSquare().equals(playerOne.gridSquare()) ? apple : null;
    }

    private GridSquare playerOneSpawnSquare() {
        return new GridSquare(4, 4);
    }

    private GridSquare playerTwoSpawnSquare() {
        return new GridSquare(24, 24);
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
