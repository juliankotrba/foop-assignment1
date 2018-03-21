package dto;

import java.io.Serializable;

/**
 * Position DTO
 *
 * @author  David Walter
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Position position;
	private TileType type;

	public Tile(TileType type, Position position) {
		this.type = type;
		this.position = position;
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
}
