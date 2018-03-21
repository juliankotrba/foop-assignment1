package gui;

import dto.Grid;
import dto.Tile;
import javafx.scene.layout.Pane;

/**
 * GameMap
 *
 * @author  David Walter
 */
class GameMap extends Pane {

	private Grid<GameTile> gameTiles;

	GameMap(Grid<Tile> maze) {
		gameTiles = new Grid<>(maze.getWidth(), maze.getHeight());

		loadMaze(maze);
		render();

		this.widthProperty().addListener((observable, oldValue, newValue) -> draw());
		this.heightProperty().addListener((observable, oldValue, newValue) -> draw());
	}

	private void loadMaze(Grid<Tile> maze) {
		maze.forEach(position -> {
			GameTile gameTile = new GameTile(position);
			gameTiles.add(gameTile);
			getChildren().add(gameTile);
		});
	}

	private void render() {
		gameTiles.forEach(gameTile -> {
			if (gameTile.isBorder()) {
				GameTile up = nextTile(gameTile, Direction.UP);
				GameTile down = nextTile(gameTile, Direction.DOWN);
				GameTile right = nextTile(gameTile, Direction.RIGHT);

				boolean front = !(down != null && down.isBorder());
				if (right != null && !right.isBorder()) {
					right.drawShadow(front, up == null || !up.isBorder());
				}
				gameTile.drawBorder(front);
			}
		});
	}

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

		gameTiles.forEach(gameTile -> gameTile.render(tileSize, offsetX, offsetY));
	}

	private GameTile nextTile(GameTile gameTile, Direction direction) {
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
}

enum Direction {
	UP, DOWN, LEFT, RIGHT
}
