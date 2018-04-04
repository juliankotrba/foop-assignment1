package dto.messages.s2c;

import dto.Mark;
import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Message which indicates that a player stepped on a mark and it should be removed.
 */
public class RemoveMarksMessage implements Message<List<Mark>>, Serializable {

	private List<Mark> marks;

	public RemoveMarksMessage(List<Mark> marks) {
		this.marks = marks;
	}

	@Override
	public void accept(OnMessageReceivedListener onMessageReceivedListener) {
		onMessageReceivedListener.onMessageReceived(this);
	}

	@Override
	public Optional<List<Mark>> getPayload() {
		return Optional.of(this.marks);
	}
}
