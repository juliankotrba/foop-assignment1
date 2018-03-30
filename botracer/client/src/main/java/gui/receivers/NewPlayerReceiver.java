//package gui.GameMap.receivers;

/*import dto.messages.OnMessageReceivedListener;
import debug.Log;
import dto.Player;
import dto.messages.s2c.NewPlayerMessage;
import gui.GameMap.GameMap;
import gui.Sprites;

import java.util.Objects;*/

/**
 * This Receiver handles NewPlayerMessages.
 */
/*public class NewPlayerReceiver implements OnMessageReceivedListener<NewPlayerMessage> {

	private GameMap gameMap;

	/**
	 * Sets the bots on the game map and adds the names to the Player list in the UI.
	 * @param message received message
	 */
	/*@Override
	public void onMessageReceived(NewPlayerMessage message) {
		Log.debug("NewPlayer message received");
		if (gameMap == null) {
			return;
		}
		// set bots on map
		message.getPayload().ifPresent(players ->
				players.stream()
						.filter(Objects::nonNull)
						.forEach(gameMap::set));

		// TODO: set player names in UI player list
		message.getPayload().ifPresent(
				players -> {
					for (Player player : players) {
						Sprites.setHighlight(player.getNumber());
					}
				}
		);
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
}*/