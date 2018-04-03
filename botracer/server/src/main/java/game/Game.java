package game;

import algorithms.RandomAlgorithm;
import dto.Position;
import dto.messages.s2c.PlayersChangedMessage;
import marks.Mark;
import util.DTOUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game implements Runnable{

    private final GameBoard gameBoard;
    private final int movementDelayMillis = 500;

    private Boolean gameRunning = true;
    private List<Player> players;
    private MazeLoader mazeLoader;
    private Set<ObjectOutputStream> writers;

    public Game(String path) throws IOException, URISyntaxException {
        mazeLoader = new MazeLoader();
        gameBoard = mazeLoader.createGameBoard(path);
        players = new ArrayList<>();
    }

    public void setWriters(Set<ObjectOutputStream> writers){
        this.writers = writers;
    }

    /**
     *
     */
    public void runGame() {
        DTOUtil dtoUtil = new DTOUtil();
        while(gameRunning){
            //drawBoard();
            //System.out.print(players);
            try {
                Thread.sleep(movementDelayMillis);

                synchronized (gameBoard) {
                    for (Player player : players) {
                        player.nextStep(gameBoard);
                    }

                    for (ObjectOutputStream writer : writers) {
                        writer.writeObject(new PlayersChangedMessage(dtoUtil.convertPlayers(players)));
                    }
                }
            } catch (InterruptedException e) {
                this.gameRunning = false;
            } catch (IOException e) {
                // TODO
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void newMark(Mark mark, int height, int width){
        synchronized (gameBoard){
            gameBoard.newMark(mark, height, width);
        }
    }

    @Override
    public void run() {
        runGame();
    }

    public void stop() {
        this.gameRunning = false;
    }

    public Boolean getGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(Boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Adds a new Player with a generated name.
     *
     * @return the added player
     */
    public Player addPlayer() {
        return addPlayer("Bot" + (players.size() + 1));
    }

    /**
     * Adds a new Player with {name}.
     *
     * @param name of the player
     * @return the added player
     */
    public Player addPlayer(String name){
        Position position = mazeLoader.getNewStartingPosition(players, gameBoard);
        Player player = new Player(players.size() + 1, name, position.getHeight(), position.getWidth(), new RandomAlgorithm());
        this.players.add(player);
        return player;
    }

    public void drawBoard(){
        System.out.print("\033[H\033[2J");
        StringBuilder stringBuilder = new StringBuilder(gameBoard.toString());
        for (Player player : players){
            stringBuilder.setCharAt(player.getHeight() * 62 + player.getWidth(),'o');
        }
        System.out.print(stringBuilder.toString());
    }
}
