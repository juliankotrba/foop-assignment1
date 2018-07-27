package marks;

import algorithms.special.TurnLeftSpecial;
import game.Player;

/**
 * bot will turn left if possible (one time)
 */
public class TurnLeftMark implements Mark {
	@Override
	public void enter(Player player) {
		player.getMemory().setSpecial(new TurnLeftSpecial());
	}
}
