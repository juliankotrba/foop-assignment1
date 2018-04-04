package gui;

import dto.Grid;
import dto.Mark;
import dto.Player;
import dto.Tile;

import java.util.List;

public interface UIManager {
	void loadMap(Grid<Tile> grid);
	void loadPlayers(List<Player> players);
	void loadPlayer(Player player);
	void setReady(Player player);

	void startGame();

	void set(List<Player> players);
	void set(Player player);

	void set(Mark mark);
	void remove(Mark mark);
}
