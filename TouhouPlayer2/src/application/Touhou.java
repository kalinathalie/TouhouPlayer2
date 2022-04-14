package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;

/**
* <h1>TouhouMediaPlayer2</h1>
* This project is just a media player with Touhou musics/design
*
* @author  Kali Nathalie
* @version 2.0
* @since   2018-06-16
*/

public class Touhou extends Application {
	/**
	 * This is the start method that initiates the application
	 * @param primeryStage start the application with login screen
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("TouhouPlayer2 Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
