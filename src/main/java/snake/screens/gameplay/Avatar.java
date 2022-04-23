package snake.screens.gameplay;

import snake.actors.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.sprites.SpriteSequence;

import java.awt.*;

public class Avatar {
    final AnimatedSnek snek = AnimatedSnek.animatedSnek();

    public Avatar() {
        snek.setState(AnimatedSnek.State.IDLE);
        snek.setSequenceEndCallback(this::onAnimationSequenceEnd);
    }

    public int width() {
        return snek.width();
    }

    public int height() {
        return snek.height();
    }

    public void setOrigin(Point origin) {
        snek.setOrigin(origin);
    }

    public void eat() {
        snek.setFps(10);
        snek.setState(AnimatedSnek.State.EATING);
    }

    private void onAnimationSequenceEnd(SpriteSequence spriteSequence) {
        if (spriteSequence.id().equals("EATING")) {
            snek.setFps(AnimatedSnek.DEFAULT_FPS);
            snek.setState(AnimatedSnek.State.IDLE);
        }
    }
}
