package game;

import marks.Mark;
import tiles.Tile;

public class GameBoard {
    private Tile[][] tiles;

    public void newMark(Mark mark, int x, int y){
        if(tiles[x][y].getMark()==null) {
            tiles[x][y].setMark(mark);
        }
    }

    public Tile getTile(int x,int y){
        return tiles[x][y];
    }
}
