package marks;

import algorithms.special.TurnRightSpecial;
import game.Player;

public class TurnRightMark implements Mark {
    @Override
    public void enter(Player player) {
        player.getMemory().setSpecial(new TurnRightSpecial());
    }
}
