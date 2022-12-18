package snake.game;

import snake.actors.apple.*;
import snake.actors.rock.Rock;
import snake.actors.snek.Direction;
import snake.actors.snek.Snek;
import snake.assets.SoundEffects;
import snake.player.PlayerControls;
import snake.settings.MultiplayerMode;
import snake.ui.screens.gameplay.GameOverNotifier;
import snake.ui.screens.gameplay.HeadsUpDisplay;
import tengine.world.World;
import tengine.TActor;
import tengine.world.TGridSquare;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class that contains all of the <code>TActor</code>s (a.k.a. game objects) and game logic for
 * <code>snek!</code> Handles the game play and notifying the main controller when the game is over.
 */
public class GameWorld extends World {
    // Random number generation
    private static final Random RNG = new Random();
    private static final double RANDOM_CHANCE_THRESHOLD = 0.35;

    // Settings for the Grid
    private static final int GRID_COLS = 28;
    private static final int GRID_ROWS = 24;
    private final Grid grid;

    // Game state and configuration
    private final GameOverNotifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;

    // HUD
    private final HeadsUpDisplay hud;

    // Players
    private Snek playerOne;
    private Snek playerTwo = null;

    // Apples
    private Apple goodApple;
    private final Set<Apple> badApples;
    private final Set<Rock> rocks;

    /**
     * Construct a new <code>GameWorld</code> with the given dimension and starting
     * <code>GameState</code>. Will call the given <code>GameOverNotifier</code> callback method
     * once the game is over.
     *
     * @see GameOverNotifier
     * @see GameState
     */
    public GameWorld(Dimension dimension, GameOverNotifier gameOverNotifier, GameState gameState) {
        super(dimension);

        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        // Play Area
        Dimension playAreaDimension = new Dimension(GRID_COLS * Grid.SQUARE_SIZE, GRID_ROWS * Grid.SQUARE_SIZE);
        Point playAreaOrigin =
                new Point((int) ((dimension.width - playAreaDimension.width) * 0.5), (int) ((dimension.height - playAreaDimension.height) * 0.66));

        // The grid square size is fixed, so we just specify the number of tiles to create
        // different sized grids
        grid = new Grid(playAreaOrigin, GRID_ROWS, GRID_COLS);

        initPlayers();

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(), playAreaDimension, playAreaOrigin, gameState);
        canvas.add(hud);

        badApples = new HashSet<>();
        rocks = new HashSet<>();
        goodApple = Apple.spawnGoodApple(this, randomUnoccupiedSquare());
    }

    private void initPlayers() {
        playerOne = Snek.spawnAt(
            this,
            playerOneSpawnSquare(),
            RNG.nextBoolean() ? Direction.RIGHT : Direction.DOWN,
            gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = Snek.spawnAt(
                this,
                playerTwoSpawnSquare(),
                RNG.nextBoolean() ? Direction.UP : Direction.LEFT,
                gameState.playerTwo());
        }
    }

    /**
     * Updates this <code>GameWorld</code>, which moves each <code>Player</code>, checks for
     * collisions, and sets game over if necessary.
     */
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

    /**
     * Checks for collisions between each <code>Player</code> and any other <code>TActor</code>
     * and handles the collision appropriately.
     */
    private void checkCollisions(Snek player) {
        boolean playerHitSomething = player.hasHitWall() || player.hasHitSelf() || playersCollided() || playerHitRock(player);
        if (playerHitSomething) setGameOver();

        Apple maybeApple = checkForEatenApples(player);

        if (maybeApple != null) {
            player.eat(maybeApple);

            hud.animateAvatar(player.playerNumber());

            maybeApple.removeFromWorld();

            if (maybeApple.appleType() == AppleType.CROMCHY) {
                SoundEffects.shared().goodApple().play();
                goodApple = Apple.spawnGoodApple(this, randomUnoccupiedSquare());
            } else {
                SoundEffects.shared().badApple().play();
                badApples.remove(maybeApple);
            }

            if (RNG.nextDouble() > RANDOM_CHANCE_THRESHOLD) {
                if (RNG.nextDouble() < RANDOM_CHANCE_THRESHOLD) {
                    badApples.add(Apple.spawnBadApple(this, randomUnoccupiedSquare()));
                } else {
                    rocks.add(Rock.spawnRockAt(this, randomUnoccupiedSquare()));
                }
            }
        }

    }

    /**
     * If the <code>MultiplayerMode</code> is set to <code>MULTIPLAYER</code>, returns whether
     * player one or player two collided with the other.
     */
    private boolean playersCollided() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            return playerOne.occupies(playerTwo.headGridSquare()) || playerTwo.occupies(playerOne.headGridSquare());
        }

        return false;
    }

    /**
     * Whether the given <code>Snek</code> player has hit a <code>Rock</code>.
     *
     * @see Snek
     * @see Rock
     */
    private boolean playerHitRock(Snek player) {
        for (var rock : rocks) {
            if (rock.gridSquare().equals(player.headGridSquare())) {
                return true;
            }
        }

        return false;
    }

    /**
     * The <code>Grid</code> belonging to this <code>GameWorld</code>.
     */
    public Grid grid() {
        return grid;
    }

    /**
     * Calls the <code>GameOverNotifier</code> callback method to notify the receiver that the
     * game is over.
     */
    private void setGameOver() {
        gameOverNotifier.notifyGameOver();
    }

    /**
     * Handles the given <code>KeyEvent</code> by dispatching relevant <code>KeyEvent</code>s
     * to the appropriate player.
     *
     * @see KeyEvent
     * @see PlayerControls
     */
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (playerOne.handleKeyEvent(keyEvent)) return;

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.handleKeyEvent(keyEvent);
        }
    }

    /**
     * Finds any apples that have been eaten by the given <code>Player</code>, if any, or returns
     * null.
     */
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

    /**
     * The <code>TGridSquare</code> where player one is spawned.
     */
    private TGridSquare playerOneSpawnSquare() {
        return new TGridSquare(10, 10);
    }

    /**
     * The <code>TGridSquare</code> where player two is spawned.
     */
    private TGridSquare playerTwoSpawnSquare() {
        return new TGridSquare(20, 20);
    }

    /**
     * Finds a random square in the <code>Grid</code> that doesn't have any <code>TActor</code>
     * already on it.
     */
    private TGridSquare randomUnoccupiedSquare() {
        TGridSquare randomSquare;

        do {
            randomSquare = grid.randomGridSquare();
        } while (getActorAtSquare(randomSquare) != null);

        return randomSquare;
    }

    /**
     * Returns the <code>TActor</code> that is on the given <code>TGridSquare</code>, if any, or
     * returns null.
     */
    private TActor getActorAtSquare(TGridSquare gridSquare) {
        if (playerOne.occupies(gridSquare)) {
            return playerOne;
        }

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            if (playerTwo.occupies(gridSquare)) {
                return playerOne;
            }
        }

        if (goodApple != null && goodApple.gridSquare().equals(gridSquare)) {
            return goodApple;
        }

        for (var apple : badApples) {
            if (apple.gridSquare().equals(gridSquare)) {
                return apple;
            }
        }

        for (var rock : rocks) {
            if (rock.gridSquare().equals(gridSquare)) {
                return rock;
            }
        }

        return null;
    }

    /**
     * The <code>GameConfig</code> for this <code>GameWorld</code>.
     */
    public GameConfig gameConfig() {
        return gameConfig;
    }
}
