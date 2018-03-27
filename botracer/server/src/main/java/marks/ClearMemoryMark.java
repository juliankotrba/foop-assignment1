package marks;

import game.Player;

public class ClearMemoryMark implements Mark {

    @Override
    public void enter(Player player) {
        player.getMemory().reset();
    }

    @Override
    public String toString() {
        return "ClearMemoryMark{}";
    }
}
