package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

	private BufferedReader readerdb;
	private PrintWriter writertemp;
	private String[] userdata;
	private String username;
	private String password;
	private String isVIP;
	/*
	 * Launch the login screen
	 * 
	 * @param event local event action
	 */
	public void Login(ActionEvent event){
		try{
			String path = new File("").getAbsolutePath();
			readerdb = new BufferedReader(new FileReader(path+"/db/database.txt"));
		    String line = readerdb.readLine();
		    Boolean finded = false;
		    
		    while (line != null) {
		    	userdata = line.split(":");
		    	username = userdata[0];
		    	password = userdata[1];
		    	isVIP = userdata[2];
		    	if(txtUsername.getText().equals(username) && txtPassword.getText().equals(password)) {
		    		finded = true;
		    		break;
		    	}
		    	line = readerdb.readLine();
		    }
		    readerdb.close();
			if(finded){	
				lblStatus.setText("Login Sucess");
				
				writertemp = new PrintWriter(path+"/temp/templogin.txt");
				writertemp.print(username+":"+isVIP);
				writertemp.close();
				
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("TouhouPlayer2");
				primaryStage.setOnCloseRequest((WindowEvent we) -> System.exit(0));
				primaryStage.show();
				
			}else{
				lblStatus.setText("Login Failed");
				lblStatus.setStyle("-fx-text-fill: #FF0000;");
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/*
	 * Register a user
	 * @param event local action event
	 * 
	 */
	public void SignUp(ActionEvent event){
		try{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/SignUp.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("TP2 SignUP");
			primaryStage.show();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}