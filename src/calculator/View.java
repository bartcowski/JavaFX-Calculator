package calculator;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Sets up calculator GUI by loading FXML file.
 * 
 * @author Bartosz Ciuækowski
 * @version 1.0
 */

public class View {
	
		private FXMLLoader loader;
		private AnchorPane anchorPane;
		
		/**
		 * View class simple constructor
		 */
		public View() {
			
		}
		
		/**
		 * Gets loader class member
		 * 
		 * @return FXMLLoader loader
		 */
		public FXMLLoader getLoader() {
			return loader;
		}
		
		/**
		 * Initializes GUI, loads FXML file, sets up the stage and creates new scene
		 * 
		 * @param app's main stage
		 * @throws IOException when loading the file goes wrong
		 */
	 	public void initializeView(Stage stage) throws IOException {
	 		loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("calculatorGUI.fxml"));
			anchorPane = loader.load();
			
			Scene scene = new Scene(anchorPane);
			
			stage.setScene(scene);
			stage.setTitle("Calculator");
			stage.setResizable(false);
			stage.show();
	 	}
	    
	  
}
