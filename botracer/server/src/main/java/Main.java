import game.Game;

import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");

        Game game = new Game("../maze.txt");
        Datagenerator datagenerator = new Datagenerator(game);

        Thread gameThread = new Thread(game);
        gameThread.start();
        Thread datageneratorThread = new Thread(datagenerator);
        datageneratorThread.start();
        while(true){

        }

    }
}
