package com.megaman.constants;

import com.badlogic.gdx.Application;

public final class GameConstants {
	public static final String	WINDOW_TITLE					= "Protoman vs Megaman";

	public static final String	ATLAS_PATH_GAME					= "packedGraphics/gameGraphics.atlas";
	public static final String	ATLAS_PATH_MENU					= "packedGraphics/menuGraphics.atlas";

	public static final float	CLEAR_COLOR_RED					= 0;
	public static final float	CLEAR_COLOR_BLUE				= 0.2f;
	public static final float	CLEAR_COLOR_GREEN				= 0;
	public static final float	CLEAR_COLOR_ALPHA				= 0;

	public static final int		GAME_WIDTH						= 800;
	public static final int		GAME_HEIGHT						= 600;

	public static final boolean	HIDE_MOUSE						= true;

	public static final int		LOG_LEVEL						= Application.LOG_DEBUG;
	public static final String	LOG_TAG_DEBUG					= "DEBUG";
	public static final String	LOG_TAG_INFO					= "INFO";
	public static final String	LOG_TAG_ERROR					= "ERROR";

	public static final int		MENU_OFFSET_TOP					= 240;
	public static final int		MENU_OFFSET_BOTTOM				= 0;
	public static final int		MENU_PADDING_BETWEEN_OPTIONS	= 50;

	public static final int		PROTOMAN_SPEED					= 550;
	public static final int		MAX_LIFE						= 20;

	private GameConstants() {

	}
}
