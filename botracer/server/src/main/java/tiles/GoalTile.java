package tiles;

public class GoalTile extends Tile {
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public String toString(){
        return "E";
    }
}
