package dto;

/**
 * Mark DTO
 *
 * @author Julian Kotrba
 */
public class Mark extends Drawable {

	private final MarkType markType;
	private final int algorithm;

	public Mark(Position position, MarkType markType) {
		super(position);
		this.algorithm = -1;
		this.markType = markType;
	}

	public Mark(int algorithm, Position position) {
		super(position);
		this.algorithm = algorithm;
		this.markType = MarkType.CHANGE_ALGORITHM;
	}

	public int getAlgorithm() {
		return algorithm;
	}

	public Position getPosition() {
		return position;
	}

	public MarkType getMarkType() {
		return markType;
	}

}