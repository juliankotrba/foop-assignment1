package marks;

import algorithms.special.StayInThisAreaSpecial;
import game.Player;

public class StayInThisAreaMark implements Mark {
    @Override
    public void enter(Player player) {
        player.getMemory().setSpecial(new StayInThisAreaSpecial(player.getWidth(),player.getHeight()));
    }
}
