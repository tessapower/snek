package snake.assets;

import tengine.AudioClip;

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

    public static SoundEffects shared() {
        if (singleton == null) {
            singleton = new SoundEffects();
        }

        return singleton;
    }

    private SoundEffects() {
    }

    public AudioClip backgroundMusic() {
        return background;
    }

    public AudioClip goodApple() {
        return goodApple;
    }

    public AudioClip badApple() { return badApple; }

    public AudioClip gameOver() {
        return hit;
    }

    public AudioClip menuMove() {
        return menuMove;
    }

    public AudioClip menuSelect() {
        return menuSelect;
    }
}
