package dto.messages.s2c;

import dto.GameData;
import dto.Grid;
import dto.Tile;
import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which holds the game map and the player
 *
 * @author Julian Kotrba
 */
public class GameDataMessage implements Message<GameData>, Serializable {

    private GameData gameData;

    //private Grid<Tile> gameBoard;

    private static final long serialVersionUID = 1L;

    public GameDataMessage(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void accept(OnMessageReceivedListener onMessageReceivedListener) {
        onMessageReceivedListener.onMessageReceived(this);
    }

    @Override
    public Optional<GameData> getPayload() {
        return Optional.of(this.gameData);
    }
}
