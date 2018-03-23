package gui;

import javafx.scene.control.Alert;

public class Error {

	public static void show(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occurred");
		alert.setContentText(message);
		alert.show();
	}
}
