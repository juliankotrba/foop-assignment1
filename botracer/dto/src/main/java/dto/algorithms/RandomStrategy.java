package dto.algorithms;

import dto.Grid;
import dto.Position;
import dto.Tile;
import dto.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <description>
 *
 * @author Julian Kotrba
 */
public class RandomStrategy implements MazeSolverStrategy {

    private Random random;

    public RandomStrategy() {
        this.random = new Random();
    }

    @Override
    public Position nextPosition(Position position, Grid<Tile> grid) {

        List<Position> positions = this.getPossibleDirections(grid, position);
        return positions.get(random.nextInt(positions.size() - 1 + 1));
    }

    @Override
    public void nextLeft() {
        // TODO
    }

    @Override
    public void nextRight() {
        // TODO
    }

    @Override
    public void reset() {
        // Nothing to do here
    }

    private List<Position> getPossibleDirections(Grid<Tile> grid, Position position) {
        List<Position> possibleDirections = new ArrayList<>();

        if (isPositionFree(grid, getNorth(position))) {
            possibleDirections.add(getNorth(position));
        }

        if (isPositionFree(grid, getEast(position))) {
            possibleDirections.add(getEast(position));
        }

        if (isPositionFree(grid, getSouth(position))) {
            possibleDirections.add(getSouth(position));
        }

        if (isPositionFree(grid, getWest(position))) {
            possibleDirections.add(getWest(position));
        }

        return possibleDirections;
    }

    private boolean isPositionFree(Grid<Tile> grid, Position position) {
        return grid.get(position).getType() == TileType.DEFAULT;
    }


    private Position getNorth(Position position) {
        return new Position(position.getX(), position.getY() - 1);
    }

    private Position getEast(Position position) {
        return new Position(position.getX() + 1, position.getY());
    }

    private Position getSouth(Position position) {
        return new Position(position.getX(), position.getY() + 1);
    }

    private Position getWest(Position position) {
        return new Position(position.getX() - 1, position.getY());
    }
}
