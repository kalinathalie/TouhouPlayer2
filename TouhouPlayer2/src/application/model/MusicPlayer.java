package application.model;

import javafx.scene.control.Button;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MusicPlayer implements Initializable {
	
	@FXML
	private Button PlayBtn;
	@FXML
	private Button PauseBtn;
	
	private Player player;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void getPlay(ActionEvent event, String filename) throws JavaLayerException {
		try{
			if(filename == null) {
				filename = "01 - Mystic Dream -Snow or Cherry Petal-.mp3";
			}
			File file = new File("C:\\Users\\USER\\eclipse-workspace\\TouhouPlayer2\\media\\songs\\"+filename);
			FileInputStream fis = new FileInputStream(file);
			player = new Player(fis);
		}catch(Exception e){
			System.out.println(e);
		}
		
		new Thread() {
			public void run() {
				try {
					player.play();
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}.start();
	}
	public void getPause(ActionEvent event) throws JavaLayerException{
		try{
			this.player.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}

}
