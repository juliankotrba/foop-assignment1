package dto;

import java.io.Serializable;

/**
 * Player
 *
 * @author  David Walter
 */
public class Player extends Drawable {

	public Player(int number, Position position) {
		super(number, position);
	}

	public int getNumber() {
		return index;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
