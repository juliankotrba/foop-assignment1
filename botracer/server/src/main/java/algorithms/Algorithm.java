package algorithms;

import game.GameBoard;

/**
 * interface for all possible alorithms
 */
public abstract class Algorithm {

	/**
	 * Calculates the next step according to the implemnented algorithm
	 *
	 * @param memory    the bots memory
	 * @param gameBoard the current gameboard
	 * @param x         x value of the bot
	 * @param y         y value of the bot
	 * @return the next step
	 */
	public abstract Step nextStep(Memory memory, GameBoard gameBoard, int x, int y);

}
