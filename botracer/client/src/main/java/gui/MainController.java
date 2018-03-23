package gui;

import connection.Connection;
import connection.SingletonConnectionFactory;
import dto.*;
import exception.service.ServiceException;
import gui.GameMap.GameMap;
import gui.debug.DebugMoves;
import gui.debug.MazeLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Pair;
import service.GameService;
import service.GameServiceImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * MainController
 *
 * @author  David Walter
 */
public class MainController {

	private GameService gameService;
	private GameMap gameMap;

	@FXML
	private BorderPane mainWindow;

	@FXML
	private VBox players;

	@FXML
	private void initialize() {

	}

	public void start(Window window) {
		TextInputDialog dialog = new TextInputDialog("Player");
		dialog.initOwner(window);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setGraphic(Sprites.asImageView(Sprites.getPlayer(0), 32.0));
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
			gameService.setPlayerReady(null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void stop() {
		if (gameService == null) { return; }
		gameService.disconnect();
	}

	@FXML
	private void close() {
		Platform.exit();
	}

	@FXML
	private void about() {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("About");
		alert.setHeaderText("Bot racer");
		alert.setContentText("");

		ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(close);

		alert.showAndWait();

	}

	private void loadGameData(GameData gameData) {
		loadMap(gameData.getGameMap());
		Player player = gameData.getPlayer();
		Sprites.setHighlight(player.getNumber());
		loadPlayer(gameData.getPlayer());
	}

	private void loadMap(Grid<Tile> grid) {
		if (grid == null) { return; }
		gameMap = new GameMap(grid);
		mainWindow.setCenter(gameMap);
	}

	private void loadPlayer(Player player) {
		try {
			HBox playerBox = FXMLLoader.load(MainController.class.getResource("../player.fxml"));

			players.getChildren().add(playerBox);

			ImageView playerImage = (ImageView)playerBox.getChildren().get(0);
			Label playerName = (Label)playerBox.getChildren().get(1);

			playerName.setText(player.getName());
			playerImage.setImage(Sprites.getPlayer(player.getNumber()));

			gameMap.set(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// MARK: - DEBUG

	@FXML
	private void debugPlayer() {
		Dialog<Pair<Integer, Position>> dialog = new Dialog<>();
		dialog.setTitle("Move Player");
		dialog.setHeaderText("Move Player to position");

		dialog.setGraphic(Sprites.asImageView(Sprites.getPlayer(0), 32.0));
		ButtonType moveType = new ButtonType("Move", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(moveType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<Integer> player = new ComboBox<>();
		player.getItems().addAll(0,1,2,3);
		player.getSelectionModel().select(0);
		Spinner<Integer> x = new Spinner<>(1, Integer.MAX_VALUE, 1, 1);
		Spinner<Integer> y = new Spinner<>(1, Integer.MAX_VALUE, 1, 1);
		y.setEditable(true);

		grid.add(new Label("Player:"), 0, 0);
		grid.add(player, 1, 0);
		grid.add(new Label("Position:"), 0, 1);
		grid.add(x, 1, 1);
		grid.add(y, 2, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == moveType) {
				return new Pair<>(player.getSelectionModel().getSelectedItem(), new Position(x.getValue(), y.getValue()));
			}
			return null;
		});

		Optional<Pair<Integer, Position>> result = dialog.showAndWait();
		result.ifPresent(value -> gameMap.set(new Player(value.getKey(), value.getValue())));
	}

	@FXML
	private void debugLoadMap() {
		Grid<Tile> grid = MazeLoader.shared.load(MainController.class.getResource("../maze.txt"));
		loadMap(grid);
	}

	private Timer timer;
	private DebugMoves debugMoves = new DebugMoves(0);

	@FXML
	private void debugAnimation() {
		debugMoves.addMoves();

		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					Player player = debugMoves.poll();
					if (player != null) {
						gameMap.set(player);
					}
				}
			}, 0, 600);
		}
	}

}
