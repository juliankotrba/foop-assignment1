package dto;

/**
 * Player
 *
 * @author  David Walter
 */
public class Player extends Drawable {

	private int number;
	private String name;

	public Player(int number, Position position) {
		this(number, null, position);
	}

	public Player(int number, String name, Position position) {
		super(position);
		this.number = number;
		this.name = name;
	}

	public int getNumber() {
		return number;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		if (name == null) {
			return "Player " + (getNumber() + 1);
		}
		return name;
	}
}
