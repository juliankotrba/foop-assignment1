package service;

import connection.OnMessageReceivedListener;
import dto.messages.s2c.NewPlayerMessage;
import dto.messages.s2c.PlayersChangedMessage;
import exception.service.ServiceException;

/**
 * Player service class
 *
 * @author Julian Kotrba
 */
public interface PlayerService {
    void registerForPlayerUpdates(OnMessageReceivedListener<PlayersChangedMessage> playerUpdate) throws ServiceException;

    void registerForNewPlayerUpdates(OnMessageReceivedListener<NewPlayerMessage> newPlayerUpdates) throws ServiceException;
}
