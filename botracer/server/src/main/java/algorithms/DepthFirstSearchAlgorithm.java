package algorithms;

import game.GameBoard;

public class DepthFirstSearchAlgorithm extends Algorithm {
    @Override
    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        if(memory.getSpecial()!=null) {
            Step step = memory.getSpecial().move(memory,gameBoard,x,y);
            if(step!=Step.NONE){
                return step;
            }
            if(memory.getSpecial().finished()){
                memory.setSpecial(null);
            }
        }
        return null;
    }
}
