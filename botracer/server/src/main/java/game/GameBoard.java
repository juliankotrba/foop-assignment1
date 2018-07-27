package game;

import dto.Position;
import marks.Mark;
import tiles.Tile;

public class GameBoard {

	private static GameBoard gameBoard;

	private Tile[][] tiles;
	private Position goalLocation;


	private GameBoard() {
	}

	public static synchronized GameBoard getInstance() {
		if (GameBoard.gameBoard == null) {
			GameBoard.gameBoard = new GameBoard();
		}
		return GameBoard.gameBoard;
	}

	public void newMark(Mark mark, int height, int width) {
		tiles[height][width].setMark(mark);
	}

	public Tile getTile(int height, int width) {
		return tiles[height][width];
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Position getGoalLocation() {
		return goalLocation;
	}

	public void setGoalLocation(Position goalLocation) {
		this.goalLocation = goalLocation;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Tile[] tile : tiles) {
			for (Tile aTile : tile) {
				stringBuilder.append(aTile);
			}
			stringBuilder.append("\n");
		}

		return stringBuilder.toString();
	}
}
