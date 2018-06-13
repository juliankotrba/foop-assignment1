package gui.gamemap;

import connection.SingletonConnectionFactory;
import debug.Log;
import dto.Mark;
import dto.MarkType;
import dto.Tile;
import dto.TileType;
import exception.service.ServiceException;
import gui.Sprites;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import service.MarkService;
import service.MarkServiceImpl;

/**
 * GameTile.java
 * Frame on the game map
 * @author David Walter
 */
public class GameTile extends StackPane {

	private static final MarkService markService = new MarkServiceImpl(SingletonConnectionFactory.getInstance());

	private final ImageView background = new ImageView();
	private final ImageView object = new ImageView();
	private final ImageView mark = new ImageView();
	private final Pane highlight = new Pane();

	private final Tile tile;

	GameTile(Tile tile) {
		this.tile = tile;

		background.setPreserveRatio(true);
		background.setSmooth(false);

		object.setPreserveRatio(true);
		object.setSmooth(false);

		mark.setPreserveRatio(true);
		mark.setSmooth(false);

		getChildren().addAll(background, object, mark, highlight);

		if (tile.getType() == TileType.DEFAULT) {
			background.setImage(Sprites.floor);
		} else if (tile.getType() == TileType.EXIT) {
			background.setImage(Sprites.exit);
		}
	}

	// MARK: - Getter

	int getX() {
		return tile.getX();
	}

	int getY() {
		return tile.getY();
	}

	// MARK: - draw

	/**
	 * Draw the Tile with the provided data from Frame
	 * @param frame Frame where this is drawn
	 */
	void draw(Frame frame) {
		double size = frame.getSize();

		setPrefSize(size, size);
		this.setTranslateX(getX() * size + frame.getOffsetX());
		this.setTranslateY(getY() * size + frame.getOffsetY());

		background.setFitWidth(size);
		object.setFitWidth(size);
		mark.setFitWidth(size);

		highlight.setPrefSize(size, size);
	}

	void drawBorder(boolean front) {
		object.setImage(front ? Sprites.border_front : Sprites.border_top);
	}

	void drawShadow(boolean front, boolean last) {
		object.setImage(front ? Sprites.shadow_front : (last ? Sprites.shadow_last : Sprites.shadow_top));
	}

	boolean isWall() {
		return tile.getType() == TileType.WALL;
	}

	// MARK: - interaction

	/**
	 * Draws a mark on the GameTile
	 * @param mark Mark to draw, removes the mark if null
	 */
	void drawMark(Mark mark) {
		if (mark == null) {
			this.mark.setImage(null);
			return;
		}

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
				this.mark.setImage(Sprites.getAlgorithm(mark.getAlgorithm()));
				break;
			case CLEAR_MEMORY:
				this.mark.setImage(Sprites.clear);
				break;
		}
	}

	/**
	 * Adds a context menu to the GameTile to enable player interaction
	 */
	void enableContextMenu() {
		if (tile.getType() != TileType.DEFAULT) { return; }

		final ContextMenu contextMenu = new ContextMenu();

		MenuItem title = new MenuItem("Place mark on: (" + tile.getX() + ", " + tile.getY() + ")");
		title.setDisable(true);
		title.getStyleClass().add("context-menu-title");

		Menu changeAlgorithm = new Menu("Change algorithm");

		String[] algorithms = {"Random movements", "Left hand rule", "Right hand rule", "Smart movements"};
		for (int i = 0; i < algorithms.length; i++) {
			MenuItem menuItem = new MenuItem(algorithms[i]);
			menuItem.setGraphic(Sprites.asImageView(Sprites.getAlgorithm(i), 16.0));
			final int algorithm = i;
			menuItem.setOnAction(event -> setMark(new Mark(algorithm, tile.getPosition())));
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
		remove.setOnAction(event -> setMark(new Mark(tile.getPosition(), MarkType.REMOVE)));

		contextMenu.getItems().addAll(title, new SeparatorMenuItem(), stay, moveAway, turn, changeAlgorithm, clear, new SeparatorMenuItem(), remove);

		// Mouse actions
		setOnMousePressed(event -> contextMenu.show(this, Side.BOTTOM, 0, 0));

		setOnMouseEntered(event -> highlight.setStyle(Sprites.highlight));
		setOnMouseExited(event -> highlight.setStyle(null));
		setOnMousePressed(event -> contextMenu.show(this, Side.BOTTOM, 0, 0));
	}

	/**
	 * Sends an add request for a mark on the GameTile's position
	 * @param mark Mark to send to the server
	 */
	private void setMark(Mark mark) {
		try {
			markService.placeMark(mark);
		} catch (ServiceException e) {
			Log.error(e.getMessage());
		}
	}
}
