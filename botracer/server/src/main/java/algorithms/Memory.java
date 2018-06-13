package algorithms;


import algorithms.special.Special;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Memory {

    private Step lastStep;
    private List<Tile> visited;
    private Stack<Step> path;
    private Special special;
    public Memory(){
        reset();
    }

    public void reset(){
        lastStep=Step.NONE;
        visited= new ArrayList<>();
        path = new Stack<>();
    }

    public Step getLastStep() {
        return lastStep;
    }

    public void setLastStep(Step lastStep) {
        this.lastStep = lastStep;
    }

    public boolean wasAlreadyVisited(Tile tile){
        return visited.contains(tile);
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

    public void addVisited(Tile tile){
        visited.add(tile);
    }
    public void addLastTileToStack(Step step){
        path.push(step);
    }
    public Step getLastTileFromStack(){
        if(path.empty()){
            return null;
        }
        return path.pop();
    }
}
