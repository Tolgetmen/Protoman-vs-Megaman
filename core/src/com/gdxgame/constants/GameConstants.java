package com.gdxgame.constants;

import com.badlogic.gdx.Application;

public final class GameConstants {
	public static final String	WINDOW_TITLE			= "Protoman vs Megaman";

	public static final String	ATLAS_PATH_GAME			= "packedGraphics/gameGraphics.atlas";
	public static final String	ATLAS_PATH_MENU			= "packedGraphics/menuGraphics.atlas";
	public static final String	ATLAS_PATH_HUD			= "packedGraphics/hud.atlas";

	public static final String	LABEL_BUNDLE_PATH		= "localization/GameLabels";

	public static final String	CFG_FILE_PATH			= "../core/assets/game.cfg";
	public static final String	CFG_FILE_CFG_SECTION	= "Gameconfig";
	public static final String	CFG_KEY_WIDTH			= "windowWidth";
	public static final String	CFG_KEY_HEIGHT			= "windowHeight";
	public static final String	CFG_KEY_FULLSCREEN		= "fullscreen";
	public static final String	CFG_KEY_MUSIC_VOLUME	= "musicVolume";
	public static final String	CFG_KEY_SOUND_VOLUME	= "soundVolume";
	public static final String	CFG_KEY_LANGUAGE		= "language";

	public static final float	CLEAR_COLOR_RED			= 0;
	public static final float	CLEAR_COLOR_BLUE		= 0.2f;
	public static final float	CLEAR_COLOR_GREEN		= 0;
	public static final float	CLEAR_COLOR_ALPHA		= 0;

	public static final int		GAME_WIDTH				= 800;
	public static final int		GAME_HEIGHT				= 600;

	public static final boolean	HIDE_MOUSE				= true;

	public static final int		LOG_LEVEL				= Application.LOG_DEBUG;
	public static final String	LOG_TAG_DEBUG			= "DEBUG";
	public static final String	LOG_TAG_INFO			= "INFO";
	public static final String	LOG_TAG_ERROR			= "ERROR";

	private GameConstants() {

	}
}
