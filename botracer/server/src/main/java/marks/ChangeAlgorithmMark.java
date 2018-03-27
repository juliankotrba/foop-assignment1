package marks;

import algorithms.Algorithm;
import game.Player;

public class ChangeAlgorithmMark implements Mark {

    Algorithm algorithm;

    @Override
    public void enter(Player player) {
        player.setAlgorithm(algorithm);
    }
}
