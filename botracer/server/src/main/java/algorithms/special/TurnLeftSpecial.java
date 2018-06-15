package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;

/**
 * If it possible to turn left on the certain tile, the bot will do it, otherwise the algorithm will calculaate the next step
 */
public class TurnLeftSpecial extends Special {
	@Override
	public Step move(Memory memory, GameBoard gameBoard, int x, int y) {
		switch (memory.getLastStep()) {
			case RIGHT:
				if (gameBoard.getTile(y - 1, x).isWalkable()) {
					return Step.UP;
				}
				break;
			case LEFT:
				if (gameBoard.getTile(y + 1, x).isWalkable()) {
					return Step.DOWN;
				}
				break;
			case UP:
				if (gameBoard.getTile(y, x - 1).isWalkable()) {
					return Step.LEFT;
				}
				break;
			case DOWN:
				if (gameBoard.getTile(y, x + 1).isWalkable()) {
					return Step.RIGHT;
				}
				break;
		}
		return Step.NONE;
	}

	@Override
	public Boolean finished() {
		return true;
	}
}
