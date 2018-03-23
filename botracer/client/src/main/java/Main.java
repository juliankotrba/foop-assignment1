import gui.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main
 *
 * @author David Walter
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("botracer.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("Bot Racer");
		primaryStage.setScene(new Scene(root, 900, 600));
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(400);

		primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> {
			MainController controller = loader.getController();
			controller.start(primaryStage.getScene().getWindow());
		});

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
