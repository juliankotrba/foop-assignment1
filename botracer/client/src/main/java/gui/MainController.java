package gui;

import connection.Connection;
import connection.SingletonConnectionFactory;
import dto.GameData;
import dto.Grid;
import dto.Player;
import dto.Tile;
import exception.service.ServiceException;
import gui.GameMap.GameMap;
import gui.debug.Debug;
import gui.debug.MazeLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import log.Log;
import service.GameService;
import service.GameServiceImpl;

import java.io.IOException;
import java.util.*;

/**
 * MainController.java
 * Main controller of the JavaFX window
 * @author  David Walter
 */
public class MainController {

	private GameService gameService;
	private Player player;
	private GameMap gameMap;

	private final Map<Player, PlayerInfo> playerInfoMap = new HashMap<>();

	@FXML
	private BorderPane mainWindow;

	@FXML
	private VBox playerList;

	@FXML
	private void initialize() {
		Log.debug("initialized");
	}

	public void start(Window window) {
		Log.debug("started");
		TextInputDialog dialog = new TextInputDialog("Player");
		dialog.initOwner(window);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setGraphic(Sprites.asImageView(Sprites.icon, 32.0));
		dialog.setTitle("Bot racer");
		dialog.setHeaderText("Choose a name to join the race");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name -> connect());
	}

	private void connect() {
		try {
			Connection connection = SingletonConnectionFactory.getDummyInstance();
			gameService = new GameServiceImpl(connection);
			gameService.connect(message -> message.getPayload().ifPresent(this::loadGameData));
		} catch (ServiceException e) {
			Log.error(e.getMessage());

			gameService = null;

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Connection error");
			alert.setHeaderText("Unable to connect to server");
			alert.setContentText("The game is unable to connect to the game server.\nAre you sure it is up and running?");

			ButtonType retry = new ButtonType("Retry");
			ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(retry, exit);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == retry) {
				connect();
			} else {
				close();
			}
		}
	}

	@FXML
	private void ready() {
		if (gameService == null) { return; }

		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				throw new NullPointerException("Player info not loaded");
			}
			playerInfo.setReady(true);
			gameService.setPlayerReady(message -> Log.debug("Game started"));
		} catch (ServiceException | NullPointerException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

	@FXML
	private void close() {
		if (gameService == null) { return; }
		gameService.disconnect();
		Platform.exit();
	}

	@FXML
	private void about() {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setGraphic(Sprites.asImageView(Sprites.icon, 64.0));
		alert.setTitle("About");
		alert.setHeaderText("Bot racer");
		alert.setContentText("");

		ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(close);

		alert.showAndWait();

	}

	private void loadGameData(GameData gameData) {
		loadMap(gameData.getGameMap());
		player = gameData.getPlayer();
		Sprites.setHighlight(player.getNumber());
		loadPlayer(player);
	}

	private void loadMap(Grid<Tile> grid) {
		if (grid == null) { return; }
		gameMap = new GameMap(grid);
		mainWindow.setCenter(gameMap);
	}

	private void loadPlayer(Player player) {
		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				playerInfo = PlayerInfo.load();
				playerInfoMap.put(player, playerInfo);
				playerList.getChildren().add(playerInfo.getNode());
			}

			playerInfo.setPlayer(player);
			gameMap.set(player);
		} catch (IOException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

	// MARK: - DEBUG

	private Debug debug;

	@FXML
	private void debugPlayer() {
		Debug.player(gameMap);
	}

	@FXML
	private void debugLoadMap() {
		Grid<Tile> grid = MazeLoader.shared.load(MainController.class.getResource("../maze.txt"));
		loadMap(grid);
	}

	@FXML
	private void debugAnimation() {
		if (debug == null) {
			debug = new Debug(gameMap);
		}
		debug.addMoves();
	}

}
