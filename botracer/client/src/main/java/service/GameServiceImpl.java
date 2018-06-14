package service;

import connection.Connection;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import exception.connection.ConnectionException;
import exception.connection.MessageException;
import exception.service.ServiceException;
import gui.UIManager;

/**
 * Implementation of the game service
 *
 * @author Julian Kotrba
 */
public class GameServiceImpl implements GameService {

    private Connection connection;

    public GameServiceImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(UIManager uiManager) throws ServiceException {

        try {
            this.connection.setUIManager(uiManager);
            this.connection.connect();

        } catch (ConnectionException e) {
            throw new ServiceException("connect failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerReady() throws ServiceException {

        try {
            this.connection.send(new PlayerReadyMessage());
        } catch (MessageException | ConnectionException e) {
            throw new ServiceException("setPlayerReady failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect() {
        this.connection.disconnect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerName(String playerName) throws ServiceException {
        try {
            connection.send(new PlayerNameMessage(playerName));
        } catch (MessageException | ConnectionException e) {
            throw new ServiceException("setPlayerName failed", e);
        }
    }
}
