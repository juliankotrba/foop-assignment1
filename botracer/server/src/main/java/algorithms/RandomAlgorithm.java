package algorithms;

import game.GameBoard;
import game.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAlgorithm implements Algorithm {

    @Override
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
        return possiblesteps.get(ThreadLocalRandom.current().nextInt(0,  possiblesteps.size()-1));
    }
}
