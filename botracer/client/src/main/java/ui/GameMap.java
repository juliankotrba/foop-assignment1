package ui;

import dto.Mark;
import dto.Player;

public interface GameMap {
	void set(Player player);
	void set(Mark mark);
	void remove(Mark mark);
}
