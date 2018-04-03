package marks;

import algorithms.special.MoveAwaySpecial;
import game.Player;

public class MoveAwayMark implements Mark {
    @Override
    public void enter(Player player) {
        player.getMemory().setSpecial(new MoveAwaySpecial());
    }
}
