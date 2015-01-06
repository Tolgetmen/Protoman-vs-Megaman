package com.megaman.constants;

public class MegamanConstants {
	public static final int		MENU_OFFSET_TOP						= 240;
	public static final int		MENU_OFFSET_BOTTOM					= 0;
	public static final int		MENU_PADDING_BETWEEN_OPTIONS		= 50;

	public static final float	BOSS_FADE_IN_TIME					= 0.75f;
	public static final float	BOSS_FADE_OUT_TIME					= 0.5f;
	// boss duration in number of megaman shots
	// f.e. 3 means that megaman shoots three times before 
	// switching to the next boss
	public static final int		BOSS_DURATION						= 9;
	// boss start delay in number of megaman shots
	// defined how many times megaman shoots before
	// spawning the first boss
	public static final int		BOSS_START_DELAY					= 5;
	// defines when a boss should spawn
	// f.e. a value of 3 means that a boss will after every
	// three missiles that megaman shoots
	public static final int		BOSS_FREQUENCY						= 3;

	public static final float	MEGAMAN_SHOOT_START_FREQUENCY		= 2.25f;
	public static final float	MEGAMAN_SHOOT_FREQUENCY_DECREMENT	= 0.07f;
	// defines when the shot frequency should be decreased
	// f.e. a value of 3 means that after every third shot 
	// the shot frequency will decrease
	public static final int		MEGAMAN_FREQUENCY_CHANGE			= 5;
	public static final int		MEGAMAN_MAX_MISSILES				= 77;
	public static final float	MEGAMAN_FADE_OUT_TIME				= 0.5f;

	public static final int		PROTOMAN_SPEED						= 460;
	public static final int		MAX_LIFE							= 20;

	public static final String	BACKGROUND_MAP_PATH					= "maps/background.tmx";

	private MegamanConstants() {

	}
}
