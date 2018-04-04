package marks;

import algorithms.Algorithm;
import game.Player;

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
