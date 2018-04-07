package gui;

import dto.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * PlayerInfo.java
 * Manages the player info view
 *
 * @author David Walter
 */
public class PlayerInfo {

	private Node node;

	@FXML
	private Label name;

	@FXML
	private ImageView image;

	@FXML
	private CheckBox ready;

	public static PlayerInfo load() throws IOException {
		FXMLLoader loader = new FXMLLoader(PlayerInfo.class.getResource("../player.fxml"));

		Node node = loader.load();
		PlayerInfo playerInfo = loader.getController();
		playerInfo.setNode(node);

		return playerInfo;
	}

	private void setNode(Node node) {
		this.node = node;
	}

	Node getNode() {
		return node;
	}

	void setPlayer(Player player, boolean isPlayer) {
		name.setText(player.getName());
		image.setImage(Sprites.getPlayer(player.getNumber()));
		if (isPlayer) {
			node.setStyle("-fx-border-color: " + Sprites.playerColor);
		}
	}

	void setReady() {
		this.ready.setSelected(true);
	}

	public boolean isReady() {
		return ready.isSelected();
	}

	public String getName() {
		return name.getText();
	}

}
