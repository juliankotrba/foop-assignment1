package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;

public class TurnRightSpecial extends Special {
    @Override
    public Step move(Memory memory, GameBoard gameBoard, int x, int y) {
        switch(memory.getLastStep()) {
            case RIGHT:
                if (gameBoard.getTile(y + 1, x).isWalkable()) {
                    return Step.DOWN;
                }
                break;
            case LEFT:
                if (gameBoard.getTile(y - 1, x).isWalkable()) {
                    return Step.UP;
                }
                break;
            case UP:
                if (gameBoard.getTile(y, x + 1).isWalkable()) {
                    return Step.RIGHT;
                }
                break;
            case DOWN:
                if (gameBoard.getTile(y, x - 1).isWalkable()) {
                    return Step.LEFT;
                }
                break;

        }
        return Step.NONE;
    }
}