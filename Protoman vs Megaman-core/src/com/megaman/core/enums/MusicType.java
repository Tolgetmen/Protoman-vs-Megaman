package com.megaman.core.enums;

/**
 * MusicType enum is the configuration enum for music that is used for the game.
 * Each value contains an optional arbitrary title name and the path to the music file.
 */
public enum MusicType {
	MENU("mm3 title theme", "audio/music/menu.mp3"),
	MENU_QUIT(null, "audio/sounds/menu_quit.wav"),
	SPARKMAN("sparkman theme", "audio/music/sparkman.mp3"),
	SHADOWMAN("shadowman theme", "audio/music/shadowman.mp3"),
	SNAKEMAN("snakeman theme", "audio/music/snakeman.mp3"),
	NEEDLEMAN("needleman theme", "audio/music/needleman.mp3"),
	HARDMAN("hardman theme", "audio/music/hardman.mp3"),
	TOPMAN("topman theme", "audio/music/topman.mp3"),
	GEMINIMAN("geminiman theme", "audio/music/geminiman.mp3"),
	MAGNETMAN("magnetman theme", "audio/music/magnetman.mp3"),
	PROTOMAN("protoman theme", "audio/music/protoman_theme.mp3"),
	WILY_STAGE("mm3 wily stage 1", "audio/music/wily_stage_1.mp3");

	/**
	 * optional name for the music track
	 */
	private final String	name;
	/**
	 * path to the music file
	 */
	private final String	filePath;

	private MusicType(String audioName, String filePath) {
		this.name = audioName;
		this.filePath = filePath;
	}

	/**
	 * returns the file path to the music track within the assets folder
	 * 
	 * @return path to music file
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * returns the name of the music track if there is one specified
	 * 
	 * @return <b>null</b> if there is no name specified. Otherwise it will return the music track name
	 */
	public String getName() {
		return name;
	}
}
