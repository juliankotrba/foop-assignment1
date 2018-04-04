package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;

public abstract class Special {
    public abstract Step move(Memory memory,GameBoard gameBoard, int x, int y);

}
