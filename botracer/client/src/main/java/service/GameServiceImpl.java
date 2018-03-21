package service;

import connection.Connection;
import connection.OnMessageReceivedListener;
import dto.MapMessage;

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
    public void startGame(OnMessageReceivedListener<MapMessage> listener) {

    }

    @Override
    public void stopGame() {

    }
}
