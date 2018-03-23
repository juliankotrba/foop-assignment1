package service;

import connection.OnMessageReceivedListener;
import dto.messages.GameStartMessage;
import dto.messages.GameDataMessage;
import exception.ConnectionException;
import exception.MessageException;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void connect(OnMessageReceivedListener<GameDataMessage> listener) throws ConnectionException;

    void setPlayerReady(OnMessageReceivedListener<GameStartMessage> callback) throws ConnectionException, MessageException;

    void disconnect();
}
