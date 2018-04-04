package connection;

import debug.Log;
import dto.messages.OnMessageReceivedListener;
import dto.messages.c2s.ChangeStrategyMessage;
import dto.messages.c2s.MarkPlacementMessage;
import dto.messages.c2s.PlayerNameMessage;
import dto.messages.c2s.PlayerReadyMessage;
import dto.messages.s2c.*;
import gui.UIManager;

public class MessageReceiver implements OnMessageReceivedListener {

	private UIManager uiManager;

	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	@Override
	public void onMessageReceived(GameDataMessage message) {
		message.getPayload().ifPresent(uiManager::loadMap);
	}

	@Override
	public void onMessageReceived(GameStartMessage message) {
		uiManager.startGame();
	}

	@Override
	public void onMessageReceived(GameEndMessage message) {
		message.getPayload().ifPresent(uiManager::endGame);
	}

	@Override
	public void onMessageReceived(dto.messages.s2c.MarkPlacementMessage message) {
		Log.verbose("MarkPlacement message received");
		message.getPayload().ifPresent(uiManager::set);
	}

	@Override
	public void onMessageReceived(NewPlayerMessage message) {
		Log.debug("NewPlayer message received");
		message.getPayload().ifPresent(uiManager::loadPlayers);
	}

	@Override
	public void onMessageReceived(PlayersChangedMessage message) {
		Log.verbose("PlayersChanged message received");
		message.getPayload().ifPresent(uiManager::set);
	}

	@Override
	public void onMessageReceived(PlayerReadyServerMessage message) {
		Log.debug("Player is now ready");
		message.getPayload().ifPresent(player -> {
			uiManager.setReady(player);
		});
	}

	@Override
	public void onMessageReceived(RemoveMarksMessage message) {
		Log.debug("Remove marks");
		message.getPayload().ifPresent(marks -> marks.forEach(uiManager::remove));
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
