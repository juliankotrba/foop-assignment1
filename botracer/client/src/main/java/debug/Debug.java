package debug;

import dto.Player;
import dto.Position;
import gui.gamemap.GameMap;
import gui.Sprites;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Debug {

	//public static final boolean DEBUG = true;
	//public static final boolean VERBOSE = false;

	public static void player(GameMap gameMap) {
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

	private Timer timer;
	private DebugMoves debugMoves = new DebugMoves(0);

	public Debug(GameMap gameMap) {
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

	public void addMoves() {
		debugMoves.addMoves();
	}
}
