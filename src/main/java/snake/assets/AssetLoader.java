package snake.assets;

import java.io.InputStream;

public final class AssetLoader {
    public static InputStream load(String path) {
         return AssetLoader.class.getClassLoader().getResourceAsStream(path);
    }
}
