package application.controller;

import java.io.File;

import application.model.MusicPlayer;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javazoom.jl.decoder.JavaLayerException;

public class MediaController {
	
	MusicPlayer player = new MusicPlayer();
	String filename = null;
	
	public void MusicPlay(ActionEvent event) throws JavaLayerException {
		try{
			this.player.getPlay(event, filename);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void MusicPause(ActionEvent event) throws JavaLayerException {
		try{
			this.player.getPause(event);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void ChooseFile(ActionEvent event) throws JavaLayerException{
		FileChooser fc = new FileChooser();
		fc.setTitle("Open your song");
		File file = fc.showOpenDialog(null);
		this.filename = file.getName();
	}
	
}
