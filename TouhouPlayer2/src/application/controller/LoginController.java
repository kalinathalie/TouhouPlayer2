package application.controller;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class LoginController {
	@FXML
	private Label lblStatus;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	@FXML
	private Button BtnLogin;
	
	@FXML
	private Button BtnSignUp;

	private BufferedReader reader;
	
	public void Login(ActionEvent event) throws Exception{
		String path = new File("").getAbsolutePath();
		reader = new BufferedReader(new FileReader(path+"/db/database.txt"));
	    String line = reader.readLine();
	    Boolean finded = false;
	    
	    while (line != null) {
	    	String[] userandpass = line.split(":");
	    	String username = userandpass[0];
	    	String password = userandpass[1];
	    	if(txtUsername.getText().equals(username) && txtPassword.getText().equals(password)) {
	    		finded = true;
	    		break;
	    	}
	    	line = reader.readLine();
	    }
		if(finded){	
			lblStatus.setText("Login Sucess");
			Thread.sleep(2000);
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("TouhouPlayer2");
			primaryStage.show();
		}else{
			lblStatus.setText("Login Failed");
			lblStatus.setStyle("-fx-text-fill: #FF0000;");
		}
	}
	public void SignUp(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/SignUp.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TP2 SignUP");
		primaryStage.show();
	}
}