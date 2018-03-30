package game;

import algorithms.LeftWallAlgorithm;
import algorithms.RandomAlgorithm;
import dto.messages.s2c.GameDataMessage;
import marks.Mark;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game implements Runnable{


    private Boolean gameRunning = true;
    private List<Player> players;
    private GameBoard gameBoard;
    private static HashSet<ObjectOutputStream> writers;

    public Game(String path) throws IOException {
        MazeLoader mazeLoader = new MazeLoader();
        gameBoard = mazeLoader.createGameBoard(path);
        //System.out.println(gameBoard);
    }

    public void runGame(){
        players= new ArrayList<>();
        players.add(new Player(1,1,new LeftWallAlgorithm()));
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
    public void newMark(Mark mark, int x, int y){
        synchronized (gameBoard){
            gameBoard.newMark(mark,x,y);
        }
        //drawBoard();
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
    public void addPlayer(Player player){
        this.players.add(player);

    }

    public void drawBoard(){
        System.out.print("\033[H\033[2J");
        StringBuilder stringBuilder = new StringBuilder(gameBoard.toString());
        for(Player player:players){
            stringBuilder.setCharAt(player.getY()*62+player.getX(),'o');
        }
        System.out.print(stringBuilder.toString());
    }
}
