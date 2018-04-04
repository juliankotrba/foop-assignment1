package dto.messages;

import java.util.Optional;

/**
 * Message DTO
 *
 * @author Julian Kotrba
 */
public interface Message<T> {

    void accept(OnMessageReceivedListener onMessageReceivedListener);

    Optional<T> getPayload();
}

