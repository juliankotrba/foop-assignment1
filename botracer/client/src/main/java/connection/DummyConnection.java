package connection;

import debug.Log;
import dto.*;
import dto.algorithms.DfsStrategy;
import dto.algorithms.MazeSolverStrategy;
import dto.messages.OnMessageReceivedListener;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.GameStartMessage;
import dto.messages.Message;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.PlayersChangedMessage;
import exception.connection.ConnectionException;
import gui.MainController;
import debug.MazeLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy connection for testing purpose
 *
 * @author Julian Kotrba
 */
// TODO: REFACTOR!
//public class DummyConnection implements Connection {

    /*private OnMessageReceivedListener<Message> onMessageReceivedListener;
    private boolean isConnected = false;
    private int playerCount = 0;
    private Grid<Tile> grid;

    private MazeSolverStrategy mazeSolverStrategy;
    private Player player;

    public DummyConnection() {
        this.grid = MazeLoader.shared.load(MainController.class.getResource("../maze.txt"));
        this.mazeSolverStrategy = new DfsStrategy(this.grid);
        this.player = new Player(0, new Position(1, 1));
    }

    @Override
    public void connect() {
        Log.debug("connect()");

        this.isConnected = true;

        GameData gameData = this.createGameData();
        GameDataMessage gameDataMessage = new GameDataMessage(grid);
        this.onMessageReceivedListener.onMessageReceived(gameDataMessage);

        startStrategySimulation();
    }

    private void startStrategySimulation() {

        new Thread(() -> {

            while (true) {
                Position newPos = this.mazeSolverStrategy.nextPosition(this.player.getPosition());
                this.player.setPosition(newPos);

                List<Player> playerList = new ArrayList<Player>();
                playerList.add(player);
                PlayersChangedMessage playersChangedMessage = new PlayersChangedMessage(playerList);
                onMessageReceivedListener.onMessageReceived(playersChangedMessage);

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

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
        Log.debug(String.format("send(%s)", message.getClass().getSimpleName()));

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

        return new GameData(grid, player);
    }*/

//}
