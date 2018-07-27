package marks;


import game.Player;

/**
 * interface for possible marks on the gameboard
 */
public interface Mark {
	/**
	 * Performs the action according to the certain mark
	 *
	 * @param player the bot who stepped onto the mark
	 */
	void enter(Player player);
}
