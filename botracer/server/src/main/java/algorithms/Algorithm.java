package algorithms;

import game.GameBoard;
import game.Memory;

public abstract class Algorithm {

    private Step lastStep;

    public abstract Step nextStep(Memory memory, GameBoard gameBoard, int x, int y);


}
