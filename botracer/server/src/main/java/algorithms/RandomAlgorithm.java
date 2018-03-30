package algorithms;

import game.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAlgorithm extends Algorithm {

    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        List<Step> possiblesteps = new ArrayList<>();


        if(gameBoard.getTile(x,y-1).isWalkable()){
            possiblesteps.add(Step.UP);
        }
        if(gameBoard.getTile(x,y+1).isWalkable()){
            possiblesteps.add(Step.DOWN);
        }
        if(gameBoard.getTile(x-1,y).isWalkable()){
            possiblesteps.add(Step.LEFT);
        }
        if(gameBoard.getTile(x+1,y).isWalkable()){
            possiblesteps.add(Step.RIGHT);
        }
        if(possiblesteps.size()!=1&&memory.getLastStep()!=Step.NONE){
            possiblesteps.remove(memory.getLastStep().getOpposite());
        }

        return possiblesteps.get(ThreadLocalRandom.current().nextInt(0,  possiblesteps.size()));
    }
}
