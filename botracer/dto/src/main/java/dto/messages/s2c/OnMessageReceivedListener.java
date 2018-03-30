package dto.messages.s2c;

/**
 * Callback for received message
 *
 * @author  Julian Kotrba
 */
public interface OnMessageReceivedListener {
    void onMessageReceived(MarkPlacementMessage message);
    void onMessageReceived(GameDataMessage message);
    void onMessageReceived(GameStartMessage message);
    void onMessageReceived(NewPlayerMessage message);
    void onMessageReceived(PlayersChangedMessage message);
}
