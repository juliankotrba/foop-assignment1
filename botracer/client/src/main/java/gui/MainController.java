package gui;

import connection.Connection;
import connection.SingletonConnectionFactory;
import debug.Debug;
import debug.Log;
import debug.MazeLoader;
import dto.GameData;
import dto.Grid;
import dto.Player;
import dto.Tile;
import exception.service.ServiceException;
import gui.GameMap.GameMap;
import gui.GameMap.receivers.MarkPlacementReceiver;
import gui.GameMap.receivers.NewPlayerReceiver;
import gui.GameMap.receivers.PlayersChangedReceiver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import service.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * MainController.java
 * BotRacer controller of the JavaFX window
 *
 * @author David Walter
 */
public class MainController {

	// Services
	private GameService gameService;

	// Message receivers
	private PlayersChangedReceiver playersChangedReceiver = new PlayersChangedReceiver();
	private MarkPlacementReceiver markPlacementReceiver = new MarkPlacementReceiver();
	private NewPlayerReceiver newPlayerReceiver = new NewPlayerReceiver();

	// Player and map info
	private Player player;
	private GameMap gameMap;

	private final Map<Player, PlayerInfo> playerInfoMap = new HashMap<>();

	@FXML
	private BorderPane mainWindow;

	@FXML
	private VBox playerList;

	@FXML
	private void initialize() {
		debugMenu.setVisible(Debug.DEBUG);
		Log.debug("initialized");
	}

	/**
	 * Starting dialog
	 * @param window Dialog owner
	 */
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

		result.ifPresent(this::connect); // needed for the server to get the name of the player
	}

	/**
	 * Connect to the GameService
	 */
	private void connect(String playerName) {
		try {
			Connection connection = SingletonConnectionFactory.getInstance(); // dummy connection removed
			gameService = new GameServiceImpl(connection);
			gameService.connect(message -> message.getPayload().ifPresent(this::loadMap)); // explanation on line 168
			gameService.setPlayerName(playerName); // needed for the server to get the name of the player

			PlayerService playerService = new PlayerServiceImpl(connection);
            playerService.registerForPlayerUpdates(playersChangedReceiver);
            playerService.registerForNewPlayerUpdates(newPlayerReceiver); // needed because players list should not be updated in the ui every time the bots move, therefore: separate listeners for NewPlayerConnected and PlayersMove

			MarkService markService = new MarkServiceImpl(connection);
            // markService.registerForMarkUpdates(markPlacementReceiver);
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
				connect(""); // needed because of method change
			} else {
				close();
			}
		}
	}

	@FXML
	private void ready() {
		if (gameService == null) {
			return;
		}

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
		if (gameService != null) {
			gameService.disconnect();
		}
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

	// no longer needed, because the map and the player list needed to be send in separate messages (NewPlayerMessage and GameDataMessage (holds ony map))
	/**
	 * Loads the game board received from the GameService
	 * @param gameBoard game board map
	 */
	/*private void loadGameData(Grid<Tile> gameBoard) {
		loadMap(gameBoard);
		//player = gameData.getPlayer();
		//Sprites.setHighlight(player.getNumber());
		//loadPlayer(player);
		//gameMap.set(player);
	}*/

	/**
	 * Loads the map from the Grid to the window
	 * @param grid Grid of Tiles
	 */
	private void loadMap(Grid<Tile> grid) {
		if (grid == null) {
			return;
		}
		gameMap = new GameMap(grid);

		playersChangedReceiver.setGameMap(gameMap);
		markPlacementReceiver.setGameMap(gameMap);
		newPlayerReceiver.setGameMap(gameMap); // needed because players list should not be updated in the ui every time the bots move, therefore: separate listeners for NewPlayerConnected and PlayersMove

		Platform.runLater(() -> mainWindow.setCenter(gameMap)); // needed because of thrown exception
	}

	/**
	 * Loads the player info
	 * @param player Player to load
	 */
	private void loadPlayer(Player player) {
		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				playerInfo = PlayerInfo.load();
				playerInfoMap.put(player, playerInfo);
				// needed because of thrown exception
				PlayerInfo finalPlayerInfo = playerInfo;
				Platform.runLater(() -> playerList.getChildren().add(finalPlayerInfo.getNode()));
			}

			playerInfo.setPlayer(player);
		} catch (IOException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

	// MARK: - DEBUG

	@FXML
	private Menu debugMenu;

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
