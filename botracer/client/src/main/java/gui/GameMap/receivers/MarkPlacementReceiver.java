package gui.GameMap.receivers;

import connection.OnMessageReceivedListener;
import debug.Log;
import dto.messages.s2c.MarkPlacementMessage;
import gui.GameMap.GameMap;

public class MarkPlacementReceiver implements OnMessageReceivedListener<MarkPlacementMessage> {
	private GameMap gameMap;

	@Override
	public void onMessageReceived(MarkPlacementMessage message) {
		Log.verbose("MarkPlacement message received");
		if (gameMap == null) {
			return;
		}
		message.getPayload().ifPresent(mark -> gameMap.set(mark));
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
}