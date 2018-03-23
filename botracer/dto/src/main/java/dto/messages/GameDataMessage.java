package dto.messages;

import dto.GameData;
import dto.Grid;
import dto.Tile;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which holds the game map
 *
 * @author Julian Kotrba
 */
public class GameDataMessage implements Message<GameData>, Serializable {

    private GameData gameData;

    private static final long serialVersionUID = 1L;

    public GameDataMessage(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public Optional<GameData> getPayload() {
        return Optional.of(this.gameData);
    }
}
