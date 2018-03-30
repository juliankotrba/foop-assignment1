package connection;

import debug.Log;
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

}
