public class GameBoard {
    private Tile[][] tiles;

    public void newMark(Mark mark,int x, int y){
        if(tiles[x][y].getMark()==null) {
            tiles[x][y].setMark(mark);
        }
    }
}
