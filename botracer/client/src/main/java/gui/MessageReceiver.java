package gui;

import debug.Log;
import dto.messages.OnMessageReceivedListener;
import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.GameDataMessage;
import dto.messages.s2c.GameStartMessage;
import dto.messages.s2c.NewPlayerMessage;
import dto.messages.s2c.PlayersChangedMessage;
import ui.GameMap;
import ui.UIManager;
import javafx.application.Platform;

import java.util.Objects;

public class MessageReceiver implements OnMessageReceivedListener {

	private UIManager uiManager;
	private GameMap gameMap;

	MessageReceiver(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	@Override
	public void onMessageReceived(GameDataMessage message) {
		message.getPayload().ifPresent(grid -> gameMap = uiManager.loadMap(grid));
	}

	@Override
	public void onMessageReceived(GameStartMessage message) {
		uiManager.startGame();
	}

	@Override
	public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) {
		Log.verbose("MarkPlacement message received");
		if (gameMap == null) {
			return;
		}
		message.getPayload().ifPresent(mark -> gameMap.set(mark));
	}

	@Override
	public void onMessageReceived(NewPlayerMessage message) {
		Log.debug("NewPlayer message received");
		if (gameMap == null) {
			return;
		}

		message.getPayload().ifPresent(players ->
				players.forEach(player -> {
					gameMap.set(player);
					Platform.runLater(() -> uiManager.loadPlayer(player));
				}));
	}

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

	/*
	 * Following methods can be left out, because only the server handles these messages.
	 */

	@Override
	public void onMessageReceived(ChangeStrategyMessage message) {
	}

	@Override
	public void onMessageReceived(MarkPlacementMessage message) {
	}

	@Override
	public void onMessageReceived(PlayerNameMessage message) {
	}

	@Override
	public void onMessageReceived(PlayerReadyMessage message) {
	}
}
