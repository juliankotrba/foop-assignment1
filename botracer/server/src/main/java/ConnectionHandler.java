import dto.messages.Message;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.NewPlayerMessage;
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

    private String name;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Game game;


    public ConnectionHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    public void run() {
        try {
            // Create character streams for the socket.
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            writers.add(out);

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {

                Message message = null;
                try {
                    message = (Message) in.readObject();
                    message.accept(new MessageHandler(this, writers, out, game));

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (message == null) {
                    return;
                }
                for (ObjectOutputStream writer : writers) {
                    writer.writeObject(new GameStartMessage());
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
