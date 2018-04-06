package gui;

import dto.*;

import java.util.List;

public interface UIManager {
	void load(GameData gameData);
	void loadPlayers(List<Player> players);
	void loadPlayer(Player player, boolean isPlayer);
	void setReady(Player player);

	void startGame();
	void endGame(List<Player> winners);

	void set(List<Player> players);
	void set(Player player);

	void set(Mark mark);
	void remove(Mark mark);
}
