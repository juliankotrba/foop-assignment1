package dto.algorithms;

import dto.Grid;
import dto.Position;
import dto.Tile;
import dto.TileType;

import java.io.Serializable;
import java.util.*;

/**
 * DFS strategy
 *
 * @author Julian Kotrba
 */
// TODO: Create proper DFS
// TODO: REFACTOR!
public class DfsStrategy implements MazeSolverStrategy, Serializable {

    private enum Direction {
        N {
            @Override
            Direction revert() {
                return S;
            }

            @Override
            Direction left() {
                return W;
            }

            @Override
            Direction right() {
                return E;
            }
        }, E {
            @Override
            Direction revert() {
                return W;
            }

            @Override
            Direction left() {
                return N;
            }

            @Override
            Direction right() {
                return S;
            }
        }, S {
            @Override
            Direction revert() {
                return N;
            }

            @Override
            Direction left() {
                return E;
            }

            @Override
            Direction right() {
                return W;
            }
        }, W {
            @Override
            Direction revert() {
                return E;
            }

            @Override
            Direction left() {
                return S;
            }

            @Override
            Direction right() {
                return N;
            }
        };

        abstract Direction revert();

        abstract Direction left();

        abstract Direction right();
    }

    private Set<Position> visited;
    private Direction currDirection;
    private Queue<Direction> rememberedDirections;

    private boolean isTurn;
    private Direction turnDirection;

    private static final long serialVersionUID = 1L;

    public DfsStrategy() {
        this.visited = new HashSet<>();
        this.currDirection = Direction.N;
        this.isTurn = false;
        this.rememberedDirections = Collections.asLifoQueue(new ArrayDeque<>());
    }

    @Override
    public Position nextPosition(Position position, Grid<Tile> grid) {
        Direction nextDirection;

        if (isTurn) {
            // Turn Left/Right mark activated
            nextDirection = this.turnDirection;
            this.isTurn = false;

        } else {

            // TODO: Scan environment for exit

            List<Direction> possibleDirections = this.getPossibleDirections(grid, position);
            if (possibleDirections.isEmpty()) {

                // Empty means we need to go back
                nextDirection = this.rememberedDirections.remove().revert();

            } else {

                // At least one unvisited tile. Choose the first direction
                nextDirection = possibleDirections.get(0);
                this.rememberedDirections.add(possibleDirections.get(0));

            }
        }

        Position nextPosition = this.getNextPositionFromDirection(nextDirection, position);
        this.visited.add(nextPosition);

        return nextPosition;
    }

    @Override
    public void nextLeft() {

        this.turnDirection = this.currDirection.left();
        this.isTurn = true;
    }

    @Override
    public void nextRight() {

        this.turnDirection = this.currDirection.right();
        this.isTurn = true;
    }

    @Override
    public void reset() {
        this.visited = new HashSet<>();
    }

    private Position getNextPositionFromDirection(Direction direction, Position position) {
        Position nextPosition;
        switch (direction) {

            case N:
                nextPosition = getNorth(position);
                break;
            case E:
                nextPosition = getEast(position);
                break;
            case S:
                nextPosition = getSouth(position);
                break;
            case W:
                nextPosition = getWest(position);
                break;
            default:
                nextPosition = position;
        }

        return nextPosition;
    }

    private List<Direction> getPossibleDirections(Grid<Tile> grid, Position position) {
        List<Direction> possibleDirections = new ArrayList<>();

        if (isPositionFreeAndNotVisited(grid, getNorth(position))) {
            possibleDirections.add(Direction.N);
        }

        if (isPositionFreeAndNotVisited(grid, getEast(position))) {
            possibleDirections.add(Direction.E);
        }

        if (isPositionFreeAndNotVisited(grid, getSouth(position))) {
            possibleDirections.add(Direction.S);
        }

        if (isPositionFreeAndNotVisited(grid, getWest(position))) {
            possibleDirections.add(Direction.W);
        }

        return possibleDirections;
    }

    private boolean isPositionFree(Grid<Tile> grid, Position position) {
        return grid.get(position).getType() == TileType.DEFAULT;
    }

    private boolean isPositionFreeAndNotVisited(Grid<Tile> grid, Position position) {
        return this.isPositionFree(grid, position) && !this.visited.contains(position);
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
