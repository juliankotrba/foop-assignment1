package gui;

import connection.Connection;
import connection.ConnectionFactory;
import dto.Grid;
import dto.Tile;
import exception.ConnectionException;
import gui.debug.Mazeloader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import service.GameService;
import service.GameServiceImpl;

import java.util.Optional;

public class MainController {

	private GameService gameService;

	@FXML
	private BorderPane mainWindow;

	@FXML
	private HBox gameMapParent;

	@FXML
	public void initialize() {
		try {
			Connection connection = ConnectionFactory.getInstance();
			connection.connect();
			gameService = new GameServiceImpl(connection);
		} catch (ConnectionException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Connection error");
			alert.setHeaderText("Unable to connect to server");
			alert.setContentText("The game is unable to connect to the game server.\nAre you sure it is up and running?");

			ButtonType debug = new ButtonType("DEBUG");
			ButtonType retry = new ButtonType("Retry");
			ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(retry, debug, exit);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == retry) {
				initialize();
			} else if (result.isPresent() && result.get() == debug) {
				loadDebugMap();
			} else {
				close();
			}
		}
	}

	public void start() {
		if (gameService == null) {
			return;
		}
		gameService.startGame();
	}

	public void stop() {
		if (gameService == null) {
			return;
		}
		gameService.stopGame();
	}

	public void close() {
		Platform.exit();
	}

	private void initGameMap() {
		// TODO: load map from server
	}

	private void loadDebugMap() {
		Grid<Tile> grid = Mazeloader.shared.load(MainController.class.getResource("../maze.txt"));

		if (grid == null) {
			Platform.exit();
			return;
		}

		GameMap map = new GameMap(grid);
		mainWindow.setCenter(map);
	}
}
