package gui.receivers;

import connection.OnMessageReceivedListener;
import debug.Log;
import dto.messages.s2c.PlayersChangedMessage;
import gui.gamemap.GameMap;

import java.util.Objects;

public class PlayersChangedReceiver implements OnMessageReceivedListener<PlayersChangedMessage> {

	private GameMap gameMap;

	@Override
	public void onMessageReceived(PlayersChangedMessage message) {
		Log.verbose("PlayersChanged message received");
		if (gameMap == null) {
			return;
		}
		message.getPayload().ifPresent(players ->
				players.stream()
						.filter(Objects::nonNull)
						.forEach(gameMap::set));
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
}