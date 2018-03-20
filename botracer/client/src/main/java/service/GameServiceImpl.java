package service;

import connection.Connection;

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

    @Override
    public void startGame() {

    }

    @Override
    public void stopGame() {

    }
}
