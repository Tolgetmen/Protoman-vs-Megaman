package com.gdxgame.core.enums;

/**
 * SoundType enum is the configuration enum for sound that is used for the game.
 * Each value contains the path to the sound file.
 */
public enum SoundType {
	SHOOT_MEGAMAN("audio/sounds/megaman_shoot.wav"),
	SHOOT_SPARKMAN("audio/sounds/sparkman_shoot.wav"),
	SHOOT_SHADOWMAN("audio/sounds/shadowman_shoot.wav"),
	SHOOT_SNAKEMAN("audio/sounds/snakeman_shoot.wav"),
	SHOOT_NEEDLEMAN("audio/sounds/needleman_shoot.wav"),
	SHOOT_HARDMAN("audio/sounds/hardman_shoot.wav"),
	SHOOT_TOPMAN("audio/sounds/topman_shoot.wav"),
	SHOOT_GEMINIMAN("audio/sounds/geminiman_shoot.wav"),
	SHOOT_MAGNETMAN("audio/sounds/magnetman_shoot.wav"),
	BLOCK("audio/sounds/block.wav"),
	HIT("audio/sounds/hit.wav"),
	MENU_MOVE("audio/sounds/menu_move.wav"),
	MENU_SELECT("audio/sounds/menu_select.wav"),
	MENU_SELECT_SHOOT("audio/sounds/menu_select_shoot.wav"),
	DEATH("audio/sounds/death.wav");

	/**
	 * file path to the sound file in the assets folder
	 */
	private final String	filePath;

	private SoundType(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * returns the file path of the sound file in the assets folder
	 * 
	 * @return file path of sound file
	 */
	public String getFilePath() {
		return filePath;
	}
}
