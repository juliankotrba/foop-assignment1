package dto;

/**
 * Tile
 *
 * @author  David Walter
 */
public class Tile extends Drawable {

	private final TileType type;

	public Tile(TileType type, Position position) {
		super(position);
		this.type = type;
	}

	public TileType getType() {
		return type;
	}

	public int getX() {
		return position.getWidth();
	}

	public int getY() {
		return position.getHeight();
	}

	public Position getPosition() {
		return position;
	}
}
