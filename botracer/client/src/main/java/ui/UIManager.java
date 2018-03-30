package ui;

import dto.Grid;
import dto.Player;
import dto.Tile;

public interface UIManager {
	void startGame();
	GameMap loadMap(Grid<Tile> grid);
	void loadPlayer(Player player);
}
