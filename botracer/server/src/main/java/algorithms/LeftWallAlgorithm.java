package algorithms;

import game.GameBoard;

public class LeftWallAlgorithm extends Algorithm {
    @Override
    public Step nextStep(Memory memory, GameBoard gameBoard, int x, int y) {
        Step step = Step.NONE;
        switch(memory.getLastStep()){
            case RIGHT:
                if(gameBoard.getTile(y-1,x).isWalkable()){
                    step=Step.UP;
                }else if(gameBoard.getTile(y,x+1).isWalkable()){
                    step=Step.RIGHT;
                }else if(gameBoard.getTile(y+1,x).isWalkable()){
                    step=step.DOWN;
                }else{
                    step=step.LEFT;
                }
                break;
            case LEFT:
                if(gameBoard.getTile(y+1,x).isWalkable()){
                    step=step.DOWN;
                }else if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=step.LEFT;
                }else if(gameBoard.getTile(y-1,x).isWalkable()) {
                    step = Step.UP;
                }else{
                    step=Step.RIGHT;
                }
                break;
            case UP:
                if(gameBoard.getTile(y,x-1).isWalkable()){
                    step=step.LEFT;
                }else if(gameBoard.getTile(y-1,x).isWalkable()) {
                    step = Step.UP;
                }else if(gameBoard.getTile(y,x+1).isWalkable()) {
                    step = Step.RIGHT;
                }else{
                    step = Step.DOWN;
                }
                break;
            case DOWN:
                if(gameBoard.getTile(y,x+1).isWalkable()){
                    step=Step.RIGHT;
                }else  if(gameBoard.getTile(y+1,x).isWalkable()){
                    step=step.DOWN;
                }else if(gameBoard.getTile(y,x-1).isWalkable()) {
                    step = step.LEFT;
                }else{
                    step=Step.UP;
                }
                break;
            case NONE:
                if(gameBoard.getTile(x,y+1).isWalkable()){
                    step=step.DOWN;
                }else if(gameBoard.getTile(x-1,y).isWalkable()){
                    step=step.LEFT;
                }else if(gameBoard.getTile(x,y-1).isWalkable()) {
                    step = Step.UP;
                }else{
                    step=Step.RIGHT;
                }
                break;
        }

        return step;
    }
}
