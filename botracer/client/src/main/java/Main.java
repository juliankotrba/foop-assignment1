import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main
 *
 * @author David Walter
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(Main.class.getResource("botracer.fxml"));
		primaryStage.setTitle("Bot Racer");
		primaryStage.setScene(new Scene(root, 900, 600));
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(400);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
