package gui;

import dto.Mark;
import dto.Tile;
import dto.TileType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * GameTile
 *
 * @author  David Walter
 */
public class GameTile extends StackPane {

	private static Image floor = loadImage("floor.png");
	private static Image exit = loadImage("exit.png");
	private static Image border_front = loadImage("border_front.png");
	private static Image border_top = loadImage("border_top.png");
	private static Image shadow_front = loadImage("shadow_front.png");
	private static Image shadow_top = loadImage("shadow_top.png");
	private static Image shadow_last = loadImage("shadow_last.png");

	private Tile tile;

	private ImageView background = new ImageView();
	private ImageView object = new ImageView();
	private ImageView mark = new ImageView();
	private ImageView player = new ImageView();

	GameTile(Tile position) {
		this.tile = position;

		if (position.getType() == TileType.DEFAULT) {
			background.setImage(floor);
		} else if (position.getType() == TileType.EXIT) {
			background.setImage(exit);
		}

		//background.setCache(true);
		background.setPreserveRatio(true);
		background.setSmooth(true);

		//object.setCache(true);
		object.setPreserveRatio(true);
		object.setSmooth(true);

		//mark.setCache(true);
		mark.setPreserveRatio(true);
		mark.setSmooth(true);

		//player.setCache(true);
		player.setPreserveRatio(true);
		player.setSmooth(true);
		
		getChildren().addAll(background, object, mark, player);
	}

	public void render(Double tileSize, double offsetX, double offsetY) {
		this.setPrefWidth(tileSize);
		this.setPrefHeight(tileSize);
		this.setTranslateX(getX() * tileSize + offsetX);
		this.setTranslateY(getY() * tileSize + offsetY);

		background.setFitWidth(tileSize);
		background.setFitHeight(tileSize);

		object.setFitWidth(tileSize);
		object.setFitHeight(tileSize);

		mark.setFitWidth(tileSize);
		mark.setFitHeight(tileSize);

		player.setFitWidth(tileSize);
		player.setFitHeight(tileSize);
	}

	public int getX() {
		return tile.getX();
	}

	public int getY() {
		return tile.getY();
	}

	public void setMark(Mark mark) {
		// TODO: set mark image based on Mark
	}

	public void clearMark() {
		mark.setImage(null);
	}

	public void setPlayer() {
		// TODO: set player image based on Player
	}

	public void clearPlayer() {
		player.setImage(null);
	}

	public boolean isBorder() {
		return tile.getType() == TileType.WALL;
	}

	public void drawBorder(boolean front) {
		object.setImage(front ? border_front : border_top);
	}

	public void drawShadow(boolean front, boolean last) {
		object.setImage(front ? shadow_front : (last ? shadow_last : shadow_top));
	}

	private static Image loadImage(String path) {
		return new Image(GameTile.class.getResourceAsStream("/assets/" + path));
	}
}
