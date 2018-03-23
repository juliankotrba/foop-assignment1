package service;

import connection.Connection;
import connection.OnMessageReceivedListener;
import dto.messages.GameStartMessage;
import dto.messages.GameDataMessage;
import dto.messages.Message;
import dto.messages.PlayerReadyMessage;
import exception.ConnectionException;
import exception.MessageException;

/**
 * Implementation of the game service
 *
 * @author Julian Kotrba
 */
public class GameServiceImpl implements GameService, OnMessageReceivedListener<Message> {

    private Connection connection;

    private OnMessageReceivedListener<GameDataMessage> onGameMapReceivedCallback;
    private OnMessageReceivedListener<GameStartMessage> onGameStartReceivedCallback;

    public GameServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void connect(OnMessageReceivedListener<GameDataMessage> callback) throws ConnectionException {
        this.onGameMapReceivedCallback = callback;

        this.connection.connect();
        this.connection.setMessageListener(this);
    }

    @Override
    public void setPlayerReady(OnMessageReceivedListener<GameStartMessage> callback) throws ConnectionException, MessageException {
        this.onGameStartReceivedCallback = callback;
        this.connection.send(new PlayerReadyMessage());
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void onMessageReceived(Message message) {
        // TODO: Try to avoid instanceof (visitor patter ?) ..

        if (message instanceof GameDataMessage) {
            if (this.onGameMapReceivedCallback != null) {
                this.onGameMapReceivedCallback.onMessageReceived((GameDataMessage) message);
            }
        }

        if (message instanceof GameStartMessage) {
            if (this.onGameStartReceivedCallback != null) {
                this.onGameStartReceivedCallback.onMessageReceived((GameStartMessage) message);
            }
        }
    }
}
