package algorithms;

import game.GameBoard;

public abstract class Algorithm {



    public abstract Step nextStep(Memory memory, GameBoard gameBoard, int x, int y);


}
