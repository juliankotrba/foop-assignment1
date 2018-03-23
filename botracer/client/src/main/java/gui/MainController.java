package gui;

import connection.Connection;
import connection.ConnectionFactory;
import dto.Grid;
import dto.Player;
import dto.Position;
import dto.Tile;
import exception.ConnectionException;
import gui.debug.DebugMoves;
import gui.debug.MazeLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import service.GameService;
import service.GameServiceImpl;

import java.util.*;

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
	public void initialize() {

	}

	private void connect() {
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
			alert.getButtonTypes().addAll(retry, exit);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == retry) {
				initialize();
			} else if (result.isPresent() && result.get() == debug) {
				debugLoadMap();
			} else {
				close();
			}
		}
	}

	public void start() {
		if (gameService == null) {
			return;
		}
		// TODO: start GameService
		connect();
	}

	public void stop() {
		if (gameService == null) {
			return;
		}
		// TODO: stop GameService
	}

	public void close() {
		Platform.exit();
	}

	public void about() {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("About");
		alert.setHeaderText("Bot racer");
		alert.setContentText("");

		ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(close);

		alert.showAndWait();

	}

	// MARK: - DEBUG

	@FXML
	private void debugPlayer() {
		Dialog<Pair<Integer, Position>> dialog = new Dialog<>();
		dialog.setTitle("Move Player");
		dialog.setHeaderText("Move Player to position");

		dialog.setGraphic(Sprites.asImageView(Sprites.player[0], 32.0));
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

		if (grid == null) {
			Platform.exit();
			return;
		}

		gameMap = new GameMap(grid);
		mainWindow.setCenter(gameMap);

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(0, new Position(1,1)));
		playerList.add(new Player(1, new Position(13,10)));
		playerList.add(new Player(2, new Position(7,8)));
		playerList.add(new Player(3, new Position(1,9)));
		playerList.forEach(player -> gameMap.set(player));
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
