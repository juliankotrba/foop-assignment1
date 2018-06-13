package communication;

import dto.messages.Message;
import game.Game;
import game.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ConnectionHandler extends Thread {

    /**
     * The set of all the print writers for all the clients.  This
     * set is kept so we can easily broadcast messages.
     */
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();

    private Player player;
    private Socket socket;
    private ObjectOutputStream out;
    private Game game;


    public ConnectionHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }


    public static HashSet<ObjectOutputStream> getWriters() {
        return writers;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public Game getGame() {
        return game;
    }

    public void run() {
        try {
            // Create character streams for the socket.
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            writers.add(out);

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {

                Message message = null;
                try {
                    message = (Message) in.readObject();
                    message.accept(new MessageHandler(this, game));

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (message == null) {
                    return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // This client is going down!  Remove its name and its print
            // writer from the sets, and close its socket.
            if (out != null) {
                writers.remove(out);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
