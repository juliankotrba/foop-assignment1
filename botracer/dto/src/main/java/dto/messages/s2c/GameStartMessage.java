package dto.messages.s2c;

import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which indicates the a game start
 *
 * @author Julian Kotrba
 */
public class GameStartMessage implements Message<Void>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void accept(OnMessageReceivedListener onMessageReceivedListener) {
        onMessageReceivedListener.onMessageReceived(this);
    }

    @Override
    public Optional<Void> getPayload() {
        return Optional.empty();
    }
}
