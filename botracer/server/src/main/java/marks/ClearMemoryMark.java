package marks;

import game.Player;

/**
 * clears the memory of the bot when stepped onto the mark
 */
public class ClearMemoryMark implements Mark {

    @Override
    public void enter(Player player) {
        player.getMemory().reset();
    }

}
