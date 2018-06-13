package marks;

import algorithms.special.MoveAwaySpecial;
import game.Player;

/**
 * bot will move away from this area after stepping onto thsi mark
 */
public class MoveAwayMark implements Mark {
    @Override
    public void enter(Player player) {
        player.getMemory().setSpecial(new MoveAwaySpecial(player.getWidth(),player.getHeight()));
    }
}
