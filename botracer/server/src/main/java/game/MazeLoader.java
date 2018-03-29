package game;

import dto.Position;
import tiles.GoalTile;
import tiles.PathTile;
import tiles.Tile;
import tiles.WallTile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MazeLoader {

    private Map<String, Integer> placeableArea;

    /**
     * Creates a game board according to a string representation in a file at path.
     *
     * @param path of the file
     * @return a new game board according file specifications
     * @throws IOException if the file can not be found or other IO Exceptions occur
     */
    public GameBoard createGameBoard(String path) throws IOException, URISyntaxException {
        GameBoard gameBoard = GameBoard.getInstance();


        List<String> lines = Files.readAllLines(Paths.get(new File(MazeLoader.class.getResource(path).toURI()).getAbsolutePath()));
        int gameBoardHeight = lines.size();
        // todo: error handling? (e.g. if maze only has <= 2 lines)
        int gameBoardWidth = lines.get(0).length();

        buildGameBoard(gameBoard, lines, gameBoardWidth, gameBoardHeight);
        placeableArea = calculatePlaceableArea(gameBoard.getGoalLocation(), gameBoardHeight, gameBoardWidth);
        return gameBoard;
    }


    /**
     * Gets a new Position on a random place on the game board.
     * The chosen position is not near the goal, is a PathTile and is not the same as the position of another player.
     *
     * @param playerList already positioned players
     * @param gameBoard on which the players should be positioned
     * @return a new Position
     */
    public Position getNewStartingPosition(List<Player> playerList, GameBoard gameBoard){
        boolean foundPosition = false;
        int height = 0;
        int width = 0;

        // search a position for player as long as a suitable position is found
        while (!foundPosition) {
            foundPosition = true;
            height = ThreadLocalRandom.current().nextInt(placeableArea.get("top"), placeableArea.get("bottom"));
            width = ThreadLocalRandom.current().nextInt(placeableArea.get("left"), placeableArea.get("right"));

            // position must be a PathTile
            if (gameBoard.getTile(height, width).getClass().equals(PathTile.class)) {

                // check if the position does not overlap with the position other players
                for (Player player : playerList){
                    if (player.getWidth() == width && player.getHeight() == height) {
                        foundPosition = false;
                        break;
                    }
                }
            } else {
                foundPosition = false;
            }
        }
        return new Position(width, height);
    }


    /**
     * Builds the game board according to lines and sets the location of the goal.
     *      - '*' = WallTile
     *      - ' ' = PathTile
     *      - 'E' = GoalTile
     *
     * @param gameBoard which should be filled
     * @param lines contains the structure of the game board
     * @param gameBoardHeight height of the game board
     * @param gameBoardWidth length of the game board
     */
    private void buildGameBoard(GameBoard gameBoard, List<String> lines, int gameBoardWidth, int gameBoardHeight) {
        Tile[][] tiles = new Tile[gameBoardHeight][gameBoardWidth];

        for (int i = 0; i < gameBoardHeight; i++) {
            String line = lines.get(i);

            for (int j = 0; j < gameBoardWidth; j++) {
                switch (line.charAt(j)) {
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
        int goalX = goal[1];
        int goalY = goal[0];

        int boundaryLeft = 1;
        int boundaryRight = gameBoardWidth - 2;
        int boundaryTop = 1;
        int boundaryBottom = gameBoardHeight - 2;

        int heightOfOneThirdOfGameBoard = Math.round(gameBoardHeight / 3);
        int widthOfOneThirdOfGameBoard = Math.round(gameBoardWidth / 3);

        // goal is on top of the game board
        if (goalY == 0) {
            boundaryTop = heightOfOneThirdOfGameBoard;
        }
        // goal is at bottom of the game board
        else if (goalY == gameBoardHeight - 1) {
            boundaryBottom = gameBoardHeight - heightOfOneThirdOfGameBoard;
        }
        // goal is on the left side of the game board
        else if (goalX == 0) {
            boundaryLeft = widthOfOneThirdOfGameBoard;
        }
        // goal is on the right side of the game board
        else if (goalX == gameBoardWidth - 1) {
            boundaryRight = gameBoardWidth - widthOfOneThirdOfGameBoard;
        }

        placeableArea.put("top", boundaryTop);
        placeableArea.put("bottom", boundaryBottom);
        placeableArea.put("left", boundaryLeft);
        placeableArea.put("right", boundaryRight);

        return placeableArea;
    }
}
