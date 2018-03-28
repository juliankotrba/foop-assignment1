package gui;

import dto.Player;
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

	public Node getNode() {
		return node;
	}

	public void setPlayer(Player player) {
		name.setText(player.getName());
		image.setImage(Sprites.getPlayer(player.getNumber()));
	}

	public void setReady(boolean ready) {
		this.ready.setSelected(ready);
	}

	public boolean isReady() {
		return ready.isSelected();
	}

	public String getName() {
		return name.getText();
	}

}
