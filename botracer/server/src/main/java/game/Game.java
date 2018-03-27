package game;

import dto.messages.s2c.GameDataMessage;
import marks.Mark;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.List;

public class Game implements Runnable{


    private Boolean gameRunning;
    private List<Player> players;
    private GameBoard gameBoard;
    private static HashSet<ObjectOutputStream> writers;

    public void runGame(){


        while(gameRunning){
            synchronized (gameBoard) {
                for (Player player : players) {
                    player.nextStep(gameBoard);
                }
            }
            try{
            for(ObjectOutputStream objectOutputStream:writers){
                objectOutputStream.writeObject(new GameDataMessage(null));
            }

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException io){

            }
        }

    }
    public void newMark(Mark mark, int x, int y){
        synchronized (gameBoard){
            gameBoard.newMark(mark,x,y);
        }
    }

    @Override
    public void run() {
        runGame();
    }
}
