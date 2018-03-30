package dto.messages;

import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.NewPlayerMessage;
import dto.messages.s2c.PlayersChangedMessage;

import java.io.IOException;

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
}
