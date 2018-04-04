package dto.messages.s2c;

import dto.Player;
import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GameEndMessage implements Message<List<Player>>, Serializable {

	private static final long serialVersionUID = 1L;

	private List<Player> winners;

	public GameEndMessage(List<Player> winners) {
		this.winners = winners;
	}

	@Override
	public void accept(OnMessageReceivedListener onMessageReceivedListener) {
		onMessageReceivedListener.onMessageReceived(this);
	}

	@Override
	public Optional<List<Player>> getPayload() {
		return Optional.of(winners);
	}
}