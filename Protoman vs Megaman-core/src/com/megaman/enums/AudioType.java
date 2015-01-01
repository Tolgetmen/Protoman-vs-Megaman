package com.megaman.enums;

public enum AudioType {
	SOUND_SHOOT_MEGAMAN("audio/sounds/megaman_shoot.wav", false),
	SOUND_SHOOT_SPARKMAN("audio/sounds/sparkman_shoot.wav", false),
	SOUND_SHOOT_SHADOWMAN("audio/sounds/shadowman_shoot.wav", false),
	SOUND_SHOOT_SNAKEMAN("audio/sounds/snakeman_shoot.wav", false),
	SOUND_SHOOT_NEEDLEMAN("audio/sounds/needleman_shoot.wav", false),
	SOUND_SHOOT_HARDMAN("audio/sounds/hardman_shoot.wav", false),
	SOUND_SHOOT_TOPMAN("audio/sounds/topman_shoot.wav", false),
	SOUND_SHOOT_GEMINIMAN("audio/sounds/geminiman_shoot.wav", false),
	SOUND_SHOOT_MAGNETMAN("audio/sounds/magnetman_shoot.wav", false),
	SOUND_BLOCK("audio/sounds/block.wav", false),
	SOUND_MENU_MOVE("audio/sounds/menu_move.wav", false),
	SOUND_MENU_SELECT("audio/sounds/menu_select.wav", false),
	SOUND_MENU_SELECT_SHOOT("audio/sounds/menu_select_shoot.wav", false),

	MUSIC_MENU("audio/music/menu.mp3", true),
	MUSIC_MENU_QUIT("audio/sounds/menu_quit.wav", true),
	MUSIC_SPARKMAN("audio/music/sparkman.mp3", true),
	MUSIC_SHADOWMAN("audio/music/shadowman.mp3", true),
	MUSIC_SNAKEMAN("audio/music/snakeman.mp3", true),
	MUSIC_NEEDLEMAN("audio/music/needleman.mp3", true),
	MUSIC_HARDMAN("audio/music/hardman.mp3", true),
	MUSIC_TOPMAN("audio/music/topman.mp3", true),
	MUSIC_GEMINIMAN("audio/music/geminiman.mp3", true),
	MUSIC_MAGNETMAN("audio/music/magnetman.mp3", true),
	MUSIC_PROTOMAN("audio/music/protoman_theme.mp3", true);

	private final String	filePath;
	private final boolean	isMusic;

	private AudioType(String filePath, boolean isMusic) {
		this.filePath = filePath;
		this.isMusic = isMusic;
	}

	public String getFilePath() {
		return filePath;
	}

	public boolean isMusic() {
		return isMusic;
	}
}
