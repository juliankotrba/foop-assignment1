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

    private Grid<Tile> maze;

    private Set<Position> visited;
    private Direction currDirection;
    private Queue<Direction> rememberedDirections;

    private boolean isTurn;
    private Direction turnDirection;

    private static final long serialVersionUID = 1L;

    public DfsStrategy(Grid<Tile> maze) {

        this.maze = maze;

        this.visited = new HashSet<>();
        this.currDirection = Direction.N;
        this.isTurn = false;
        this.rememberedDirections = Collections.asLifoQueue(new ArrayDeque<>());
    }

    @Override
    public Position nextPosition(Position position) {
        Direction nextDirection;

        if (isTurn) {
            // Turn Left/Right mark activated
            nextDirection = this.turnDirection;
            this.isTurn = false;

        } else {

            // TODO: Scan environment for exit

            List<Direction> possibleDirections = this.getPossibleDirections(position);
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

    private List<Direction> getPossibleDirections(Position position) {
        List<Direction> possibleDirections = new ArrayList<>();

        if (isPositionFreeAndNotVisited(getNorth(position))) {
            possibleDirections.add(Direction.N);
        }

        if (isPositionFreeAndNotVisited(getEast(position))) {
            possibleDirections.add(Direction.E);
        }

        if (isPositionFreeAndNotVisited(getSouth(position))) {
            possibleDirections.add(Direction.S);
        }

        if (isPositionFreeAndNotVisited(getWest(position))) {
            possibleDirections.add(Direction.W);
        }

        return possibleDirections;
    }

    private boolean isPositionFree(Position position) {
        return this.maze.get(position).getType() == TileType.DEFAULT;
    }

    private boolean isPositionFreeAndNotVisited(Position position) {
        return this.isPositionFree(position) && !this.visited.contains(position);
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
