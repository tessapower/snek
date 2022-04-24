package tengine.graphics.entities.sprites;

import tengine.world.GridSquare;

import java.util.List;

public record SpriteSequence(String id, List<GridSquare> frames, boolean loops) {
    public int numFrames() {
        return frames.size();
    }
    public int lastFrame() {
        return numFrames() - 1;
    }
}
