package dto.messages.c2s;

import dto.messages.Message;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which indicates the ready state of a player
 *
 * @author Julian Kotrba
 */
public class PlayerReadyMessage implements Message<Void>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Optional<Void> getPayload() {
        return Optional.empty();
    }
}
