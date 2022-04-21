package snake.player;

public record PlayerConfig(Player playerNumber, PlayerControls controls) {
    private static final PlayerConfig PLAYER_ONE_CONFIG = new PlayerConfig(Player.PLAYER_ONE, PlayerControls.controlsForPlayer(Player.PLAYER_ONE));

    private static final PlayerConfig PLAYER_TWO_CONFIG =
            new PlayerConfig(Player.PLAYER_TWO, PlayerControls.controlsForPlayer(Player.PLAYER_TWO));

    public static PlayerConfig configFor(Player playerNumber) {
        return switch (playerNumber) {
            case PLAYER_ONE -> PLAYER_ONE_CONFIG;
            case PLAYER_TWO -> PLAYER_TWO_CONFIG;
        };
    }
}
