package gui.gamemap;

import dto.Player;
import dto.Position;
import gui.Sprites;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * PlayerTile.java
 * Manages the movement of players on the map
 * @author David Walter
 */
class PlayerTile extends StackPane {

	private final TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), this);

	private final ImageView player = new ImageView();
	private Position position;

	private Frame frame = new Frame();

	PlayerTile(Player player, boolean isPlayer) {
		this.position = player.getPosition();

		if (isPlayer) {
			this.player.setEffect(new DropShadow(16, Color.valueOf(Sprites.playerColor)));
		}

		this.player.setPreserveRatio(true);
		this.player.setImage(Sprites.getPlayer(player.getNumber()));

		setMouseTransparent(true);
		getChildren().add(this.player);
	}

	private int getX() {
		return position.getWidth();
	}

	private int getY() {
		return position.getHeight();
	}

	// MARK: - draw

	/**
	 * Draw the Tile with the provided data from Frame
	 * @param frame Frame where this is drawn
	 */
	void draw(Frame frame) {
		this.frame = frame;

		double size = frame.getSize();

		setPrefSize(size, size);
		player.setFitWidth(size);

		double x = getX() * size + frame.getOffsetX();
		double y = getY() * size + frame.getOffsetY();

		transition.stop();

		this.setTranslateX(x);
		this.setTranslateY(y);
	}

	// MARK: - interaction

	/**
	 * Moves the Player to the specified position
	 * @param position Position to move to
	 */
	void move(Position position) {
		this.position = position;

		transition.setToX(getX() * frame.getSize() + frame.getOffsetX());
		transition.setToY(getY() * frame.getSize() + frame.getOffsetY());

		transition.play();
	}
}
