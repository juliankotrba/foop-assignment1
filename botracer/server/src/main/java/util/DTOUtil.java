package util;

import algorithms.DepthFirstSearchAlgorithm;
import algorithms.LeftWallAlgorithm;
import algorithms.RandomAlgorithm;
import algorithms.RightWallAlgorithm;
import dto.Grid;
import dto.Position;
import dto.TileType;
import game.GameBoard;
import game.Player;
import marks.*;
import tiles.PathTile;
import tiles.Tile;
import tiles.WallTile;

import java.util.ArrayList;
import java.util.List;

public class DTOUtil {

    public Grid<dto.Tile> convertGameBoard(GameBoard gameBoard) {
        int gameBoardWidth = gameBoard.getTiles()[0].length;
        int gameBoardHeight = gameBoard.getTiles().length;
        Grid<dto.Tile> grid = new Grid<>(gameBoardWidth, gameBoardHeight);

        for (int x = 0; x < gameBoardHeight; x++) {
            Tile[] tiles = gameBoard.getTiles()[x];

            for (int y = 0; y < gameBoardWidth; y++) {
                Tile tile = tiles[y];

                if (tile instanceof WallTile) {
                    grid.add(new dto.Tile(TileType.WALL, new Position(y, x)));
                }
                else if (tile instanceof PathTile) {
                    grid.add(new dto.Tile(TileType.DEFAULT, new Position(y, x)));
                }
                else {
                    grid.add(new dto.Tile(TileType.EXIT, new Position(y, x)));
                }
            }
        }
        return grid;
    }

    public List<dto.Player> convertPlayers(List<Player> players) {
        List<dto.Player> playerList = new ArrayList<>();

        for (Player player : players) {
            playerList.add(convertPlayer(player));
        }

        return playerList;
    }

    public dto.Player convertPlayer(Player player) {
        Position position = new Position(player.getWidth(), player.getHeight());
        return new dto.Player(player.getId(), player.getName(), position);
    }

    /**
     * Converts a dto.Mark to a server.Mark.
     *
     * @param mark which should be converted
     * @return the converted mark or null, if the mark has the type REMOVE
     */
    public Mark convertMarkDto(dto.Mark mark) {
        Mark markNew = null;

        switch (mark.getMarkType()) {
            case TURN_LEFT:
                markNew = new TurnLeftMark();
                break;

            case TURN_RIGHT:
                markNew = new TurnRightMark();
                break;

            case CLEAR_MEMORY:
                markNew = new ClearMemoryMark();
                break;

            case STAY_IN_AREA:
                markNew = new StayInThisAreaMark();
                break;

            case MOVE_AWAY_FROM_AREA:
                markNew = new MoveAwayMark();
                break;

            case CHANGE_ALGORITHM:
                switch (mark.getAlgorithm()){
                    case 0:
                        markNew = new ChangeAlgorithmMark(new RandomAlgorithm());
                        break;
                    case 1:
                        markNew = new ChangeAlgorithmMark(new LeftWallAlgorithm());
                        break;
                    case 2:
                        markNew = new ChangeAlgorithmMark(new RightWallAlgorithm());
                        break;
                    case 3:
                        markNew = new ChangeAlgorithmMark(new DepthFirstSearchAlgorithm());
                        break;
                }
                break;

            case REMOVE:
                break;
        }
        return  markNew;
    }
}
