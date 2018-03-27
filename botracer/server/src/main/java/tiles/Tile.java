package tiles;

import game.Player;
import marks.Mark;

public abstract class Tile{
    private Mark mark;

    public abstract boolean isWalkable();

    public void enters(Player player){
        if(mark!=null){
            mark.enter(player);
        }
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
