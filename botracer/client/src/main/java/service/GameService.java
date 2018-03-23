package service;

import connection.OnMessageReceivedListener;
import dto.messages.GameStartMessage;
import dto.messages.MapMessage;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void startGame(OnMessageReceivedListener<MapMessage> listener);

    void playerReady(OnMessageReceivedListener<GameStartMessage> gameStartListener);

    void stopGame();
}
