package utils;

import java.io.InputStream;

public final class ResourceLoader {
    private final static String PREFIX = "main/resources/";

    public static InputStream load(String path) {
         return ResourceLoader.class.getClassLoader().getResourceAsStream(PREFIX + path);
    }
}
