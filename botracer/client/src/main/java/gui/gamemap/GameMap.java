package gui.gamemap;

import debug.Log;
import dto.Grid;
import dto.Mark;
import dto.Player;
import dto.Tile;
import javafx.application.Platform;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * GameMap.java
 * Renders the map of the game
 *
 * @author David Walter
 */
public class GameMap extends Pane {

	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private final Grid<GameTile> gameTiles;
	private final Map<Player, PlayerTile> players;

	private final Frame frame;

	public GameMap(Grid<Tile> maze) {
		gameTiles = new Grid<>(maze.getWidth(), maze.getHeight());
		players = new HashMap<>();
		frame = new Frame();

		Log.debug("Loading map");
		maze.forEach(this::load);
		Log.debug("Render map");
		gameTiles.forEach(this::render);

		this.widthProperty().addListener((observable, oldValue, newValue) -> draw());
		this.heightProperty().addListener((observable, oldValue, newValue) -> draw());

		effects(false);
	}

	/**
	 * Loads the Tile into a GameTile
	 *
	 * @param tile Tile to load
	 */
	private void load(Tile tile) {
		GameTile gameTile = new GameTile(tile);
		gameTiles.add(gameTile);
		getChildren().add(gameTile);
	}

	/**
	 * Renders the GameTiles (e.g. shadows)
	 *
	 * @param gameTile GameTile to render
	 */
	private void render(GameTile gameTile) {
		// Only render walls
		if (!gameTile.isWall()) {
			return;
		}

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
	 * Calculates the frame based on the view size
	 *
	 * @return true if layout is needed, false if not
	 */
	private boolean checkNeedsLayout() {
		double tileSizeW = this.widthProperty().doubleValue() / gameTiles.getWidth();
		double tileSizeH = this.heightProperty().doubleValue() / gameTiles.getHeight();

		if (tileSizeW == 0 || tileSizeH == 0) {
			return false;
		}

		if (tileSizeH < tileSizeW) {
			frame.setSize(tileSizeH);
			frame.setOffsetX((this.widthProperty().doubleValue() - frame.getSize() * gameTiles.getWidth()) / 2);
			frame.setOffsetY(0);
		} else {
			frame.setSize(tileSizeW);
			frame.setOffsetX(0);
			frame.setOffsetY((this.heightProperty().doubleValue() - frame.getSize() * gameTiles.getHeight()) / 2);
		}

		return true;
	}

	/**
	 * Resize and center the game map based on container size
	 */
	private void draw() {
		// Checks if the frame has changed
		if (checkNeedsLayout()) {
			Log.debug("Draw map [" + frame + "]");
			Platform.runLater(() -> {
				players.values().forEach(playerTile -> playerTile.draw(frame));
				gameTiles.forEach(gameTile -> gameTile.draw(frame));
			});
		}
	}

	/**
	 * Gets neighbouring game tiles
	 *
	 * @param gameTile  origin
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

	public void set(Player player) {
		set(player, false);
	}

	/**
	 * Moves the player to its location
	 *
	 * @param player Player to add/move on the map
	 */
	public void set(Player player, boolean isPlayer) {
		PlayerTile playerTile = players.get(player);

		if (playerTile == null) {
			Log.debug("Add new player '" + player.getName() + "' to map");
			playerTile = new PlayerTile(player, isPlayer);
			players.put(player, playerTile);
			// change was necessary because of an exception
			getChildren().add(playerTile);
			playerTile.draw(frame);
		} else {
			playerTile.move(player.getPosition());
		}
	}

	/**
	 * Adds the mark to the map
	 *
	 * @param mark Mark to add
	 */
	public void set(Mark mark) {
		gameTiles.get(mark.getPosition()).drawMark(mark);
	}

	/**
	 * Removes the mark from the map
	 *
	 * @param mark Mark to remove
	 */
	public void remove(Mark mark) {
		gameTiles.get(mark.getPosition()).drawMark(null);
	}

	/**
	 * Puts the GameMap into a playable state
	 */
	public void start() {
		this.setMouseTransparent(false);
		this.effects(true);
		gameTiles.forEach(GameTile::enableContextMenu);
	}

	/**
	 * Puts the GameMap into a non-playable state
	 */
	public void end() {
		this.setMouseTransparent(true);
		this.effects(false);
	}

	/**
	 * Adds or Removes a blur effect
	 *
	 * @param enabled true enables the effect, false disables the effect
	 */
	private void effects(boolean enabled) {
		if (enabled) {
			this.setEffect(null);
			this.setOpacity(1.0);
		} else {
			BoxBlur bb = new BoxBlur();
			bb.setWidth(11);
			bb.setHeight(11);
			bb.setIterations(7);
			this.setEffect(bb);
			this.setOpacity(0.5);
		}

	}
}
