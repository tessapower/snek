package snake.assets;

import tengine.audio.TAudioClip;

/**
 * A Singleton for accessing audio clips for different sound effects in <code>snek!</code> After
 * retrieving the <code>TAudioClip</code>, call <code>play()</code> or <code>playOnLoop()</code>
 * to play the sound effect, or <code>stopPlayingLoop()</code> to stop playing looped sound
 * effects, such as background music.
 *
 * @author Tessa Power
 * @see TAudioClip
 */
public class SoundEffects {
    private static final String APPLE_GOOD = "apple-good.wav";
    private static final TAudioClip goodApple = new TAudioClip(AssetLoader.load(APPLE_GOOD));

    private static final String APPLE_BAD = "apple-bad.wav";
    private static final TAudioClip badApple = new TAudioClip(AssetLoader.load(APPLE_BAD));

    private static final String HIT = "hit.wav";
    private static final TAudioClip hit = new TAudioClip(AssetLoader.load(HIT));

    private static final String BACKGROUND_MUSIC = "background.wav";
    private static final TAudioClip background = new TAudioClip(AssetLoader.load(BACKGROUND_MUSIC));

    private static final String MENU_MOVE = "menu.wav";
    private static final TAudioClip menuMove = new TAudioClip(AssetLoader.load(MENU_MOVE));

    private static final String MENU_SELECT = "menu-select.wav";
    private static final TAudioClip menuSelect = new TAudioClip(AssetLoader.load(MENU_SELECT));

    private static SoundEffects singleton = null;

    /**
     * Offers access to the single instance of the <code>SoundEffects</code> class.
     */
    public static SoundEffects shared() {
        if (singleton == null) {
            singleton = new SoundEffects();
        }

        return singleton;
    }

    /**
     * Private constructor for the Singleton.
     */
    private SoundEffects() {
        // No-op
    }

    /**
     * The <code>TAudioClip</code> used for background music. Play this on a loop by calling
     * <code>playOnLoop()</code>.
     */
    public TAudioClip backgroundMusic() {
        return background;
    }

    /**
     * The <code>TAudioClip</code> played when <code>snek</code> eats a good <code>Apple</code>.
     */
    public TAudioClip goodApple() {
        return goodApple;
    }

    /**
     * The <code>TAudioClip</code> played when <code>snek</code> eats a poisonous <code>Apple</code>.
     */
    public TAudioClip badApple() {
        return badApple;
    }

    /**
     * The <code>TAudioClip</code> played when <code>snek</code> dies.
     */
    public TAudioClip gameOver() {
        return hit;
    }

    /**
     * Play this <code>TAudioClip</code> when the player moves the arrow keys in the menu or game
     * over screen.
     */
    public TAudioClip menuMove() {
        return menuMove;
    }

    /**
     * Play this <code>TAudioClip</code> when the player selects an option in the menu or game
     * over screen.
     */
    public TAudioClip menuSelect() {
        return menuSelect;
    }
}
