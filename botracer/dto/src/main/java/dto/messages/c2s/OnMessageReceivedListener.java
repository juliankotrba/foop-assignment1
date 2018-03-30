package dto.messages.c2s;

/**
 * Callback for received message
 *
 * @author  Julian Kotrba
 */
public interface OnMessageReceivedListener {
    void onMessageReceived(ChangeStrategyMessage message);
    void onMessageReceived(MarkPlacementMessage message);
    void onMessageReceived(PlayerNameMessage message);
    void onMessageReceived(PlayerReadyMessage message);
    void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message);
}
