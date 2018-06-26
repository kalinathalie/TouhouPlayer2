package application.model;

public class Music {
	
	private String Name;
	private String Loc;
	/*
	 * Music constructor
	 * @param MusicName name of the music
	 * @param MusicLoc localization of the music
	 */
	public Music(String MusicName, String MusicLoc) {
		this.Name = MusicName;
		this.Loc = MusicLoc;
	}
	/*
	 * @return music name
	 */
	public String getName() {
		return this.Name;
	}
	/*
	 * @return music localization
	 */
	public String getLoc() {
		return this.Loc;
	}
	
}
