package dto.messages.s2c;

import dto.GameData;
import dto.Grid;
import dto.Tile;
import dto.messages.Message;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which holds the game map
 *
 * @author Julian Kotrba
 */
public class GameDataMessage implements Message<Grid<Tile>>, Serializable {

    //private GameData gameData;

    private Grid<Tile> gameBoard;

    private static final long serialVersionUID = 1L;

    public GameDataMessage(Grid<Tile> gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public Optional<Grid<Tile>> getPayload() {
        return Optional.of(this.gameBoard);
    }
}
