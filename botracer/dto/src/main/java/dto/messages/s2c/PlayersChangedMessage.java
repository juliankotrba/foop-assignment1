package dto.messages.s2c;

import dto.Player;
import dto.messages.Message;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Message for player updates
 *
 * @author Julian Kotrba
 */
public class PlayersChangedMessage implements Message<List<Player>>, Serializable {

    private List<Player> players;

    public PlayersChangedMessage(List<Player> players) {
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
