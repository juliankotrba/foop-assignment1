package algorithms.special;

import algorithms.Memory;
import algorithms.Step;
import game.GameBoard;

public class MoveAwaySpecial extends Special {

    private int xStart;
    private int yStart;
    private int reachedStart;

    public MoveAwaySpecial(int xStart, int yStart){
        this.xStart=xStart;
        this.yStart=yStart;
        reachedStart=0;
    }
    @Override
    public Step move(Memory memory, GameBoard gameBoard, int x, int y){
        if(x== xStart &&y==yStart){
            reachedStart++;
        }

        Step step = Step.NONE;
        int maxManhattenDistance = 0;


        if(gameBoard.getTile(y-1,x).isWalkable()&&memory.getLastStep()!=Step.DOWN){
            int distance = calculateManhattenDistance(x,y-1, xStart,yStart);
            if(maxManhattenDistance<distance){
                step=Step.UP;
                maxManhattenDistance=distance;
            }
        }
        if(gameBoard.getTile(y+1,x).isWalkable()&&memory.getLastStep()!=Step.UP){
            int distance = calculateManhattenDistance(x,y+1, xStart,yStart);
            if(maxManhattenDistance<distance){
                step=Step.DOWN;
                maxManhattenDistance=distance;
            }
        }
        if(gameBoard.getTile(y,x-1).isWalkable()&&memory.getLastStep()!=Step.RIGHT){
            int distance = calculateManhattenDistance(x-1,y, xStart,yStart);
            if(maxManhattenDistance<distance){
                step=Step.LEFT;
                maxManhattenDistance=distance;
            }
        }
        if(gameBoard.getTile(y,x+1).isWalkable()&&memory.getLastStep()!=Step.LEFT){
            int distance = calculateManhattenDistance(x+1,y,xStart,yStart);
            if(maxManhattenDistance<distance){
                step=Step.RIGHT;

            }
        }

        if(step==Step.NONE){
            return memory.getLastStep().getOpposite();
        }
        return step;
    }

    @Override
    public Boolean finished() {
        if (reachedStart==3){
            return true;
        }
        return false;
    }

    private int calculateManhattenDistance(int x1,int y1, int x2, int y2){
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}
