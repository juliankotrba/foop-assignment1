package dto.messages.s2c;

import dto.Player;
import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * Message which indicates that {player} is ready.
 */
public class PlayerReadyServerMessage implements Message<Player>, Serializable {

	private Player player;

	public PlayerReadyServerMessage(Player player) {
		this.player = player;
	}

	@Override
	public void accept(OnMessageReceivedListener onMessageReceivedListener) {
		onMessageReceivedListener.onMessageReceived(this);
	}

	@Override
	public Optional<Player> getPayload() {
		return Optional.of(this.player);
	}
}
