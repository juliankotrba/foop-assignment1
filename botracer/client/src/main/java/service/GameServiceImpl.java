package service;

import connection.Connection;
import connection.OnMessageReceivedListener;
import dto.messages.GameStartMessage;
import dto.messages.GameDataMessage;
import dto.messages.Message;
import dto.messages.PlayerReadyMessage;
import exception.connection.ConnectionException;
import exception.connection.MessageException;
import exception.service.ServiceException;

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
    public void connect(OnMessageReceivedListener<GameDataMessage> callback) throws ServiceException {
        this.onGameMapReceivedCallback = callback;

        try {
            this.connection.connect();
            this.connection.setMessageListener(this);
        } catch (ConnectionException e) {
            throw new ServiceException("connect failed", e);
        }
    }

    @Override
    public void setPlayerReady(OnMessageReceivedListener<GameStartMessage> callback) throws ServiceException {
        this.onGameStartReceivedCallback = callback;

        try {
            this.connection.send(new PlayerReadyMessage());
        } catch (MessageException | ConnectionException e) {
            throw new ServiceException("setPlayerReady failed", e);
        }
    }

    @Override
    public void disconnect() {
        this.connection.disconnect();
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
