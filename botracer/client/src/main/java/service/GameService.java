package service;

import exception.service.ServiceException;
import gui.UIManager;

/**
 * Game service
 *
 * @author  Julian Kotrba
 */
public interface GameService {

    /**
     * Connects to the game server
     *
     * @param uiManager ui callback
     * @throws ServiceException if connecting to server fails
     */
    void connect(UIManager uiManager) throws ServiceException;

    /**
     * Sets player ready
     * @throws ServiceException if setting player ready fails
     */
    void setPlayerReady() throws ServiceException;

    /**
     * Disconnect from game server
     */
    void disconnect();

    /**
     * Sends a PlayerNameMessage to the server.
     *
     * @param playerName name of the player which should be sent to the server
     * @throws ServiceException if a ConnectionException or MessageException occurs
     */
    void setPlayerName(String playerName) throws ServiceException;
}
