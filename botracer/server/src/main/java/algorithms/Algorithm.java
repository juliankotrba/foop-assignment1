package algorithms;

import game.GameBoard;
import game.Memory;

public interface Algorithm {

    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y);

}
