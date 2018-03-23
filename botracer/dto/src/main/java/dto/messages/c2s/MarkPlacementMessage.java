package dto.messages.c2s;

import dto.Mark;
import dto.messages.Message;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which indicates a mark placement
 *
 * @author Julian Kotrba
 */
public class MarkPlacementMessage implements Message<Mark>, Serializable {

    private Mark mark;

    public MarkPlacementMessage(Mark mark) {
        this.mark = mark;
    }

    @Override
    public Optional<Mark> getPayload() {
        return Optional.of(this.mark);
    }
}
