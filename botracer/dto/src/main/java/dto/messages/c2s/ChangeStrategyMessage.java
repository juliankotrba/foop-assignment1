package dto.messages.c2s;

import dto.algorithms.MazeSolverStrategy;
import dto.messages.Message;
import dto.messages.s2c.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which can be use to change the maze solving algorithm
 *
 * @author Julian Kotrba
 */
public class ChangeStrategyMessage implements Message<MazeSolverStrategy>, Serializable {

    private MazeSolverStrategy strategy;

    private static final long serialVersionUID = 1L;

    public ChangeStrategyMessage(MazeSolverStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void accept(OnMessageReceivedListener onMessageReceivedListener) {
        onMessageReceivedListener.onMessageReceived(this);
    }

    @Override
    public Optional<MazeSolverStrategy> getPayload() {
        return Optional.of(this.strategy);
    }
}
