package dto.algorithms;

import dto.Grid;
import dto.Position;
import dto.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * <description>
 *
 * @author Julian Kotrba
 */
public class DfsStrategy implements MazeSolverStrategy {

    private List<Position> visited;

    public DfsStrategy() {
        this.visited = new ArrayList<>();
    }

    @Override
    public Position nextPosition(Position position, Grid<Tile> grid) {
        return null;
    }

    @Override
    public void reset() {
        this.visited = new ArrayList<>();
    }
}
