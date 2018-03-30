package dto.messages.c2s;

import dto.messages.Message;
import dto.messages.s2c.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * This message sends the player name of a new player to the server.
 */
public class PlayerNameMessage implements Message<String>, Serializable {

    private String playerName;

    public PlayerNameMessage() {
        this.playerName = "";
    }

    public PlayerNameMessage(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void accept(OnMessageReceivedListener onMessageReceivedListener) {
        onMessageReceivedListener.onMessageReceived(this);
    }

    @Override
    public Optional<String> getPayload() {
        return Optional.of(this.playerName);
    }
}
