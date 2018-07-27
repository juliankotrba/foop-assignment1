import game.Game;
import marks.ClearMemoryMark;

import java.util.concurrent.ThreadLocalRandom;

public class Datagenerator implements Runnable {

	Game game;

	public Datagenerator(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while (game.getGameRunning()) {
			game.newMark(new ClearMemoryMark(), ThreadLocalRandom.current().nextInt(0, 9), ThreadLocalRandom.current().nextInt(0, 9));
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
