package algorithms;

import game.GameBoard;

public class LeftWallAlgorithm extends Algorithm {
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
        Step step = Step.NONE;
        Step stepWithMemory = Step.NONE;
        switch(memory.getLastStep()){
            case RIGHT:
                if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=Step.LEFT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x-1))){
                        stepWithMemory = Step.LEFT;
                    }
                }
                if(gameBoard.getTile(y+1,x).isWalkable()){
                    step = Step.DOWN;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y+1,x))){
                        stepWithMemory = Step.DOWN;
                    }
                }
                if(gameBoard.getTile(y,x+1).isWalkable()){
                    step = Step.RIGHT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x+1))){
                        stepWithMemory = Step.RIGHT;
                    }
                }
                if(gameBoard.getTile(y-1,x).isWalkable()){
                    step = Step.UP;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y-1,x))) {
                        stepWithMemory = Step.UP;
                    }
                }
                break;
            case LEFT:
            case NONE:
                if(gameBoard.getTile(y,x+1).isWalkable()){
                    step = Step.RIGHT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x+1))){
                        stepWithMemory = Step.RIGHT;
                    }
                }
                if(gameBoard.getTile(y-1,x).isWalkable()){
                    step = Step.UP;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y-1,x))) {
                        stepWithMemory = Step.UP;
                    }
                }
                if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=Step.LEFT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x-1))){
                        stepWithMemory = Step.LEFT;
                    }
                }
                if(gameBoard.getTile(y+1,x).isWalkable()) {
                    step = Step.DOWN;
                    if (!memory.wasAlreadyVisited(gameBoard.getTile(y + 1, x))) {
                        stepWithMemory = Step.DOWN;
                    }
                }
                break;
            case UP:
                if(gameBoard.getTile(y+1,x).isWalkable()) {
                    step = Step.DOWN;
                    if (!memory.wasAlreadyVisited(gameBoard.getTile(y + 1, x))) {
                        stepWithMemory = Step.DOWN;
                    }
                }
                if(gameBoard.getTile(y,x+1).isWalkable()){
                    step = Step.RIGHT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x+1))){
                        stepWithMemory = Step.RIGHT;
                    }
                }
                if(gameBoard.getTile(y-1,x).isWalkable()){
                    step = Step.UP;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y-1,x))) {
                        stepWithMemory = Step.UP;
                    }
                }
                if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=Step.LEFT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x-1))){
                        stepWithMemory = Step.LEFT;
                    }
                }
                break;
            case DOWN:
                if(gameBoard.getTile(y-1,x).isWalkable()){
                    step = Step.UP;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y-1,x))) {
                        stepWithMemory = Step.UP;
                    }
                }
                if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=Step.LEFT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x-1))){
                        stepWithMemory = Step.LEFT;
                    }
                }
                if(gameBoard.getTile(y+1,x).isWalkable()) {
                    step = Step.DOWN;
                    if (!memory.wasAlreadyVisited(gameBoard.getTile(y + 1, x))) {
                        stepWithMemory = Step.DOWN;
                    }
                }
                if(gameBoard.getTile(y,x+1).isWalkable()){
                    step = Step.RIGHT;
                    if(!memory.wasAlreadyVisited(gameBoard.getTile(y,x+1))){
                        stepWithMemory = Step.RIGHT;
                    }
                }
                break;
        }
        if(stepWithMemory != Step.NONE){
            return stepWithMemory;
        }
        return step;
    }
}
