package snake.screens.play;

import snake.snek.AnimatedSnek;
import tengine.graphics.graphicsObjects.sprites.SpriteSequence;

import java.awt.*;

public class Avatar {
    AnimatedSnek snek = AnimatedSnek.animatedSnek();

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
        if (spriteSequence.id().equals("eating")) {
            snek.setFps(AnimatedSnek.defaultFps());
            snek.setState(AnimatedSnek.State.IDLE);
        }
    }
}
