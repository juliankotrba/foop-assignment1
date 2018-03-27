package algorithms;

import game.GameBoard;
import game.Memory;

public class RandomAlgorithm implements Algorithm {

    @Override
    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        return Step.DOWN;
    }
}
