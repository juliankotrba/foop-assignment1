package dto;

import java.io.Serializable;

/**
 * Holds the game map and a player
 *
 * @author Julian Kotrba
 */
public class GameData implements Serializable {

    private Grid<Tile> gameMap;
    private Player player;

    private static final long serialVersionUID = 1L;

    public GameData(Grid<Tile> gameMap, Player player) {
        this.gameMap = gameMap;
        this.player = player;
    }

    public Grid<Tile> getGameMap() {
        return gameMap;
    }

    public void setGameMap(Grid<Tile> gameMap) {
        this.gameMap = gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
