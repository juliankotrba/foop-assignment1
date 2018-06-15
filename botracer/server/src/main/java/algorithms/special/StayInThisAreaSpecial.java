package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;


/**
 * The bot tries to stay as clase as possible, until a new special is assigned or the memory is cleared
 */
public class StayInThisAreaSpecial extends Special {

	private int xStart;
	private int yStart;
	private int reachedStart;

	public StayInThisAreaSpecial(int xStart, int yStart) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.reachedStart = 0;
	}

	@Override
	public Step move(Memory memory, GameBoard gameBoard, int x, int y) {
		if (x == xStart && y == yStart) {
			reachedStart++;
		}

		Step step = Step.NONE;
		int minManhattenDistance = Integer.MAX_VALUE;


		if (gameBoard.getTile(y - 1, x).isWalkable() && memory.getLastStep() != Step.DOWN) {
			int distance = calculateManhattenDistance(x, y - 1, xStart, yStart);
			if (minManhattenDistance > distance) {
				step = Step.UP;
				minManhattenDistance = distance;
			}
		}
		if (gameBoard.getTile(y + 1, x).isWalkable() && memory.getLastStep() != Step.UP) {
			int distance = calculateManhattenDistance(x, y + 1, xStart, yStart);
			if (minManhattenDistance > distance) {
				step = Step.DOWN;
				minManhattenDistance = distance;
			}
		}
		if (gameBoard.getTile(y, x - 1).isWalkable() && memory.getLastStep() != Step.RIGHT) {
			int distance = calculateManhattenDistance(x - 1, y, xStart, yStart);
			if (minManhattenDistance > distance) {
				step = Step.LEFT;
				minManhattenDistance = distance;
			}
		}
		if (gameBoard.getTile(y, x + 1).isWalkable() && memory.getLastStep() != Step.LEFT) {
			int distance = calculateManhattenDistance(x + 1, y, xStart, yStart);
			if (minManhattenDistance > distance) {
				step = Step.RIGHT;

			}
		}

		if (step == Step.NONE) {
			return memory.getLastStep().getOpposite();
		}
		return step;
	}

	@Override
	public Boolean finished() {
		if (reachedStart == 3) {
			return true;
		}
		return false;
	}

	/**
	 * Calculates the ManhattenDistance between two tiles
	 *
	 * @param x1 x of the first tile
	 * @param y1 y of the first tile
	 * @param x2 x of the second tile
	 * @param y2 y of the second tile
	 * @return the ManhattenDistance
	 */
	private int calculateManhattenDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
