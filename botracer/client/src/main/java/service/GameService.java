package service;

import connection.OnMessageReceivedListener;
import dto.messages.GameStartMessage;
import dto.messages.GameDataMessage;
import exception.service.ServiceException;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void connect(OnMessageReceivedListener<GameDataMessage> listener) throws ServiceException;

    void setPlayerReady(OnMessageReceivedListener<GameStartMessage> callback) throws ServiceException;
    void disconnect();
}
