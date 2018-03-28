package game;

import tiles.GoalTile;
import tiles.PathTile;
import tiles.Tile;
import tiles.WallTile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

        buildGameBoard(gameBoard, lines, x, y);
        return gameBoard;
    }


    /**
     * Sets all players on a random place on the game board.
     * The chosen position is not near the goal, is a PathTile and is not the same as the position of another player.
     *
     * @param playerList players which should be positioned
     * @param gameBoard on which the players should be positioned
     */
    public void setStartingPosition(List<Player> playerList, GameBoard gameBoard){
        Integer[] goal = gameBoard.getGoalLocation();
        int gameBoardHeight = gameBoard.getTiles().length;
        int gameBoardWidth = gameBoard.getTiles()[0].length;

        Map<String, Integer> placeableArea = calculatePlaceableArea(goal, gameBoardHeight, gameBoardWidth);

        for (Player player : playerList) {
            boolean playerAlreadyOnPosition = false;

            // search a position for player as long as a suitable position is found
            while (true) {
                int x = ThreadLocalRandom.current().nextInt(placeableArea.get("top"), placeableArea.get("bottom"));
                int y = ThreadLocalRandom.current().nextInt(placeableArea.get("left"), placeableArea.get("right"));

                // position must be a PathTile
                if (gameBoard.getTile(x, y).getClass().equals(PathTile.class)) {
                    Player player1;
                    Iterator<Player> playerIterator = playerList.iterator();

                    // check if the position does not overlap with the position other players
                    while (!(player1 = playerIterator.next()).equals(player)) {
                        if (player1.getX() == x && player1.getY() == y) {
                            playerAlreadyOnPosition = true;
                            break;
                        }
                    }
                    if (!playerAlreadyOnPosition) {
                        player.setX(x);
                        player.setY(y);
                        break;
                    }
                }
            }
        }

    }


    /**
     * Builds the game board according to lines and sets the location of the goal.
     *      - '*' = WallTile
     *      - ' ' = PathTile
     *      - 'E' = GoalTile
     *
     * @param gameBoard which should be filled
     * @param lines contains the structure of the game board
     * @param x height of the game board
     * @param y length of the game board
     */
    private void buildGameBoard(GameBoard gameBoard, List<String> lines, int x, int y) {
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
                        gameBoard.setGoalLocation(new Integer[]{i, j});
                        break;
                }
            }
        }

        gameBoard.setTiles(tiles);
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

    /**
     * Calculates the area on which players can be initially set.
     * The area excludes the first third of the side where the goal is located.
     *      E.g.: goal is on top: placeable area is the last two third of the game board height.
     *            goal is on the right: placeable area is the first two thirds of the game board width.
     *
     * @param goal x, y location of the goal
     * @param gameBoardHeight height of the game board (x-axis)
     * @param gameBoardWidth widht of the game board (y-axis)
     * @return a map containting the boundaries of the placeable area (incl). (containing keys: 'bottom', 'top', 'left', 'right')
     */
    private Map<String, Integer> calculatePlaceableArea (Integer[] goal, int gameBoardHeight, int gameBoardWidth) {
        Map<String, Integer> placeableArea = new HashMap<>();
        int goalX = goal[0];
        int goalY = goal[1];

        int boundaryLeft = 1;
        int boundaryRight = gameBoardWidth - 2;
        int boundaryTop = 1;
        int boundaryBottom = gameBoardHeight - 2;

        int heightOfOneThirdOfGameBoard = Math.round(gameBoardHeight / 3);
        int widthOfOneThirdOfGameBoard = Math.round(gameBoardWidth / 3);

        // goal is on top of the game board
        if (goalX == 0) {
            boundaryTop = heightOfOneThirdOfGameBoard;
        }
        // goal is at bottom of the game board
        else if (goalX == gameBoardHeight - 1) {
            boundaryBottom = gameBoardHeight - heightOfOneThirdOfGameBoard;
        }
        // goal is on the left side of the game board
        else if (goalY == 0) {
            boundaryLeft = widthOfOneThirdOfGameBoard;
        }
        // goal is on the right side of the game board
        else if (goalY == gameBoardWidth - 1) {
            boundaryRight = gameBoardWidth - widthOfOneThirdOfGameBoard;
        }

        placeableArea.put("top", boundaryTop);
        placeableArea.put("bottom", boundaryBottom);
        placeableArea.put("left", boundaryLeft);
        placeableArea.put("right", boundaryRight);

        return placeableArea;
    }
}
