package gui.receivers;

import connection.OnMessageReceivedListener;
import debug.Log;
import dto.Player;
import dto.messages.s2c.NewPlayerMessage;
import gui.Error;
import gui.PlayerInfo;
import gui.gamemap.GameMap;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This Receiver handles NewPlayerMessages.
 */
public class NewPlayerReceiver implements OnMessageReceivedListener<NewPlayerMessage> {

	private GameMap gameMap;

	private VBox playerList;
	private final Map<Player, PlayerInfo> playerInfoMap = new HashMap<>();

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public void setPlayerInfo(VBox playerList) {
		this.playerList = playerList;
	}
	
	/**
	 * Sets the bots on the game map and adds the names to the Player list in the UI.
	 * @param message received message
	 */
	@Override
	public void onMessageReceived(NewPlayerMessage message) {
		message.getPayload().ifPresent(players -> {
				if (gameMap == null || playerList == null) { return; }
				players.forEach(player -> {
					gameMap.set(player);
					Platform.runLater(() -> loadPlayer(player));
				});
		});
	}

	/**
	 * Loads the player info
	 *
	 * @param player Player to load
	 */
	private void loadPlayer(Player player) {
		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				playerInfo = PlayerInfo.load();
				playerInfoMap.put(player, playerInfo);
				playerList.getChildren().add(playerInfo.getNode());
			}

			playerInfo.setPlayer(player);
		} catch (IOException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

}