package game;

import tiles.GoalTile;
import tiles.PathTile;
import tiles.Tile;
import tiles.WallTile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MazeLoader {

    /**
     * Creates a game board according to a string representation in a file at path.
     *
     * @param path of the file
     * @return a new game board according file specifications
     * @throws IOException if the file can not be found or other IO Exceptions occur
     */
    public GameBoard createGameBoard(String path) throws IOException {

        int x;
        int y;
        GameBoard gameBoard = new GameBoard();

        List<String> lines = readGameBoardFile(path);
        x = lines.size();
        // todo: error handling? (e.g. if maze only has <= 2 lines)
        y = lines.get(0).length();

        Tile[][] tiles = buildGameBoard(lines, x, y);
        gameBoard.setTiles(tiles);
        return gameBoard;
    }



    public void setStartingPosition(List<Player> playerList, GameBoard gameBoard){
        int x=0;
        int y=0;
        //Todo: implement starting positions


    }


    /**
     * Builds the game board according to lines.
     *      - '*' = WallTile
     *      - ' ' = PathTile
     *      - 'E' = GoalTile
     *
     * @param lines contains the structure of the game board
     * @param x height of the game board
     * @param y length of the game board
     * @return a Tile[][] representation of the game board
     */
    private Tile[][] buildGameBoard(List<String> lines, int x, int y) {
        Tile[][] tiles = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            String line = lines.get(i);
            char c;

            for (int j = 0; j < y; j++) {
                switch (c = line.charAt(j)) {
                    case '*':
                        tiles[i][j] = new WallTile();
                        break;

                    case ' ':
                        tiles[i][j] = new PathTile();
                        break;

                    case 'E':
                        tiles[i][j] = new GoalTile();
                        break;
                }
            }
        }

        return tiles;
    }


    /**
     * Reads the file at path line by line and saves it into an list.
     *
     * @param path of the file
     * @return list of Strings, representing the file's content
     * @throws IOException if the file does not exist or other IOExceptions occur
     */
    private List<String> readGameBoardFile (String path) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while((line = reader.readLine()) != null){
                lines.add(line);
            }
        }

        return lines;
    }
}
