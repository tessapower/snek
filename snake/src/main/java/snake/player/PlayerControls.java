package snake.player;

import snake.actors.snek.Action;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Optional;

/**
 * Maps keyboard input as <code>KeyEvent</code>s to <code>Action</code>s to represent player input.
 * There are pre-existing mappings for players 1 and 2 which can be accessed through the static
 * factory method <code>controlsForPlayer(PlayerNumber)</code>.
 * 
 * @author Tessa Power
 * @see KeyEvent
 * @see Action
 * @see PlayerControls#controlsForPlayer(PlayerNumber)
 */
public record PlayerControls(Map<Integer, Action> controls) {
    private static final Map<Integer, Action> PLAYER_ONE_CONTROLS =
            Map.of(KeyEvent.VK_UP, Action.MOVE_UP,
                    KeyEvent.VK_DOWN, Action.MOVE_DOWN,
                    KeyEvent.VK_LEFT, Action.MOVE_LEFT,
                    KeyEvent.VK_RIGHT, Action.MOVE_RIGHT
            );

    private static final Map<Integer, Action> PLAYER_TWO_CONTROLS =
            Map.of(KeyEvent.VK_W, Action.MOVE_UP,
                    KeyEvent.VK_S, Action.MOVE_DOWN,
                    KeyEvent.VK_A, Action.MOVE_LEFT,
                    KeyEvent.VK_D, Action.MOVE_RIGHT
            );

    /**
     * Retrieves the mapped keyboard inputs to <code>Action</code>s for the given player.
     *
     * @see PlayerNumber
     */
    public static PlayerControls controlsForPlayer(PlayerNumber playerNumber) {
        return switch (playerNumber) {
            case PLAYER_ONE -> new PlayerControls(PLAYER_ONE_CONTROLS);
            case PLAYER_TWO -> new PlayerControls(PLAYER_TWO_CONTROLS);
        };
    }

    /**
     * The <code>Action</code> mapped to the given keyCode, if any.
     *
     * @see Action
     */
    public Optional<Action> mappedAction(int keyCode) {
        return Optional.ofNullable(controls.get(keyCode));
    }
}
