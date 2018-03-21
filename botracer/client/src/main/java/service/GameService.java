package service;

import connection.OnMessageReceivedListener;
import dto.MapMessage;
import dto.Message;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void startGame(OnMessageReceivedListener<MapMessage> listener);
    void stopGame();
}
