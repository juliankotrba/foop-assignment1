package connection;

import dto.*;
import dto.messages.GameDataMessage;
import dto.messages.GameStartMessage;
import dto.messages.Message;
import dto.messages.PlayerReadyMessage;
import exception.connection.ConnectionException;
import gui.MainController;
import gui.debug.MazeLoader;

/**
 * Dummy connection for testing purpose
 *
 * @author Julian Kotrba
 */
public class DummyConnection implements Connection {

    private OnMessageReceivedListener<Message> onMessageReceivedListener;
    private boolean isConnected = false;
    private int playerCount = 0;

    @Override
    public void connect() {
        this.isConnected = true;

        GameData gameData = this.createGameData();
        this.onMessageReceivedListener.onMessageReceived(new GameDataMessage(gameData));
    }

    @Override
    public void connectAndListen(OnMessageReceivedListener<Message> onMessageReceivedListener) {
        this.connect();
        this.onMessageReceivedListener = onMessageReceivedListener;
    }

    @Override
    public void disconnect() {
        this.isConnected = false;
    }

    @Override
    public void send(Message message) throws ConnectionException {
        if (!isConnected) throw new ConnectionException("not connected");

        if (message instanceof PlayerReadyMessage) {
            this.onMessageReceivedListener.onMessageReceived(new GameStartMessage());
        }
    }

    @Override
    public void setMessageListener(OnMessageReceivedListener<Message> onMessageReceivedListener) {
        this.onMessageReceivedListener = onMessageReceivedListener;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    private GameData createGameData() {
        Position position = new Position(1, 1 + playerCount);
        Player player = new Player(0, position);

        Grid<Tile> grid = MazeLoader.shared.load(MainController.class.getResource("../maze.txt"));
        return new GameData(grid, player);
    }

}
