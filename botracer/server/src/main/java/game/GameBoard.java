package game;

import marks.Mark;
import tiles.Tile;

import java.util.Arrays;

public class GameBoard {
    private Tile[][] tiles;
    private Integer[] goalLocation;


    public void newMark(Mark mark, int x, int y){
        tiles[y][x].setMark(mark);
    }

    public Tile getTile(int x,int y){
        return tiles[y][x];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Integer[] getGoalLocation() {
        return goalLocation;
    }

    public void setGoalLocation(Integer[] goalLocation) {
        this.goalLocation = goalLocation;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Tile[] tile : tiles) {
            for (Tile aTile : tile) {
                stringBuilder.append(aTile);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
