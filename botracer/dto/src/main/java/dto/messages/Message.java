package dto.messages;

import java.util.Optional;

/**
 * Message DTO
 *
 * @author Julian Kotrba
 */
public interface Message<T> {
    Optional<T> getPayload();
}

