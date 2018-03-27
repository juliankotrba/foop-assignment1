package game;

import marks.Mark;
import tiles.Tile;

import java.util.Arrays;

public class GameBoard {
    private Tile[][] tiles;


    public void newMark(Mark mark, int x, int y){
        tiles[x][y].setMark(mark);
    }

    public Tile getTile(int x,int y){
        return tiles[x][y];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles[i].length;j++){
                if(tiles[i][j].getMark()==null){
                    stringBuilder.append("_");
                }else{
                    stringBuilder.append("x");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
