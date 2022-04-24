package snake.ui.screens.gameplay;

import snake.assets.AnimatedSnek;
import snake.player.PlayerNumber;
import tengine.graphics.entities.sprites.SpriteSequence;

import java.awt.*;

public class Avatar {
    AnimatedSnek snek;

    public Avatar(PlayerNumber playerNumber) {
        snek = AnimatedSnek.animatedSnek(playerNumber);
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
