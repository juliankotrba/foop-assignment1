package tiles;

import game.Player;
import marks.Mark;


/**
 * abstract class for possible tiles in the maze
 */
public abstract class Tile{
    private Mark mark;

    /**
     * Chceks whether it is possible to walk onto this tile or not
     *
     * @return true if it is possible to walk onto this tile, false if not
     */
    public abstract boolean isWalkable();

    /**
     * Performs action when the tile was entered
     *
     * @param player the bot who enters the tile
     */
    public void enters(Player player){
        if(mark!=null){
            mark.enter(player);
        }
    }

    /**
     * Get the mark of a certain tile
     *
     * @return the mark if it is set, null otherwise
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Sets a mark onto the field
     * @param mark the new mark of the field
     */
    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "mark=" + mark +
                '}';
    }
}
