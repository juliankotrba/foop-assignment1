package algorithms;

import game.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bot will walk into a random location
 */
public class RandomAlgorithm extends Algorithm {


    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        List<Step> possiblesteps = new ArrayList<>();

        if (memory.getSpecial() != null) {
            Step step = memory.getSpecial().move(memory, gameBoard, x, y);
            if (step != Step.NONE) {
                return step;
            }
            if(memory.getSpecial().finished()){
                memory.setSpecial(null);
            }
        }
        if(gameBoard.getTile(y-1,x).isWalkable()){
            possiblesteps.add(Step.UP);
        }
        if(gameBoard.getTile(y+1,x).isWalkable()){
            possiblesteps.add(Step.DOWN);
        }
        if(gameBoard.getTile(y,x-1).isWalkable()){
            possiblesteps.add(Step.LEFT);
        }
        if(gameBoard.getTile(y,x+1).isWalkable()){
            possiblesteps.add(Step.RIGHT);
        }
        if(possiblesteps.size()!=1&&memory.getLastStep()!=Step.NONE){
            possiblesteps.remove(memory.getLastStep().getOpposite());
        }

        return possiblesteps.get(ThreadLocalRandom.current().nextInt(0,  possiblesteps.size()));
    }
}
