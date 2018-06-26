package application.controller;

import javafx.scene.control.Label;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private Button BtnRegister;
	
	@FXML
	private CheckBox CheckVIP;

	private BufferedReader reader;

	private BufferedWriter output;
	
	private String isVIP;

	private FileWriter newviplist;
	private FileWriter newusermusics;
	/*
	 *Register a user on database
	 */
	public void Register(ActionEvent event){
		try{
			String path = new File("").getAbsolutePath();
			reader = new BufferedReader(new FileReader(path+"/db/database.txt"));
		    String line = reader.readLine();
		    Boolean finded = false;
		    
		    while (line != null) {
		    	String[] userandpass = line.split(":");
		    	String username = userandpass[0];
		    	if(txtUsername.getText().equals(username)) {
		    		finded = true;
		    		break;
		    	}
		    	line = reader.readLine();
		    }
		    reader.close();
			if(finded){	
				lblStatus.setText("User exists");
			}else if(!(txtUsername.getText().matches("[a-zA-Z0-9]*") && txtPassword.getText().matches("[a-zA-Z0-9]*"))){
				lblStatus.setText("Use only letters and numbers");
			}
			else {
				
				isVIP = (CheckVIP.isSelected()) ? "1" : "0";
				output = new BufferedWriter(new FileWriter(path+"/db/database.txt", true));
				output.write(txtUsername.getText() + ":"+ txtPassword.getText() + ":" + isVIP);
				output.newLine();
				output.flush();
				
				if(CheckVIP.isSelected()) {
					Paths.get(path+"/db/userdata/vipPlaylists/"+txtUsername.getText());
				}
				
				newusermusics = new FileWriter(path+"/db/userdata/userMusics/"+txtUsername.getText()+".txt");
				newusermusics.write("");
				newusermusics.flush();
				newusermusics.close();
				
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
