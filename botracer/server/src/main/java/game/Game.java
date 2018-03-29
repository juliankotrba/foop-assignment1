package game;

import algorithms.RandomAlgorithm;
import dto.Position;
import marks.Mark;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable{


    private Boolean gameRunning = true;
    private List<Player> players;
    private final GameBoard gameBoard;
    private MazeLoader mazeLoader;

    public Game(String path) throws IOException, URISyntaxException {
        mazeLoader = new MazeLoader();
        gameBoard = mazeLoader.createGameBoard(path);
        players = new ArrayList<>();
    }

    public void runGame(){
        while(gameRunning){
            synchronized (gameBoard) {
                for (Player player : players) {
                    player.nextStep(gameBoard);
                }
            }
            try{
                drawBoard();
                System.out.print(players);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
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
     */
    public void addPlayer() {
        addPlayer("Player" + (players.size() + 1));
    }

    /**
     * Adds a new Player with {name}.
     *
     * @param name of the player
     */
    public void addPlayer(String name){
        Position position = mazeLoader.getNewStartingPosition(players, gameBoard);
        Player player = new Player(players.size() + 1, name, position.getHeight(), position.getWidth(), new RandomAlgorithm());
        this.players.add(player);
    }

    public void drawBoard(){
        System.out.print("\033[H\033[2J");
        StringBuilder stringBuilder = new StringBuilder(gameBoard.toString());
        for (Player player : players){
            stringBuilder.setCharAt(player.getHeight() * 62 + player.getHeight(),'o');
        }
        System.out.print(stringBuilder.toString());
    }
}
