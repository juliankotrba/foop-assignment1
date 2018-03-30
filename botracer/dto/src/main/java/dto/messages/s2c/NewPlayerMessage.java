package dto.messages.s2c;

import dto.Player;
import dto.messages.Message;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * This message indicates, that a new Player has connected to the server.
 *
 * @author Julian Kotrba
 */
public class NewPlayerMessage implements Message<List<Player>>, Serializable {

    private List<Player> players;

    public NewPlayerMessage(List<Player> players) {
        this.players = players;
    }

    @Override
    public void accept(OnMessageReceivedListener onMessageReceivedListener) {
        onMessageReceivedListener.onMessageReceived(this);
    }

    @Override
    public Optional<List<Player>> getPayload() {
        return Optional.of(this.players);
    }
}
