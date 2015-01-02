package com.megaman.constants;

import com.badlogic.gdx.Application;

public final class GameConstants {
	public static final String	WINDOW_TITLE					= "Protoman vs Megaman";
	public static final String	ATLAS_PATH_GAME					= "packedGraphics/gameGraphics.atlas";
	public static final String	ATLAS_PATH_MENU					= "packedGraphics/menuGraphics.atlas";

	public static final int		GAME_WIDTH						= 800;
	public static final int		GAME_HEIGHT						= 600;

	public static final int		LOG_LEVEL						= Application.LOG_DEBUG;

	public static final int		MENU_OFFSET_TOP					= 240;
	public static final int		MENU_OFFSET_BOTTOM				= 0;
	public static final int		MENU_PADDING_BETWEEN_OPTIONS	= 50;

	public static final int		PROTOMAN_SPEED					= 550;
	public static final int		MAX_LIFE						= 20;

	private GameConstants() {

	}
}
