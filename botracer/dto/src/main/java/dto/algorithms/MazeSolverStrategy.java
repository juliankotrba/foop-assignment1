package dto.algorithms;

import dto.Position;

/**
 * Interface for calculating the next position of a bot
 *
 * @author Julian Kotrba
 */
public interface MazeSolverStrategy {

    Position nextPosition(Position position);
    void nextLeft();
    void nextRight();
    void reset();
}
