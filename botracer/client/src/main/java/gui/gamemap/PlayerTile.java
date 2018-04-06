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

	private double tileSize;
	private double offsetX;
	private double offsetY;

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

	public void draw(double tileSize, double offsetX, double offsetY) {
		this.tileSize = tileSize;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		setPrefSize(tileSize, tileSize);
		player.setFitWidth(tileSize);

		double x = getX() * tileSize + offsetX;
		double y = getY() * tileSize + offsetY;

		transition.stop();

		this.setTranslateX(x);
		this.setTranslateY(y);
	}

	// MARK: - interaction

	public void move(Position position) {
		this.position = position;

		transition.setToX(getX() * tileSize + offsetX);
		transition.setToY(getY() * tileSize + offsetY);

		transition.play();
	}
}
