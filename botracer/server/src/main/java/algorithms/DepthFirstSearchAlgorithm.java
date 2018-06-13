package algorithms;

import game.GameBoard;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A deph first search algorihm which will always find the exit of the maze
 */
public class DepthFirstSearchAlgorithm extends Algorithm {

    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        List<Step> possiblesteps = new ArrayList<>();

        if(memory.getSpecial()!=null) {
            Step step = memory.getSpecial().move(memory,gameBoard,x,y);
            if(step!=Step.NONE){
                return step;
            }
            if(memory.getSpecial().finished()){
                memory.setSpecial(null);
            }
        }
        if(gameBoard.getTile(y-1,x).isWalkable() && !memory.wasAlreadyVisited(gameBoard.getTile(y-1, x))){
            possiblesteps.add(Step.UP);
        }
        if(gameBoard.getTile(y+1,x).isWalkable() && !memory.wasAlreadyVisited(gameBoard.getTile(y+1, x))){
            possiblesteps.add(Step.DOWN);
        }
        if(gameBoard.getTile(y,x-1).isWalkable() && !memory.wasAlreadyVisited(gameBoard.getTile(y,x-1 ))){
            possiblesteps.add(Step.LEFT);
        }
        if(gameBoard.getTile(y,x+1).isWalkable() && !memory.wasAlreadyVisited(gameBoard.getTile(y,y+1 ))){
            possiblesteps.add(Step.RIGHT);
        }
        if(possiblesteps.size()==0){
            Step step = memory.getLastTileFromStack();
            if(step!=null){
                return step.getOpposite();
            }
            memory.addLastTileToStack(memory.getLastStep().getOpposite());
            return memory.getLastStep().getOpposite();
        }

        Step step = possiblesteps.get(ThreadLocalRandom.current().nextInt(0,  possiblesteps.size()));
        memory.addLastTileToStack(step);

        return step;
    }
}
