package gui;

import dto.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameMap
 *
 * @author  David Walter
 */
class GameMap extends Pane {

	private Grid<GameTile> gameTiles;
	private Map<Player, GameTile> playerMap = new HashMap<>();

	GameMap(Grid<Tile> maze) {
		gameTiles = new Grid<>(maze.getWidth(), maze.getHeight());

		maze.forEach(this::load);
		gameTiles.forEach(this::render);

		this.widthProperty().addListener((observable, oldValue, newValue) -> draw());
		this.heightProperty().addListener((observable, oldValue, newValue) -> draw());
	}

	/**
	 * Loads the Tile into a GameTile
	 * @param tile Tile to load
	 */
	private void load(Tile tile) {
		GameTile gameTile = new GameTile(tile);
		gameTiles.add(gameTile);
		getChildren().add(gameTile);
	}

	/**
	 * Renders the GameTiles (e.g. shadows)
	 * @param gameTile GameTile to render
	 */
	private void render(GameTile gameTile) {
		if (!gameTile.isWall()) { return; }

		GameTile up = next(gameTile, Direction.UP);
		GameTile down = next(gameTile, Direction.DOWN);
		GameTile right = next(gameTile, Direction.RIGHT);

		boolean front = !(down != null && down.isWall());
		if (right != null && !right.isWall()) {
			right.drawShadow(front, up == null || !up.isWall());
		}
		gameTile.drawBorder(front);
	}

	/**
	 * Resizes and centers the gamemap based on container size
	 */
	private void draw() {
		double tileSizeW = this.widthProperty().doubleValue() / gameTiles.getWidth();
		double tileSizeH = this.heightProperty().doubleValue() / gameTiles.getHeight();

		if (tileSizeW == 0 || tileSizeH == 0) { return; }

		double tileSize;
		final double offsetX;
		final double offsetY;

		if (tileSizeH < tileSizeW) {
			tileSize = tileSizeH;
			offsetX = (this.widthProperty().doubleValue() - tileSize * gameTiles.getWidth()) / 2;
			offsetY = 0;
		} else {
			tileSize = tileSizeW;
			offsetX = 0;
			offsetY = (this.heightProperty().doubleValue() - tileSize * gameTiles.getHeight()) / 2;
		}

		gameTiles.forEach(gameTile -> gameTile.draw(tileSize, offsetX, offsetY));
	}

	/**
	 * Gets neighbouring game tiles
	 * @param gameTile origin
	 * @param direction Direction
	 * @return GameTile in the given direction or null if there is not a game tile
	 */
	private GameTile next(GameTile gameTile, Direction direction) {
		int width = gameTiles.getWidth();
		int height = gameTiles.getHeight();

		int x = gameTile.getX();
		int y = gameTile.getY();

		switch (direction) {
			case LEFT:
				if (x > 0 && x < width) {
					return gameTiles.get(x - 1, y);
				}
				break;
			case RIGHT:
				if (x >= 0 && x < width - 1) {
					return gameTiles.get(x + 1, y);
				}
				break;
			case UP:
				if (y > 0 && y < height) {
					return gameTiles.get(x, y - 1);
				}
				break;
			case DOWN:
				if (y >= 0 && y < height - 1) {
					return gameTiles.get(x, y + 1);
				}
				break;
			default:
				break;
		}

		return null;
	}

	/**
	 * Moves the player to its location
	 * @param player Player to add/move on the map
	 */
	public void set(Player player) {
		GameTile oldPosition = playerMap.get(player);
		if (oldPosition != null) {
			oldPosition.clearPlayer();
		}
		GameTile newPosition = gameTiles.get(player.getPosition());
		newPosition.setPlayer(player.getNumber());
		playerMap.put(player, newPosition);
	}

	/**
	 * Adds the mark to the map
	 * @param mark Mark to add
	 */
	public void set(Mark mark) {
		gameTiles.get(mark.getPosition()).setMark(mark);
	}

	/**
	 * Removes the mark from the map
	 * @param mark Mark to remove
	 */
	public void remove(Mark mark) {
		gameTiles.get(mark.getPosition()).clearMark();
	}
}

enum Direction {
	UP, DOWN, LEFT, RIGHT
}
