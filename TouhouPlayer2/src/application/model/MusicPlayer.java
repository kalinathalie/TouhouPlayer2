package application.model;

import javafx.scene.control.Button;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

import java.io.InputStream;
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
	
	private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
    
    private final Object playerLock = new Object();
    private int playerStatus = NOTSTARTED;
    /**
     * constructor
     */
    public MusicPlayer(){
    }
    /**
     * construcotor
     * @param inputStream music file to load
     */
    public MusicPlayer(final InputStream inputStream) throws JavaLayerException {
        this.player = new Player(inputStream);
    }
    
    /**
     * constructor
     * @param inputStream music file to load
     * @param audioDevice local audio loaded
     */
    public MusicPlayer(final InputStream inputStream, final AudioDevice audioDevice) throws JavaLayerException {
        this.player = new Player(inputStream, audioDevice);
    }

    /**
     * Starts playback (resumes if paused)
     */
    public void getPlay() throws JavaLayerException {
        try{
        	synchronized (playerLock) {
        		switch (playerStatus) {
        		case NOTSTARTED:
        			final Runnable r = new Runnable() {
        				public void run() {
        					playInternal();
        				}
        			};
        			final Thread t = new Thread(r);
        			t.setDaemon(true);
        			t.setPriority(Thread.MAX_PRIORITY);
        			playerStatus = PLAYING;
        			t.start();
        			break;
        		case PAUSED:
        			resume();
        			break;
        		default:
        			break;
        		}
        	}
        }catch(Exception e) {
        	System.out.println(e);
        }
    }

    /**
     * Pauses playback.
     * @return true if new state is PAUSED.
     */
    public boolean getPause() {
        try{
        	synchronized (playerLock) {
	            if (playerStatus == PLAYING) {
	                playerStatus = PAUSED;
	            }
	            return playerStatus == PAUSED;
	        }
        }catch(Exception e) {
        	System.out.println(e);
        }
        return false;
    }

    /**
     * Resumes playback.
     * @return true if the new state is PLAYING.
     */
    public boolean resume() {
        try {
        	synchronized (playerLock) {
        		if (playerStatus == PAUSED) {
	                playerStatus = PLAYING;
	                playerLock.notifyAll();
	            }
	            return playerStatus == PLAYING;
	        }
        }catch(Exception e) {
        	System.out.println(e);
        }
        return false;
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public void getStop() {
        try{
        	synchronized (playerLock) {
        		playerStatus = FINISHED;
        		playerLock.notifyAll();
	        }
        }catch(Exception e) {
        	System.out.println(e);
        }
    }
    /**
     * The main player
     */
    private void playInternal() {
        try {
	    	while (playerStatus != FINISHED) {
	            try {
	                if (!player.play(1)) {
	                    break;
	                }
	            } catch (final JavaLayerException e) {
	                break;
	            }
	            // check if paused or terminated
	            synchronized (playerLock) {
	                while (playerStatus == PAUSED) {
	                    try {
	                        playerLock.wait();
	                    } catch (final InterruptedException e) {
	                        break;
	                    }
	                }
	            }
	        }
        }catch(Exception e) {
        	System.out.println(e);
        }
        close();
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
    	try {
    		synchronized (playerLock) {
    			playerStatus = FINISHED;
    		}
            player.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Verify if the player is running
     * @return true if the media is playing, else false
     */
    public Boolean isPlaying() {
    	try {
	    	switch (playerStatus) {
	    	case PLAYING:
	    		return true;
	    	default:
	            return false;
	    	}
    	}catch(Exception e){
	    		System.out.println(e);
	    }
    	return false;
    }
    /**
     * Verify if the player is paused
     * @return true if the media is paused, else false 
     */
    public Boolean isPaused() {
	    try {
	    	switch (playerStatus) {
	    	case PAUSED:
	    		return true;
	    	default:
	            return false;
	    	}
	    }catch(Exception e) {
	    	System.out.println(e);
	    }
	    return false;
    }
}
