package game;

import tiles.PathTile;
import tiles.Tile;

import java.util.List;

public class MazeLoader {

    public GameBoard createGameBoard(){


        //Todo: implement real mazeloader
        Tile[][] tiles = new Tile[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                tiles[i][j]=new PathTile();
            }
        }
        GameBoard gameBoard = new GameBoard();
        gameBoard.setTiles(tiles);
        return gameBoard;
    }

    public void setStartingPosition(List<Player> playerList, GameBoard gameBoard){
        int x=0;
        int y=0;
        //Todo: implement starting positions


    }


}
