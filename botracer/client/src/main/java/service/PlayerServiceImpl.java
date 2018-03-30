package service;

import connection.Connection;
import connection.OnMessageReceivedListener;
import dto.messages.s2c.NewPlayerMessage;
import dto.messages.s2c.PlayersChangedMessage;
import exception.service.ServiceException;

/**
 * Implementation of the player service
 *
 * @author Julian Kotrba
 */
public class PlayerServiceImpl implements PlayerService {

    private Connection connection;

    public PlayerServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registerForPlayerUpdates(OnMessageReceivedListener<PlayersChangedMessage> playerUpdate) throws ServiceException {

        this.connection.setMessageListener(message -> {
            if (message instanceof PlayersChangedMessage) {
                if (playerUpdate != null) {
                    playerUpdate.onMessageReceived((PlayersChangedMessage) message);
                }
            }
        });
    }

    @Override
    public void registerForNewPlayerUpdates(OnMessageReceivedListener<NewPlayerMessage> newPlayerUpdate) throws ServiceException {
        this.connection.setMessageListener(message -> {
            if (message instanceof NewPlayerMessage) {
                if (newPlayerUpdate != null) {
                    newPlayerUpdate.onMessageReceived((NewPlayerMessage) message);
                }
            }
        });
    }
}
