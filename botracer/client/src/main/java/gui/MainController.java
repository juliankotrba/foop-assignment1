package gui;

import connection.Connection;
import connection.OnMessageReceivedListener;
import connection.SingletonConnectionFactory;
import debug.Debug;
import debug.Log;
import debug.MazeLoader;
import dto.Grid;
import dto.Player;
import dto.Tile;
import dto.messages.s2c.NewPlayerMessage;
import exception.service.ServiceException;
import gui.gamemap.GameMap;
import gui.receivers.MarkPlacementReceiver;
import gui.receivers.PlayersChangedReceiver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import service.GameService;
import service.GameServiceImpl;
import service.PlayerService;
import service.PlayerServiceImpl;

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
public class MainController implements OnMessageReceivedListener<NewPlayerMessage> {

	// Services
	private GameService gameService;

	// Message receivers
	private PlayersChangedReceiver playersChangedReceiver = new PlayersChangedReceiver();
	private MarkPlacementReceiver markPlacementReceiver = new MarkPlacementReceiver();

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
	 *
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

		result.ifPresent(this::connect);
	}

	/**
	 * Connect to the GameService
	 */
	private void connect(String playerName) {
		try {
			Connection connection = SingletonConnectionFactory.getInstance();
			gameService = new GameServiceImpl(connection);
			// Connect and load the received map
			gameService.connect(message -> message.getPayload().ifPresent(this::loadMap));
			// Send the chosen player name to the server
			gameService.setPlayerName(playerName);

			PlayerService playerService = new PlayerServiceImpl(connection);
			playerService.registerForPlayerUpdates(playersChangedReceiver);
			playerService.registerForNewPlayerUpdates(this);
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
				connect(playerName);
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
			gameService.setPlayerReady(message -> Log.debug("Set player ready"));
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

	/**
	 * Loads the map from the Grid to the window
	 *
	 * @param grid Grid of Tiles
	 */
	private void loadMap(Grid<Tile> grid) {
		if (grid == null) {
			return;
		}
		gameMap = new GameMap(grid);

		playersChangedReceiver.setGameMap(gameMap);
		markPlacementReceiver.setGameMap(gameMap);

		// Load the game map into the window on the FX Thread
		Platform.runLater(() -> mainWindow.setCenter(gameMap));
	}

	/**
	 * Loads the player info
	 *
	 * @param player Player to load
	 */
	private void loadPlayer(Player player) {
		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				playerInfo = PlayerInfo.load();
				playerInfoMap.put(player, playerInfo);
				playerList.getChildren().add(playerInfo.getNode());
			}

			playerInfo.setPlayer(player);
		} catch (IOException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

	@Override
	public void onMessageReceived(NewPlayerMessage message) {
		message.getPayload().ifPresent(players ->
				players.forEach(player -> {
					gameMap.set(player);
					Platform.runLater(() -> loadPlayer(player));
				}));
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
