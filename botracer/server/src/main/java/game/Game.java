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

        players= new ArrayList<>();
        players.add(new Player(0,"player1",1,1,new RandomAlgorithm()));
        while(gameRunning){
            drawBoard();
            System.out.print(players);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (gameBoard) {
                for (Player player : players) {
                    player.nextStep(gameBoard);
                }
            }
            /*try{
            for(ObjectOutputStream objectOutputStream:writers){
                objectOutputStream.writeObject(new GameDataMessage(null));
            }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException io){

            }*/

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
