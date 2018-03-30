package service;

import connection.Connection;

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
