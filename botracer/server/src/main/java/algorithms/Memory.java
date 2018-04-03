package algorithms;


import algorithms.special.Special;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Memory {

    private Step lastStep;
    private List<Tile> visited;
    private Special special;
    public Memory(){
        reset();
    }

    public void reset(){
        lastStep=Step.NONE;
        visited= new ArrayList<>();
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
}
