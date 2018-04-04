package dto.messages;

import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.*;

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
    void onMessageReceived(GameDataMessage message);
    void onMessageReceived(GameStartMessage message);
    void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message);
    void onMessageReceived(NewPlayerMessage message);
    void onMessageReceived(PlayersChangedMessage message);
    void onMessageReceived(PlayerReadyServerMessage message);
    void onMessageReceived(RemoveMarksMessage message);
}
