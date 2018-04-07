package gui;

import javafx.scene.control.Alert;

class Error {

	/**
	 * Displays an Error dialog with a message
	 *
	 * @param message Message to display
	 */
	static void show(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occurred");
		alert.setContentText(message);
		alert.show();
	}

}
