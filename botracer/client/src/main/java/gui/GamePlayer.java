package gui;

import dto.Player;
import dto.Position;

public class GamePlayer {

	private Position position;
	private GameTile tile;

	public GamePlayer(Player player, GameTile tile) {

		position = player.getPosition();
	}

	public void movePlayer(Position position) {

	}

}
