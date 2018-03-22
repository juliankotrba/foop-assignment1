package dto;

/**
 * Tile
 *
 * @author  David Walter
 */
public class Tile extends Drawable {

	private TileType type;

	public Tile(TileType type, Position position) {
		super(-1, position);
		this.type = type;
	}

	public TileType getType() {
		return type;
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}

	public Position getPosition() {
		return position;
	}
}
