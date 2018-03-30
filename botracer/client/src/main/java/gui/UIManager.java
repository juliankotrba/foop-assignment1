package gui;

import dto.Grid;
import dto.Mark;
import dto.Player;
import dto.Tile;

public interface UIManager {
	void loadMap(Grid<Tile> grid);
	void loadPlayer(Player player);

	void startGame();

	void set(Player player);
	void set(Mark mark);
	void remove(Mark mark);
}
