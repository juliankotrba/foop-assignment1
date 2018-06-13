package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;

/**
 * Used for specialmoves which are not certain algorithms
 */
public abstract class Special {
    /**
     * Calculate the next step according to which special was implemented
     *
     * @param memory memory of the bot
     * @param gameBoard current gameboard
     * @param x current x position of the bot
     * @param y current y position of the bot
     * @return next step
     */
    public abstract Step move(Memory memory,GameBoard gameBoard, int x, int y);

    /**
     *
     * @return true if the special has been finished, false if not
     */
    public abstract Boolean finished();

}
