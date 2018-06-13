package algorithms;


import algorithms.special.Special;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * One bots memory
 */
public class Memory {

    private Step lastStep;
    private List<Tile> visited;
    private Stack<Step> path;
    private Special special;
    public Memory(){
        reset();
    }

    /**
     * Resets the whole meomory
     */
    public void reset(){
        lastStep=Step.NONE;
        visited= new ArrayList<>();
        path = new Stack<>();
        special = null;
    }

    /**
     * @return the last ste done
     */
    public Step getLastStep() {
        return lastStep;
    }

    /**
     * Sets the last done step
     * @param lastStep the last step
     */
    public void setLastStep(Step lastStep) {
        this.lastStep = lastStep;
    }

    /**
     * Returns whether a certain tile was already visited or not
     *
     * @param tile the tile to check
     * @return true if it was visited, false if not
     */
    public boolean wasAlreadyVisited(Tile tile){
        return visited.contains(tile);
    }

    /**
     * @return the current special if set, null if no special is set
     */
    public Special getSpecial() {
        return special;
    }

    /**
     * Set the special
     *
     * @param special the new special
     */
    public void setSpecial(Special special) {
        this.special = special;
    }

    /**
     * Add a visited tile to the memory
     *
     * @param tile the visited tile
     */
    public void addVisited(Tile tile){
        visited.add(tile);
    }

    /**
     * add last done step to the path
     *
     * @param step last step
     */
    public void addLastTileToStack(Step step){
        path.push(step);
    }

    /**
     * Returns the last step done
     *
     * @return the step if the memory is not empty and null otherwise
     */
    public Step getLastTileFromStack(){
        if(path.empty()){
            return null;
        }
        return path.pop();
    }
}
