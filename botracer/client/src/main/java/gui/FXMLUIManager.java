package gui;

import connection.Connection;
import connection.SingletonConnectionFactory;
import debug.Log;
import dto.GameData;
import dto.Mark;
import dto.Player;
import exception.service.ServiceException;
import gui.gamemap.GameMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Window;
import service.GameService;
import service.GameServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * FXMLUIManager.java
 * BotRacer controller of the JavaFX window
 *
 * @author David Walter
 */
public class FXMLUIManager implements UIManager {

	private static final String textStyle = "-fx-text-fill: #E7E8EB; -fx-font-size: 24pt;";

	private GameMap gameMap;
	private final Map<Player, PlayerInfo> playerInfoMap = new HashMap<>();
	private Player player;

	// Services
	private GameService gameService;

	@FXML
	private StackPane mainView;

	@FXML
	private VBox playerList;

	@FXML
	private void initialize() {
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

		if (result.isPresent()) {
			connect(result.get());
		} else {
			Platform.exit();
		}
	}

	/**
	 * Connect to the GameService
	 */
	private void connect(String playerName) {
		try {
			Connection connection = SingletonConnectionFactory.getInstance();

			gameService = new GameServiceImpl(connection);
			// Connect and load the received map
			gameService.connect(this);
			// Send the chosen player name to the server
			gameService.setPlayerName(playerName);

			Label loadingLabel = new Label("Loading...");
			loadingLabel.setStyle(textStyle);
			mainView.getChildren().add(loadingLabel);
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
			alert.getButtonTypes().add(exit);

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
			gameService.setPlayerReady();
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

	// MARK: - UIManager

	/**
	 * Loads the map from the Grid to the window
	 *
	 * @param gameData GameData
	 */
	public void load(GameData gameData) {
		gameMap = new GameMap(gameData.getGameMap());
		player = gameData.getPlayer();
		Sprites.setHighlight(player.getNumber());
		loadPlayer(player, true);

		Platform.runLater(() -> {
			mainView.getChildren().clear();
			mainView.getChildren().add(gameMap);

			Label info = new Label("Waiting for everyone to be ready...");
			info.setStyle(textStyle);
			mainView.getChildren().add(info);
		});
	}

	/**
	 * Loads players to display them in the UI
	 *
	 * @param players Players to load
	 */
	public void loadPlayers(List<Player> players) {
		players.forEach(this::loadPlayer);
	}

	/**
	 * Loads a player to display them in the UI
	 *
	 * @param player Player that is not the clients player
	 */
	private void loadPlayer(Player player) {
		loadPlayer(player, false);
	}

	/**
	 * Loads the player info
	 *
	 * @param player Player to load
	 */
	public void loadPlayer(Player player, boolean isPlayer) {
		try {
			PlayerInfo playerInfo = playerInfoMap.get(player);
			if (playerInfo == null) {
				playerInfo = PlayerInfo.load();
				playerInfoMap.put(player, playerInfo);
				Node node = playerInfo.getNode();
				Platform.runLater(() -> playerList.getChildren().add(node));
			}

			final PlayerInfo info = playerInfo;
			Platform.runLater(() -> {
				info.setPlayer(player, isPlayer);
				gameMap.set(player, isPlayer);
			});
		} catch (IOException e) {
			Log.error(e.getMessage());
			Error.show(e.getMessage());
		}
	}

	/**
	 * Marks the player as ready
	 *
	 * @param player Player to be marked as ready
	 */
	public void setReady(Player player) {
		PlayerInfo playerInfo = playerInfoMap.get(player);
		if (playerInfo != null) {
			playerInfo.setReady();
		}
	}

	/**
	 * The game has started
	 */
	public void startGame() {
		if (gameMap == null) {
			return;
		}
		// Removes the "Waiting for other Players" hint
		Platform.runLater(() -> mainView.getChildren().remove(mainView.getChildren().size() - 1));
		gameMap.start();
	}

	/**
	 * The game has ended
	 *
	 * @param winners List of winners
	 */
	public void endGame(List<Player> winners) {
		gameMap.end();

		Label winnersLabel = new Label();
		winnersLabel.setTextAlignment(TextAlignment.CENTER);
		winnersLabel.setStyle(textStyle);

		boolean playerIsWinner = winners.contains(player);
		String gameOver = playerIsWinner ? "You win!" : "Game Over";

		if (winners.size() == 1) {
			if (playerIsWinner) {
				winnersLabel.setText(gameOver);
			} else {
				winnersLabel.setText(gameOver + "\n\n'" + winners.get(0).getName() + "'\n\nhas won the game");
			}
		} else {
			StringBuilder builder = new StringBuilder(gameOver);
			winners.forEach(player -> builder.append("\n\n'").append(player.getName()).append("'\n"));
			builder.append("\nhave won the game");
			winnersLabel.setText(builder.toString());
		}

		Platform.runLater(() -> mainView.getChildren().add(winnersLabel));
	}

	/**
	 * Adds players to the GameMap
	 *
	 * @param players List of Players
	 */
	public void set(List<Player> players) {
		players.forEach(this::set);
	}

	/**
	 * Adds a player to the GameMap
	 *
	 * @param player Player to add
	 */
	public void set(Player player) {
		if (gameMap == null) {
			return;
		}
		gameMap.set(player);
	}

	/**
	 * Adds a Mark to the GameMap
	 *
	 * @param mark Mark to add
	 */
	public void set(Mark mark) {
		if (gameMap == null) {
			return;
		}
		gameMap.set(mark);
	}

	/**
	 * Removes a Mark to the GameMap
	 *
	 * @param mark Mark to remove
	 */
	public void remove(Mark mark) {
		if (gameMap == null) {
			return;
		}
		gameMap.remove(mark);
	}

}
