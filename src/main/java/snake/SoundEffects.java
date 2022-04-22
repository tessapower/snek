package snake;

import snake.utils.ResourceLoader;
import tengine.AudioClip;

public class SoundEffects {
    private static final String CRUNCH = "apple-crunch.wav";
    private static final AudioClip crunch = new AudioClip(ResourceLoader.load(CRUNCH));

    private static final String HIT = "hit.wav";
    private static final AudioClip hit = new AudioClip(ResourceLoader.load(HIT));

    private static final String BACKGROUND_MUSIC = "background.wav";
    private static final AudioClip background = new AudioClip(ResourceLoader.load(BACKGROUND_MUSIC));

    private static SoundEffects singleton = null;


    // TODO: Check standard singleton pattern in Java, maybe plain old statics can be used for this?
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

    public AudioClip appleCrunch() {
        return crunch;
    }

    public AudioClip gameOver() {
        return hit;
    }
}
