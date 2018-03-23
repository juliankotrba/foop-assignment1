package service;

import connection.OnMessageReceivedListener;
import dto.messages.PlayersChangedMessage;
import exception.service.ServiceException;

/**
 * Player service class
 *
 * @author Julian Kotrba
 */
public interface PlayerService {
    void registerForPlayerUpdates(OnMessageReceivedListener<PlayersChangedMessage> playerUpdate) throws ServiceException;
}
