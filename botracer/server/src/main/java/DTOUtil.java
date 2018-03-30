import dto.Grid;
import dto.Position;
import dto.TileType;
import game.GameBoard;
import game.Player;
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
            Position position = new Position(player.getWidth(), player.getHeight());
            playerList.add(new dto.Player(player.getId(), player.getName(), position));
        }

        return playerList;
    }
}
