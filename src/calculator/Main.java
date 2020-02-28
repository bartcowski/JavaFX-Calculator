package calculator;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Calculator Application
 * 
 * @author Bartosz Ciuækowski
 * @version 1.0
 */

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		View view = new View();
		view.initializeView(primaryStage);
		Controller controller = view.getLoader().getController();
		controller.setButtons();
	}

}
