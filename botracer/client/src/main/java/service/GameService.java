package service;

import dto.messages.OnMessageReceivedListener;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.GameDataMessage;
import exception.service.ServiceException;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void connect() throws ServiceException;
    void setPlayerReady() throws ServiceException;
    void disconnect();

    /**
     * Sends a PlayerNameMessage to the server.
     *
     * @param playerName name of the player which should be sent to the server
     * @throws ServiceException if a ConnectionException or MessageException occurs
     */
    void setPlayerName(String playerName) throws ServiceException;
}
