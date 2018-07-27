package marks;

import algorithms.special.StayInThisAreaSpecial;
import game.Player;

/**
 * bot will stay in this area after stepping onto the mark
 */
public class StayInThisAreaMark implements Mark {
	@Override
	public void enter(Player player) {
		player.getMemory().setSpecial(new StayInThisAreaSpecial(player.getWidth(), player.getHeight()));
	}
}
