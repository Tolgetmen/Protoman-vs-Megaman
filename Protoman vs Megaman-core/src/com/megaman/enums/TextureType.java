package com.megaman.enums;

import com.megaman.constants.GameConstants;

public enum TextureType {
	TEXTURE_CHARACTER_MEGAMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/megaman.png", "characters/megaman", 3, 1),
	TEXTURE_CHARACTER_PROTOMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/protoman.png", "characters/protoman", 2, 1),
	TEXTURE_CHARACTER_SPARKMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/sparkman.png", "characters/sparkman", 6, 1),
	TEXTURE_CHARACTER_SHADOWMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/shadowman.png", "characters/shadowman", 6, 1),
	TEXTURE_CHARACTER_SNAKEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/snakeman.png", "characters/snakeman", 6, 1),
	TEXTURE_CHARACTER_NEEDLEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/needleman.png", "characters/needleman", 6, 1),
	TEXTURE_CHARACTER_HARDMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/hardman.png", "characters/hardman", 6, 1),
	TEXTURE_CHARACTER_TOPMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/topman.png", "characters/topman", 6, 1),
	TEXTURE_CHARACTER_GEMINIMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/geminiman.png", "characters/geminiman", 6, 1),
	TEXTURE_CHARACTER_MAGNETMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/magnetman.png", "characters/magnetman", 6, 1),

	TEXTURE_MISSILE_MEGAMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/megaman_missile.png", "missiles/megaman_missile", 1, 1),
	TEXTURE_MISSILE_SPARKMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/sparkman_missile.png", "missiles/sparkman_missile", 4, 1),
	TEXTURE_MISSILE_SHADOWMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/shadowman_missile.png", "missiles/shadowman_missile", 2, 1),
	TEXTURE_MISSILE_SNAKEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/snakeman_missile.png", "missiles/snakeman_missile", 2, 1),
	TEXTURE_MISSILE_NEEDLEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/needleman_missile.png", "missiles/needleman_missile", 1, 1),
	TEXTURE_MISSILE_HARDMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/hardman_missile.png", "missiles/hardman_missile", 1, 1),
	TEXTURE_MISSILE_TOPMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/topman_missile.png", "missiles/topman_missile", 3, 1),
	TEXTURE_MISSILE_GEMINIMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/geminiman_missile.png", "missiles/geminiman_missile", 3, 1),
	TEXTURE_MISSILE_MAGNETMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/magnetman_missile.png", "missiles/magnetman_missile", 1, 1),

	TEXTURE_MENU_MEGAMAN(GameConstants.ATLAS_PATH_MENU, "graphics/menu/megaman.png", "megaman", 3, 1),
	TEXTURE_MENU_PROTOMAN(GameConstants.ATLAS_PATH_MENU, "graphics/menu/protoman.png", "protoman", 2, 1),
	TEXTURE_MENU_MISSLE(GameConstants.ATLAS_PATH_MENU, "graphics/menu/select_missile.png", "select_missile", 9, 1);

	private final String	textureAtlasPath;
	private final String	originalFilePath;
	private final String	atlasRegionName;
	private final int		numColumns;
	private final int		numRows;

	private TextureType(String textureAtlasPath, String originalFilePath, String atlasRegionName, int numColumns, int numRows) {
		this.textureAtlasPath = textureAtlasPath;
		this.originalFilePath = originalFilePath;
		this.atlasRegionName = atlasRegionName;
		this.numColumns = numColumns;
		this.numRows = numRows;
	}

	public String getTextureAtlasPath() {
		return textureAtlasPath;
	}

	public String getOriginalFilePath() {
		return originalFilePath;
	}

	public String getAtlasRegionName() {
		return atlasRegionName;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumRows() {
		return numRows;
	}

	public static TextureType getGraphicsConstantByAtlasRegionName(String atlasRegionName) {
		for (TextureType value : TextureType.values()) {
			if (atlasRegionName.equals(value.getAtlasRegionName()))
				return value;
		}

		return null;
	}
}
