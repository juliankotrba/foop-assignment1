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

    private Grid<Tile> maze;
    private Random random;

    public RandomStrategy(Grid<Tile> maze) {
        this.maze = maze;
        this.random = new Random();
    }

    @Override
    public Position nextPosition(Position position) {

        List<Position> positions = this.getPossibleDirections(position);
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

    private List<Position> getPossibleDirections(Position position) {
        List<Position> possibleDirections = new ArrayList<>();

        if (isPositionFree(getNorth(position))) {
            possibleDirections.add(getNorth(position));
        }

        if (isPositionFree(getEast(position))) {
            possibleDirections.add(getEast(position));
        }

        if (isPositionFree(getSouth(position))) {
            possibleDirections.add(getSouth(position));
        }

        if (isPositionFree(getWest(position))) {
            possibleDirections.add(getWest(position));
        }

        return possibleDirections;
    }

    private boolean isPositionFree(Position position) {
        return this.maze.get(position).getType() == TileType.DEFAULT;
    }


    private Position getNorth(Position position) {
        return new Position(position.getWidth(), position.getHeight() - 1);
    }

    private Position getEast(Position position) {
        return new Position(position.getWidth() + 1, position.getHeight());
    }

    private Position getSouth(Position position) {
        return new Position(position.getWidth(), position.getHeight() + 1);
    }

    private Position getWest(Position position) {
        return new Position(position.getWidth() - 1, position.getHeight());
    }
}
