package dto;

/**
 * Mark DTO
 *
 * @author Julian Kotrba
 */
public class Mark extends Drawable {

	private MarkType markType;

	public Mark(Position position, MarkType markType) {
		super(-1, position);
		this.markType = markType;
	}

	public Mark(int algorithm, Position position) {
		super(algorithm, position);
		this.markType = MarkType.CHANGE_ALGORITHM;
	}

	public int getIndex() {
		return index;
	}

	public Position getPosition() {
		return position;
	}

	public MarkType getMarkType() {
		return markType;
	}

}