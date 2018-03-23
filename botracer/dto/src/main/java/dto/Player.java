package dto;

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

	@Override
	public int hashCode() {
		return getNumber();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj.getClass() != Player.class) return false;

		return equals((Player) obj);
	}

	public boolean equals(Player player) {
		return this.getNumber() == player.getNumber();
	}
}
