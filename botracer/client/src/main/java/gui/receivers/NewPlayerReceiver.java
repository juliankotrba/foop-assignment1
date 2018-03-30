package gui.receivers;

import connection.OnMessageReceivedListener;
import debug.Log;
import dto.messages.s2c.NewPlayerMessage;
import gui.gamemap.GameMap;

/**
 * This Receiver handles NewPlayerMessages.
 */
public class NewPlayerReceiver implements OnMessageReceivedListener<NewPlayerMessage> {

	private GameMap gameMap;

	/**
	 * Sets the bots on the game map and adds the names to the Player list in the UI.
	 * @param message received message
	 */
	@Override
	public void onMessageReceived(NewPlayerMessage message) {
		Log.debug("NewPlayer message received");
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

}