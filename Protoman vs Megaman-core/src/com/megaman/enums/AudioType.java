package com.megaman.enums;

public enum AudioType {
	SOUND_SHOOT_MEGAMAN(null, "audio/sounds/megaman_shoot.wav", false),
	SOUND_SHOOT_SPARKMAN(null, "audio/sounds/sparkman_shoot.wav", false),
	SOUND_SHOOT_SHADOWMAN(null, "audio/sounds/shadowman_shoot.wav", false),
	SOUND_SHOOT_SNAKEMAN(null, "audio/sounds/snakeman_shoot.wav", false),
	SOUND_SHOOT_NEEDLEMAN(null, "audio/sounds/needleman_shoot.wav", false),
	SOUND_SHOOT_HARDMAN(null, "audio/sounds/hardman_shoot.wav", false),
	SOUND_SHOOT_TOPMAN(null, "audio/sounds/topman_shoot.wav", false),
	SOUND_SHOOT_GEMINIMAN(null, "audio/sounds/geminiman_shoot.wav", false),
	SOUND_SHOOT_MAGNETMAN(null, "audio/sounds/magnetman_shoot.wav", false),
	SOUND_BLOCK(null, "audio/sounds/block.wav", false),
	SOUND_MENU_MOVE(null, "audio/sounds/menu_move.wav", false),
	SOUND_MENU_SELECT(null, "audio/sounds/menu_select.wav", false),
	SOUND_MENU_SELECT_SHOOT(null, "audio/sounds/menu_select_shoot.wav", false),

	MUSIC_MENU("mm3 title theme", "audio/music/menu.mp3", true),
	MUSIC_MENU_QUIT(null, "audio/sounds/menu_quit.wav", true),
	MUSIC_SPARKMAN("sparkman theme", "audio/music/sparkman.mp3", true),
	MUSIC_SHADOWMAN("shadowman theme", "audio/music/shadowman.mp3", true),
	MUSIC_SNAKEMAN("snakeman theme", "audio/music/snakeman.mp3", true),
	MUSIC_NEEDLEMAN("needleman theme", "audio/music/needleman.mp3", true),
	MUSIC_HARDMAN("hardman theme", "audio/music/hardman.mp3", true),
	MUSIC_TOPMAN("topman theme", "audio/music/topman.mp3", true),
	MUSIC_GEMINIMAN("geminiman theme", "audio/music/geminiman.mp3", true),
	MUSIC_MAGNETMAN("magnetman theme", "audio/music/magnetman.mp3", true),
	MUSIC_PROTOMAN("protoman theme", "audio/music/protoman_theme.mp3", true),
	MUSIC_WILY_STAGE("mm3 wily stage 1", "audio/music/wily_stage_1.mp3", true);

	private final String	audioName;
	private final String	filePath;
	private final boolean	isMusic;

	private AudioType(String audioName, String filePath, boolean isMusic) {
		this.audioName = audioName;
		this.filePath = filePath;
		this.isMusic = isMusic;
	}

	public String getFilePath() {
		return filePath;
	}

	public boolean isMusic() {
		return isMusic;
	}

	public String getAudioName() {
		return audioName;
	}
}
