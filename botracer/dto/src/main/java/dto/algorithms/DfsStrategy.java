package dto.algorithms;

import dto.Grid;
import dto.Position;
import dto.Tile;
import dto.TileType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DFS strategy
 *
 * @author Julian Kotrba
 */
// TODO: REFACTOR!
public class DfsStrategy implements MazeSolverStrategy, Serializable {

    private enum Direction {
        N, E, S, W
    }

    private Set<Position> visited;
    private Direction currDirection;

    private boolean isTurn;
    private Direction turnDirection;

    private static final long serialVersionUID = 1L;

    public DfsStrategy() {
        this.visited = new HashSet<>();
        this.currDirection = Direction.N;
        this.isTurn = false;
    }

    @Override
    public Position nextPosition(Position position, Grid<Tile> grid) {
        Position nextPosition;

        if (isTurn) {

            switch (this.turnDirection) {

                case N:
                    nextPosition = this.getNorth(position);
                    break;
                case E:
                    nextPosition = this.getEast(position);
                    break;
                case S:
                    nextPosition = this.getSouth(position);
                    break;
                case W:
                    nextPosition = this.getWest(position);
                    break;
                default:
                    nextPosition = position;
            }

        } else {

            if (isPositionFreeAndNotVisited(grid, getNorth(position))) {
                nextPosition = getNorth(position);
                this.currDirection = Direction.N;
            } else if (isPositionFreeAndNotVisited(grid, getEast(position))) {
                nextPosition = getEast(position);
                this.currDirection = Direction.E;
            } else if (isPositionFreeAndNotVisited(grid, getSouth(position))) {
                nextPosition = getSouth(position);
                this.currDirection = Direction.S;
            } else if (isPositionFreeAndNotVisited(grid, getWest(position))) {
                nextPosition = getWest(position);
                this.currDirection = Direction.W;
            } else {
                nextPosition = position;
            }
        }

        this.visited.add(nextPosition);

        return nextPosition;
    }

    @Override
    public void nextLeft() {
        switch (this.currDirection) {

            case N:
                this.isTurn = true;
                this.turnDirection = Direction.W;
                break;
            case E:
                this.isTurn = true;
                this.turnDirection = Direction.N;
                break;
            case S:
                this.isTurn = true;
                this.turnDirection = Direction.E;
                break;
            case W:
                this.isTurn = true;
                this.turnDirection = Direction.S;
                break;
        }
    }

    @Override
    public void nextRight() {
        switch (this.currDirection) {

            case N:
                this.isTurn = true;
                this.turnDirection = Direction.E;
                break;
            case E:
                this.isTurn = true;
                this.turnDirection = Direction.S;
                break;
            case S:
                this.isTurn = true;
                this.turnDirection = Direction.W;
                break;
            case W:
                this.isTurn = true;
                this.turnDirection = Direction.N;
                break;
        }
    }

    @Override
    public void reset() {
        this.visited = new HashSet<>();
    }


    private boolean isPositionFreeAndNotVisited(Grid<Tile> grid, Position position) {
        return grid.get(position).getType() != TileType.WALL && !this.visited.contains(position);
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
