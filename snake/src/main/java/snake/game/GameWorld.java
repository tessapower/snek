package snake.game;

import snake.actors.apple.Apple;
import snake.actors.apple.AppleType;
import snake.actors.snek.Direction;
import snake.actors.snek.Snek;
import snake.assets.SoundEffects;
import snake.settings.MultiplayerMode;
import snake.ui.screens.gameplay.GameOverNotifier;
import snake.ui.screens.gameplay.HeadsUpDisplay;
import tengine.Actor;
import tengine.world.GridSquare;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameWorld extends World {
    private static final Random RANDOM = new Random();
    private static final double RANDOM_CHANCE = 0.35;
    private static final int TILE_COLS = 28;
    private static final int TILE_ROWS = 24;

    // Future support for offsetting the world around other UI elements
    private final Grid grid;
    private final GameOverNotifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;

    private final HeadsUpDisplay hud;

    // Players
    private Snek playerOne;
    private Snek playerTwo = null;

    // Apples
    private Apple goodApple;
    private final Set<Apple> badApples;

    public GameWorld(Dimension dimension, GameOverNotifier gameOverNotifier, GameState gameState) {
        super(dimension);

        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        // Play Area
        Dimension playAreaDimension = new Dimension(TILE_COLS * Grid.TILE_SIZE, TILE_ROWS * Grid.TILE_SIZE);
        Point playAreaOrigin =
                new Point((int) ((dimension.width - playAreaDimension.width) * 0.5), (int) ((dimension.height - playAreaDimension.height) * 0.66));

        // The grid tile size is fixed, so we just specify the number of tiles to create different sized grids
        grid = new Grid(playAreaOrigin, TILE_ROWS, TILE_COLS);

        initPlayers();

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(), playAreaDimension, playAreaOrigin, gameState);
        canvas.add(hud);

        goodApple = Apple.spawnGoodApple(this, randomUnoccupiedSquare());
        badApples = new HashSet<>();
    }

    private void initPlayers() {
        playerOne = Snek.spawnAt(
                this,
                playerOneSpawnSquare(),
                RANDOM.nextBoolean() ? Direction.RIGHT : Direction.DOWN,
                gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = Snek.spawnAt(
                    this,
                    playerTwoSpawnSquare(),
                    RANDOM.nextBoolean() ? Direction.UP : Direction.LEFT,
                    gameState.playerTwo());
        }
    }

    public void update() {
        playerOne.update();
        if (gameState.playerOne().livesLeft() == 0) {
            setGameOver();
        }

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.update();
            if (gameState.playerTwo().livesLeft() == 0) {
                setGameOver();
            }
        }

        checkCollisions(playerOne);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            checkCollisions(playerTwo);
        }
    }

    private void checkCollisions(Snek player) {
        boolean playerHitSomething = player.hasHitWall() || player.hasHitSelf() || playersCollided();
        if (playerHitSomething) setGameOver();

        Apple maybeApple = checkForEatenApples(player);

        if (maybeApple != null) {
            player.eat(maybeApple);

            hud.animateAvatar();

            maybeApple.removeFromWorld();

            if (maybeApple.appleType() == AppleType.CROMCHY) {
                SoundEffects.shared().goodApple().play();
                goodApple = Apple.spawnGoodApple(this, randomUnoccupiedSquare());
            } else {
                SoundEffects.shared().badApple().play();
                badApples.remove(maybeApple);
            }

            if (RANDOM.nextDouble() < RANDOM_CHANCE) badApples.add(Apple.spawnBadApple(this, randomUnoccupiedSquare()));
        }

    }

    private boolean playersCollided() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            return playerOne.occupies(playerTwo.headGridSquare()) || playerTwo.occupies(playerOne.headGridSquare());
        }

        return false;
    }

    public Grid grid() {
        return grid;
    }

    private void setGameOver() {
        gameOverNotifier.notifyGameOver();
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (playerOne.handleKeyEvent(keyEvent)) return;

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.handleKeyEvent(keyEvent);
        }
    }

    private Apple checkForEatenApples(Snek player) {
        if (goodApple.gridSquare().equals(player.headGridSquare())) {
            return goodApple;
        }

        for (var badApple : badApples) {
            if (badApple.gridSquare().equals(player.headGridSquare())) {
                return badApple;
            }
        }

        return null;
    }

    private GridSquare playerOneSpawnSquare() {
        return new GridSquare(10, 10);
    }

    private GridSquare playerTwoSpawnSquare() {
        return new GridSquare(20, 20);
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
            if (actor instanceof Snek) continue;
            if (((Apple) actor).gridSquare().equals(gridSquare)) {
                return actor;
            }
        }

        return null;
    }

    public GameConfig gameConfig() {
        return gameConfig;
    }
}
