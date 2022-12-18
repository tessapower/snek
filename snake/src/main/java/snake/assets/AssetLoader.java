package snake.assets;

import java.io.InputStream;

/**
 * A helper class to load assets such as images, sound effects, and fonts.
 */
public final class AssetLoader {
    /**
     * Returns an <code>InputStream</code> by loading the asset with the given name found inside
     * the <code>snake/src/main/resources</code> directory. Paths do not need to contain
     * anything other than the file name, e.g. <code>"snake.png"</code>, unless the file is inside
     * nested directories inside <code>resources</code>, e.g. <code>"tiles/rock.png"</code>.
     */
    public static InputStream load(String path) {
        return AssetLoader.class.getClassLoader().getResourceAsStream(path);
    }
}
