package dto.algorithms;

import dto.Grid;
import dto.Position;
import dto.Tile;

/**
 * Interface for calculating the next position of a bot
 *
 * @author Julian Kotrba
 */
public interface MazeSolverStrategy {

    Position nextPosition(Position position, Grid<Tile> grid);

    void nextLeft();

    void nextRight();
    void reset();
}
