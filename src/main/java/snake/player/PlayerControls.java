package snake.player;

import java.awt.event.KeyEvent;
import java.util.Map;

public record PlayerControls(Map<Integer, Action> controls) {
    private static final Map<Integer, Action> PLAYER_ONE_CONTROLS =
            Map.of(KeyEvent.VK_UP, Action.MOVE_UP, KeyEvent.VK_DOWN, Action.MOVE_DOWN, KeyEvent.VK_LEFT, Action.MOVE_LEFT, KeyEvent.VK_RIGHT, Action.MOVE_RIGHT);

    private static final Map<Integer, Action> PLAYER_TWO_CONTROLS =
            Map.of(KeyEvent.VK_W, Action.MOVE_UP, KeyEvent.VK_S, Action.MOVE_DOWN, KeyEvent.VK_A, Action.MOVE_LEFT, KeyEvent.VK_D, Action.MOVE_RIGHT);

    public static PlayerControls controlsForPlayer(Player playerNumber) {
        return switch (playerNumber) {
            case PLAYER_ONE -> new PlayerControls(PLAYER_ONE_CONTROLS);
            case PLAYER_TWO -> new PlayerControls(PLAYER_TWO_CONTROLS);
        };
    }

    public boolean contains(int keyCode) {
        return controls.containsKey(keyCode);
    }

    public Action get(int keyCode) {
        return controls.getOrDefault(keyCode, null);
    }
}
