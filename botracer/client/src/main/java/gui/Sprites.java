package gui;

import gui.gamemap.GameTile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Sprites.java
 * Access the sprites of the game
 *
 * @author David Walter
 */
public class Sprites {

	public static final Image icon = loadImage("botracer.png");

	public static String highlight = "-fx-background-color: rgba(0, 152, 211, 0.5)";
	public static String playerColor = "rgb(0, 152, 211)";

	static void setHighlight(int number) {
		if (number > 0 && number <= highlights.length) {
			highlight = highlights[number - 1];
			playerColor = playerColors[number - 1];
		}
	}

	// Player tile selection color; unused
	private static final String[] highlights = {
			"-fx-background-color: rgba(44, 182, 177, 0.5)", // cyan
			"-fx-background-color: rgba(222, 58, 59, 0.5)", // red
			"-fx-background-color: rgba(254, 249, 53, 0.5)", // yellow
			"-fx-background-color: rgba(81, 195, 79, 0.5)", // green
			"-fx-background-color: rgba(225, 45, 160, 0.5)", // pink
			"-fx-background-color: rgba(231, 232, 235, 0.5)", // white
			"-fx-background-color: rgba(211, 141, 94, 0.5)" // brown
	};

	private static final String[] playerColors = {
			"rgb(44, 182, 177)", // cyan
			"rgb(222, 58, 59)", // red
			"rgb(254, 249, 53)", // yellow
			"rgb(81, 195, 79)", // green
			"rgb(225, 45, 160)", // pink
			"rgb(231, 232, 235)", // white
			"rgb(211, 141, 94)" // brown
	};

	// Players
	public static Image getPlayer(int number) {
		if (number > 0 && number <= player.length) {
			return player[number - 1];
		}

		return null;
	}

	private static final Image[] player = {
			loadImage("players/bot_cyan.png"),
			loadImage("players/bot_red.png"),
			loadImage("players/bot_yellow.png"),
			loadImage("players/bot_green.png"),
			loadImage("players/bot_pink.png"),
			loadImage("players/bot_white.png"),
			loadImage("players/bot_brown.png")
	};

	// Maze
	public static final Image floor = loadImage("floor.png");
	public static final Image exit = loadImage("exit.png");

	public static final Image border_front = loadImage("border_front.png");
	public static final Image border_top = loadImage("border_top.png");

	public static final Image shadow_front = loadImage("shadow_front.png");
	public static final Image shadow_top = loadImage("shadow_top.png");
	public static final Image shadow_last = loadImage("shadow_last.png");

	// Marks
	public static final Image stay = loadImage("marks/stay.png");
	public static final Image move_away = loadImage("marks/move_away.png");

	public static Image getAlgorithm(int index) {
		if (index > 0 && index < algorithm.length) {
			return algorithm[index];
		}

		return algorithm[0];
	}

	private static final Image[] algorithm = {
			loadImage("marks/algorithms/a1.png"),
			loadImage("marks/algorithms/a2.png"),
			loadImage("marks/algorithms/a3.png"),
			loadImage("marks/algorithms/a4.png")
	};
	public static final Image left = loadImage("marks/left.png");
	public static final Image right = loadImage("marks/right.png");
	public static final Image clear = loadImage("marks/clear.png");

	private static Image loadImage(String path) {
		return new Image(GameTile.class.getResourceAsStream("/assets/" + path));
	}

	/**
	 * Wraps the specified sprite in an ImageView
	 *
	 * @param image image to display
	 * @param width width of the ImageView
	 * @return ratio preserving ImageView
	 */
	public static ImageView asImageView(Image image, double width) {
		ImageView imageView = new ImageView(image);

		imageView.setFitWidth(width);
		imageView.setSmooth(true);
		imageView.setPreserveRatio(true);

		return imageView;
	}
}
