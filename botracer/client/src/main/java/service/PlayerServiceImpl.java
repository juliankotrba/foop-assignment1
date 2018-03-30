package service;

import connection.Connection;
import dto.messages.OnMessageReceivedListener;
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


}
