package gui;

import dto.Player;
import dto.Position;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayerTile extends Pane {

	private TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), this);

	private ImageView player = new ImageView();
	private Position position;

	private double tileSize;
	private double offsetX;
	private double offsetY;

	PlayerTile(Player player) {
		this.position = player.getPosition();
		this.player.setPreserveRatio(true);
		this.player.setImage(Sprites.player[player.getNumber()]);

		setMouseTransparent(true);
		getChildren().add(this.player);
	}

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

	public void move(Position position) {
		//transition.stop();
		//transition.setFromX(getX() * tileSize + offsetX);
		//transition.setFromY(getY() * tileSize + offsetY);

		this.position = position;

		transition.setToX(getX() * tileSize + offsetX);
		transition.setToY(getY() * tileSize + offsetY);

		transition.play();
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}
}
