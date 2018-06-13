package marks;

import algorithms.Algorithm;
import game.Player;

/**
 * changes algorithm of the bot when stepping onto the mark
 */
public class ChangeAlgorithmMark implements Mark {

    private Algorithm algorithm;

    public ChangeAlgorithmMark(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void enter(Player player) {
        player.setAlgorithm(algorithm);
    }
}
