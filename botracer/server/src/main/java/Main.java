import game.Game;
import game.Player;

import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Maze Server is running");

        Game game = new Game("../maze.txt");
        for (int i = 0; i < 4; i++) {
            game.addPlayer();
        }

        for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + ", height: " + player.getHeight() + ", width: " + player.getWidth());
        }

        ServerSocket listener = new ServerSocket(9001);
        try {
            while (true) {
                new ConnectionHandler(listener.accept(), game).start();
            }
        } finally {
            listener.close();
        }
    }
}
