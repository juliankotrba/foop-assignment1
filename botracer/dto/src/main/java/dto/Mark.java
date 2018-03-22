package dto;

/**
 * Mark DTO
 *
 * @author Julian Kotrba
 */
public class Mark extends Drawable {

	private MarkType markType;

	public static Mark algorithm(int algorithm, Position position) {
		return new Mark(algorithm, position);
	}

	public Mark(Position position, MarkType markType) {
		super(0, position);
		this.markType = markType;
	}

	private Mark(int algorithm, Position position) {
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