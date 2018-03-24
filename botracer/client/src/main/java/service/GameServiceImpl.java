package service;

import connection.Connection;
import connection.OnMessageReceivedListener;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.GameDataMessage;
import dto.messages.Message;
import dto.messages.c2s.PlayerReadyMessage;
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
        this.connection.setMessageListener(this);

        try {
            this.connection.connect();

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
