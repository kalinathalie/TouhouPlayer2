package application.controller;
import application.model.MusicPlayer;
import application.model.Music;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MediaController{
	
	private MusicPlayer player = new MusicPlayer();
	private String selectedMusic = null;
	private String selectedPlaylist = null;
	private String selectedLoc = null;
	
	private String username;
	
	private BufferedReader reader;
	private String[] userdata;
	
	@FXML
	private Label lblUsername;	
	@FXML
	private Label lblVIP;
	
	@FXML
	private ListView<String> lstViewMusics;
	@FXML
	private ListView<String> lstViewPlaylist;
	@FXML
	private ListView<String> lstViewPlaylists;
	
	private List<Music> lstMusics = new ArrayList<>();
	private List<String> lstPlaylists = new ArrayList<>();
	private List<Music> lstPlaylist = new ArrayList<>();
	
	
	
	private JFileChooser PathChooser;
	private JFileChooser MusicChooser;
	private File addedPath;
	private File apPath;
	private File apMusic;
	private File[] allMusics;
	private BufferedWriter writeMusic;
	
	/*
	 * Initialize the media player with the respective user settings
	 */
	@FXML
    public void initialize() {
		try{
			String path = new File("").getAbsolutePath();
			reader = new BufferedReader(new FileReader(path+"/temp/templogin.txt"));
		    userdata = reader.readLine().split(":");
		    username = userdata[0];
		    lblUsername.setText(username);
		    String checkVIP = (userdata[1].equals("0")) ? "normal account" : "VIP account";
		    lblVIP.setText(checkVIP);
		    LoadMusic(null);
		    if(checkVIP.split(" ")[0].equals("VIP")) {
		    	LoadPlaylists(null);
		    }
		}catch(Exception e) {
			System.out.println(e);
		}
    }
	/*
	 * Method to Play Button, create a player if not playing, else resume the song
	 */
	public void MusicPlay(){
		try{
			selectedMusic = lstViewMusics.getSelectionModel().getSelectedItem();
			if(!player.isPlaying() && selectedMusic != null) {
				if(!player.isPaused()) {
					for (Music runM : lstMusics){
						if(selectedMusic.equals(runM.getName())) {
							selectedLoc = runM.getLoc();
						}
					}
					String path = new File("").getAbsolutePath();
					FileInputStream streamLoc = new FileInputStream(path+"/../"+selectedLoc);
					
					player = new MusicPlayer(streamLoc);
					this.player.getPlay();
				}else {
					this.player.getPlay();
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/*
	 * Method to Stop Button, the player is deleted
	 */
	public void MusicStop(){
		try{
			if(player.isPlaying()){
				this.player.getStop();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/*
	 * Method to Pause Button, the player is paused
	 */
	public void MusicPause(){
		try{
			if(player.isPlaying()){
				this.player.getPause();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/*
	 * Method to Add Path Button, the user select a path to add all this songs to musics list
	 */
	public void AddPath(ActionEvent event){
		try {
			apPath = new File(new File("/media/songs/").getCanonicalPath());
			PathChooser = new JFileChooser();
			PathChooser.setCurrentDirectory(apPath);
			PathChooser.setDialogTitle("Select a Path");
			PathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			PathChooser.setAcceptAllFileFilterUsed(false);
			if (PathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				addedPath = PathChooser.getSelectedFile();
				allMusics = addedPath.listFiles();
				for (File file : allMusics) {
				    if (file.isFile()) {
				    	String path = new File("").getAbsolutePath();
				    	String userpath = new File(path+"/db/userdata/userMusics").getAbsolutePath();
				    	reader = new BufferedReader(new FileReader(userpath+"/"+username+".txt"));
					    String line = reader.readLine();
					    Boolean finded = false;
					    
					    while (line != null) {
					    	String musicName = line.split(":::")[0];
					    	if(file.getName().contains(musicName)) {
					    		System.out.println("the File "+file.getName()+" Exists! | "+musicName);
					    		finded = true;
					    		break;
					    	}
					    	line = reader.readLine();
					    }
					    reader.close();
					    if(!finded) {
					    	System.out.println("Add: "+ file.getName());
					    	
					    	writeMusic = new BufferedWriter(new FileWriter(userpath+"/"+username+".txt", true));
					    	String NameToWrite = file.getName().substring(0, file.getName().length() - 4);
					    	String pathToWrite = file.getPath().split("TouhouPlayer2")[2];
					    	writeMusic.write(NameToWrite+":::"+pathToWrite);
					    	writeMusic.newLine();
					    	writeMusic.flush();
					    	writeMusic.close();
					    }
				    }
				}
				lstViewMusics.getItems().clear();
				LoadMusic(event);
			}else{
				System.out.println("No path selected");
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/*
	 * Method to Add Music Button, the user select a song to add on musics list
	 */
	public void AddMusic(ActionEvent event){
		try {
			apMusic = new File(new File("/media/songs/").getCanonicalPath());
			MusicChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "MP3 Files", "mp3");
			MusicChooser.setFileFilter(filter);
			MusicChooser.setCurrentDirectory(apMusic);
			MusicChooser.setDialogTitle("Select a Music");
			MusicChooser.setAcceptAllFileFilterUsed(false);
			if (MusicChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File addedFile = MusicChooser.getSelectedFile();
				String path = new File("").getAbsolutePath();
			    String userpath = new File(path+"/db/userdata/userMusics").getAbsolutePath();
			    reader = new BufferedReader(new FileReader(userpath+"/"+username+".txt"));
				String line = reader.readLine();
				Boolean finded = false;
				    
				while (line != null) {
					String musicName = line.split(":::")[0];
				    if(addedFile.getName().contains(musicName)) {
				    	System.out.println("the File "+addedFile.getName()+" Exists! | "+musicName);
				    	finded = true;
				    	break;
				    }
				    line = reader.readLine();
				}
				if(!finded) {
				   	System.out.println("Add: "+ addedFile.getName());
				    	
				   	writeMusic = new BufferedWriter(new FileWriter(userpath+"/"+username+".txt", true));
				   	String NameToWrite = addedFile.getName().substring(0, addedFile.getName().length() - 4);
				   	String pathToWrite = addedFile.getPath().split("TouhouPlayer2")[2];
				   	writeMusic.write(NameToWrite+":::"+pathToWrite);
				   	writeMusic.newLine();
				   	writeMusic.flush();
				   	writeMusic.close();
				}
				reader.close();
				lstViewMusics.getItems().clear();
				LoadMusic(event);
			}else{
				System.out.println("No music selected");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/*
	 * Method to Remove Music Button, remove a selected song on musics list 
	 */
	public void RemoveMusic(ActionEvent event){
		try{
			selectedMusic = lstViewMusics.getSelectionModel().getSelectedItem();
			if(selectedMusic != null){
				String path = new File("").getAbsolutePath();
				String LoadUserpath = new File(path+"/db/userdata/userMusics").getAbsolutePath();
				FileWriter tempFile = new FileWriter(LoadUserpath+"/temp.txt");
				FileReader localFile = new FileReader(LoadUserpath+"/"+username+".txt");
				BufferedReader readerRemoval = new BufferedReader(localFile);
				BufferedWriter writerRemoval = new BufferedWriter(tempFile);
				String currentLine = readerRemoval.readLine();
				while(currentLine != null) {
					if(currentLine.split(":::")[0].equals(selectedMusic)){
						currentLine = readerRemoval.readLine();
						continue;
					}
					writerRemoval.write(currentLine + System.getProperty("line.separator"));
				    currentLine = readerRemoval.readLine();
				}
				writerRemoval.close(); 
				readerRemoval.close();
				tempFile.close();
				localFile.close();
				File renameFile = new File(LoadUserpath+"/temp.txt");
				File deleteFile = new File(LoadUserpath+"/"+username+".txt");
				deleteFile.delete();
				renameFile.renameTo(deleteFile);
				lstViewMusics.getItems().clear();
				LoadMusic(event);
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/*
	 * Method to load all the songs on musics lists
	 */
	public void LoadMusic(ActionEvent event){
		try {
			String path = new File("").getAbsolutePath();
			String LoadUserpath = new File(path+"/db/userdata/userMusics").getAbsolutePath();
	    	reader = new BufferedReader(new FileReader(LoadUserpath+"/"+username+".txt"));
	    	String line = reader.readLine();
	    	
	    	while (line != null) {
		    	String[] musicData = line.split(":::");
		    	Music LoadNewMusic = new Music(musicData[0], musicData[1]);
		    	lstMusics.add(LoadNewMusic);
		    	
		    	lstViewMusics.getItems().add(musicData[0]);
		    	line = reader.readLine();
		    }
	    	reader.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void AddPlaylist(ActionEvent event){
		
	}
	/*
	 * Method to Load the playlists list
	 */
	public void LoadPlaylists(ActionEvent event){
		try {
			String path = new File("").getAbsolutePath();
			String PlaylistPath = new File(path+"/db/userdata/vipPlaylists/"+username).getAbsolutePath();
			File LoadPlaylists = new File(PlaylistPath);
			File[] playlistList = LoadPlaylists.listFiles();
			for(File f : playlistList) {
				System.out.println(f.getCanonicalPath());
				lstPlaylists.add(f.getName());
		    	
		    	lstViewPlaylists.getItems().add(f.getName());
		    }
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/*
	 * Method to load all the songs on selected playlist
	 */
	public void LoadPlaylist(ActionEvent event){
		try {
			selectedPlaylist = lstViewPlaylists.getSelectionModel().getSelectedItem();
			
			String path = new File("").getAbsolutePath();
			String playlistFile = new File(path+"/db/userdata/vipPlaylists/"+username+"/"+selectedPlaylist).getAbsolutePath();
	    	reader = new BufferedReader(new FileReader(playlistFile));
	    	String line = reader.readLine();
	    	
	    	while (line != null) {
		    	String[] musicData = line.split(":::");
		    	Music LoadNewMusic = new Music(musicData[0], musicData[1]);
		    	lstPlaylist.add(LoadNewMusic);
		    	
		    	lstViewPlaylist.getItems().add(musicData[0]);
		    	line = reader.readLine();
		    }
	    	reader.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
