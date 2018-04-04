package service;

import exception.service.ServiceException;
import gui.UIManager;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {
    void connect(UIManager uiManager) throws ServiceException;
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
