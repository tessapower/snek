package snake.ui.screens.gameplay;

import snake.assets.AnimatedSnek;
import snake.player.PlayerNumber;
import tengine.graphics.entities.sprites.SpriteSequence;

import java.awt.*;

/**
 * An adorable animating snake avatar, inspired by Doomguy, that appears in the HUD while the game
 * is being played.
 *
 * @author Tessa Power
 * @see AnimatedSnek
 */
public class Avatar {
    AnimatedSnek snek;

    /**
     * Constructs an <code>Avatar</code> for the given player.
     *
     * @see PlayerNumber
     */
    public Avatar(PlayerNumber playerNumber) {
        snek = AnimatedSnek.animatedSnek(playerNumber);
        snek.setState(AnimatedSnek.State.IDLE);
        snek.setSequenceEndCallback(this::onAnimationSequenceEnd);
    }

    /**
     * Returns the width of this <code>Avatar</code>.
     */
    public int width() {
        return snek.width();
    }

    /**
     * Returns the height of this <code>Avatar</code>.
     */
    public int height() {
        return snek.height();
    }

    /**
     * Set the origin of this <code>Avatar</code>.
     */
    public void setOrigin(Point origin) {
        snek.setOrigin(origin);
    }

    /**
     * Animates this <code>Avatar</code> eating once.
     */
    public void eat() {
        snek.setFps(10);
        snek.setState(AnimatedSnek.State.EATING);
    }

    /**
     * Callback method for <code>MenuScreen</code> to receive notifications when a given
     * <code>SpriteSequence</code> is over.
     *
     * @see SpriteSequence
     */
    private void onAnimationSequenceEnd(SpriteSequence spriteSequence) {
        // Set the Avatar back to idling once the eating animation sequence has ended
        if (spriteSequence.id().equals("EATING")) {
            snek.setFps(AnimatedSnek.DEFAULT_FPS);
            snek.setState(AnimatedSnek.State.IDLE);
        }
    }
}
