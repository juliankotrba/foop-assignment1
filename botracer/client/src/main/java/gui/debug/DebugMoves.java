package gui.debug;

import dto.Player;
import dto.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class DebugMoves extends ArrayBlockingQueue<Player> {

	public DebugMoves(int number) {
		super(1024);
	}

	public void addMoves() {
		List<Player> moves = new ArrayList<>();

		moves.add(new Player(0, new Position(1,1)));
		moves.add(new Player(0, new Position(2,1)));
		moves.add(new Player(0, new Position(3,1)));
		moves.add(new Player(0, new Position(4,1)));
		moves.add(new Player(0, new Position(5,1)));
		moves.add(new Player(0, new Position(5,2)));
		moves.add(new Player(0, new Position(5,3)));
		moves.add(new Player(0, new Position(6,3)));
		moves.add(new Player(0, new Position(7,3)));
		moves.add(new Player(0, new Position(7,2)));
		moves.add(new Player(0, new Position(7,1)));
		moves.add(new Player(0, new Position(8,1)));
		moves.add(new Player(0, new Position(9,1)));
		moves.add(new Player(0, new Position(9,2)));
		moves.add(new Player(0, new Position(9,3)));
		moves.add(new Player(0, new Position(10,3)));
		moves.add(new Player(0, new Position(11,3)));
		moves.add(new Player(0, new Position(11,2)));
		moves.add(new Player(0, new Position(11,1)));
		moves.add(new Player(0, new Position(12,1)));
		moves.add(new Player(0, new Position(13,1)));
		moves.add(new Player(0, new Position(14,1)));
		moves.add(new Player(0, new Position(15,1)));
		moves.add(new Player(0, new Position(15,2)));
		moves.add(new Player(0, new Position(15,3)));
		moves.add(new Player(0, new Position(15,4)));
		moves.add(new Player(0, new Position(15,5)));


		this.addAll(moves);
		for (int i = moves.size() - 2; i >= 0; i -= 1) {
			this.add(moves.get(i));
		}
	}
}
