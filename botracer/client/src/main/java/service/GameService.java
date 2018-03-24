package service;

import connection.OnMessageReceivedListener;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.GameDataMessage;
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
