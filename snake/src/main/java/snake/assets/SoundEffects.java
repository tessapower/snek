package snake.assets;

import tengine.audio.AudioClip;

/**
 * A Singleton for accessing audio clips for different sound effects in <code>snek!</code> After
 * retrieving the <code>AudioClip</code>, call <code>play()</code> or <code>playOnLoop()</code>
 * to play the sound effect, or <code>stopPlayingLoop()</code> to stop playing looped sound
 * effects, such as background music.
 *
 * @author Tessa Power
 * @see AudioClip
 */
public class SoundEffects {
    private static final String APPLE_GOOD = "apple-good.wav";
    private static final AudioClip goodApple = new AudioClip(AssetLoader.load(APPLE_GOOD));

    private static final String APPLE_BAD = "apple-bad.wav";
    private static final AudioClip badApple = new AudioClip(AssetLoader.load(APPLE_BAD));

    private static final String HIT = "hit.wav";
    private static final AudioClip hit = new AudioClip(AssetLoader.load(HIT));

    private static final String BACKGROUND_MUSIC = "background.wav";
    private static final AudioClip background = new AudioClip(AssetLoader.load(BACKGROUND_MUSIC));

    private static final String MENU_MOVE = "menu.wav";
    private static final AudioClip menuMove = new AudioClip(AssetLoader.load(MENU_MOVE));

    private static final String MENU_SELECT = "menu-select.wav";
    private static final AudioClip menuSelect = new AudioClip(AssetLoader.load(MENU_SELECT));

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
     * The <code>AudioClip</code> used for background music. Play this on a loop by calling
     * <code>playOnLoop()</code>.
     */
    public AudioClip backgroundMusic() {
        return background;
    }

    /**
     * The <code>AudioClip</code> played when <code>snek</code> eats a good <code>Apple</code>.
     */
    public AudioClip goodApple() {
        return goodApple;
    }

    /**
     * The <code>AudioClip</code> played when <code>snek</code> eats a poisonous <code>Apple</code>.
     */
    public AudioClip badApple() {
        return badApple;
    }

    /**
     * The <code>AudioClip</code> played when <code>snek</code> dies.
     */
    public AudioClip gameOver() {
        return hit;
    }

    /**
     * Play this <code>AudioClip</code> when the player moves the arrow keys in the menu or game
     * over screen.
     */
    public AudioClip menuMove() {
        return menuMove;
    }

    /**
     * Play this <code>AudioClip</code> when the player selects an option in the menu or game
     * over screen.
     */
    public AudioClip menuSelect() {
        return menuSelect;
    }
}
