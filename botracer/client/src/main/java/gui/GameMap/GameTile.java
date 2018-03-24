package gui.GameMap;

import dto.Mark;
import dto.MarkType;
import dto.Tile;
import dto.TileType;
import gui.Sprites;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import log.Log;

/**
 * GameTile
 *
 * @author David Walter
 */
public class GameTile extends StackPane {

	private final Tile tile;

	private final ImageView background = new ImageView();
	private final ImageView object = new ImageView();
	private final ImageView mark = new ImageView();
	private final Pane highlight = new Pane();

	GameTile(Tile tile) {
		this.tile = tile;

		background.setPreserveRatio(true);
		background.setSmooth(false);

		object.setPreserveRatio(true);
		object.setSmooth(false);

		mark.setPreserveRatio(true);
		mark.setSmooth(false);

		//player.setPreserveRatio(true);
		//player.setSmooth(false);

		getChildren().addAll(background, object, mark, highlight);

		if (tile.getType() == TileType.DEFAULT) {
			background.setImage(Sprites.floor);

			// TODO: Send places mark to server via some service
			final ContextMenu contextMenu = new ContextMenu();

			MenuItem title = new MenuItem("Place mark on: (" + tile.getX() + ", " + tile.getY() + ")");
			title.setDisable(true);
			title.getStyleClass().add("context-menu-title");

			Menu changeAlgorithm = new Menu("Change algorithm");

			String[] algorithms = {"Left hand rule", "Right hand rule"};
			for (int i = 0; i < algorithms.length; i++) {
				MenuItem menuItem = new MenuItem(algorithms[i]);
				menuItem.setGraphic(Sprites.asImageView(Sprites.getAlgorithm(i), 16.0));
				final int number = i;
				menuItem.setOnAction(event -> setMark(new Mark(number, tile.getPosition())));
				changeAlgorithm.getItems().add(menuItem);
			}

			MenuItem stay = new MenuItem("Stay in area");
			stay.setGraphic(Sprites.asImageView(Sprites.stay, 16.0));
			stay.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.STAY_IN_AREA)));
			MenuItem moveAway = new MenuItem("Move away");
			moveAway.setGraphic(Sprites.asImageView(Sprites.move_away, 16.0));
			moveAway.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.MOVE_AWAY_FROM_AREA)));

			Menu turn = new Menu("Turn");
			MenuItem turnLeft = new MenuItem("Left");
			turnLeft.setGraphic(Sprites.asImageView(Sprites.left, 16.0));
			turnLeft.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.TURN_LEFT)));
			MenuItem turnRight = new MenuItem("Right");
			turnRight.setGraphic(Sprites.asImageView(Sprites.right, 16.0));
			turnRight.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.TURN_RIGHT)));
			turn.getItems().addAll(turnLeft, turnRight);

			MenuItem clear = new MenuItem("Clear memory");
			clear.setGraphic(Sprites.asImageView(Sprites.clear, 16.0));
			clear.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.CLEAR_MEMORY)));

			MenuItem remove = new MenuItem("Remove Mark");
			remove.setOnAction(event -> {
				Log.debug("Remove Mark");
				clearMark();
			});

			contextMenu.getItems().addAll(title, new SeparatorMenuItem(), stay, moveAway, turn, changeAlgorithm, clear, new SeparatorMenuItem(), remove);

			// Mouse actions
			setOnMousePressed(event -> contextMenu.show(this, Side.BOTTOM, 0, 0));

			// TODO: find better way to highlight tile
			setOnMouseEntered(event -> highlight.setStyle(Sprites.highlight));
			setOnMouseExited(event -> highlight.setStyle(null));
		} else if (tile.getType() == TileType.EXIT) {
			background.setImage(Sprites.exit);
		}
	}

	public void draw(double tileSize, double offsetX, double offsetY) {
		setPrefSize(tileSize, tileSize);
		this.setTranslateX(getX() * tileSize + offsetX);
		this.setTranslateY(getY() * tileSize + offsetY);

		background.setFitWidth(tileSize);
		object.setFitWidth(tileSize);
		mark.setFitWidth(tileSize);
		// player.setFitWidth(tileSize);

		highlight.setPrefSize(tileSize, tileSize);
	}

	public int getX() {
		return tile.getX();
	}

	public int getY() {
		return tile.getY();
	}

	public void setMark(Mark mark) {
		switch (mark.getMarkType()) {
			case STAY_IN_AREA:
				this.mark.setImage(Sprites.stay);
				break;
			case MOVE_AWAY_FROM_AREA:
				this.mark.setImage(Sprites.move_away);
				break;
			case TURN_LEFT:
				this.mark.setImage(Sprites.left);
				break;
			case TURN_RIGHT:
				this.mark.setImage(Sprites.right);
				break;
			case CHANGE_ALGORITHM:
				this.mark.setImage(Sprites.getAlgorithm(mark.getIndex()));
				break;
			case CLEAR_MEMORY:
				this.mark.setImage(Sprites.clear);
				break;
		}
	}

	public void clearMark() {
		mark.setImage(null);
	}
	/*
	public void setPlayer(int index) {
		player.setImage(Sprites.player[index]);
	}

	public void clearPlayer() {
		player.setImage(null);
	}
	*/
	public boolean isWall() {
		return tile.getType() == TileType.WALL;
	}

	public void drawBorder(boolean front) {
		object.setImage(front ? Sprites.border_front : Sprites.border_top);
	}

	public void drawShadow(boolean front, boolean last) {
		object.setImage(front ? Sprites.shadow_front : (last ? Sprites.shadow_last : Sprites.shadow_top));
	}
}
