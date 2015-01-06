package com.gdxgame.core.enums;

import com.gdxgame.constants.GameConstants;

/**
 * TextureType enum is the configuration enum for textures that are used for the game.
 * Each value contains the path to the texture atlas that contains the texture, the original file
 * path within the assets folder, the atlas region name (=name within the texture atlas that identifies the texture),
 * the number of columns and the number of rows (if it is a texture without animation the value for column and row is 1).
 */
public enum TextureType {
	CHARACTER_MEGAMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/megaman.png", "characters/megaman", 3, 1),
	CHARACTER_PROTOMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/protoman.png", "characters/protoman", 2, 1),
	CHARACTER_SPARKMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/sparkman.png", "characters/sparkman", 6, 1),
	CHARACTER_SHADOWMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/shadowman.png", "characters/shadowman", 6, 1),
	CHARACTER_SNAKEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/snakeman.png", "characters/snakeman", 6, 1),
	CHARACTER_NEEDLEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/needleman.png", "characters/needleman", 6, 1),
	CHARACTER_HARDMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/hardman.png", "characters/hardman", 6, 1),
	CHARACTER_TOPMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/topman.png", "characters/topman", 6, 1),
	CHARACTER_GEMINIMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/geminiman.png", "characters/geminiman", 6, 1),
	CHARACTER_MAGNETMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/magnetman.png", "characters/magnetman", 6, 1),
	CHARACTER_METTOOL(GameConstants.ATLAS_PATH_GAME, "graphics/game/characters/mettool.png", "characters/mettool", 5, 1),

	EFFECT_HIT(GameConstants.ATLAS_PATH_GAME, "graphics/game/effects/hit_effect.png", "effects/hit_effect", 3, 1),

	MISSILE_MEGAMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/megaman_missile.png", "missiles/megaman_missile", 1, 1),
	MISSILE_SPARKMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/sparkman_missile.png", "missiles/sparkman_missile", 4, 1),
	MISSILE_SHADOWMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/shadowman_missile.png", "missiles/shadowman_missile", 2, 1),
	MISSILE_SNAKEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/snakeman_missile.png", "missiles/snakeman_missile", 2, 1),
	MISSILE_NEEDLEMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/needleman_missile.png", "missiles/needleman_missile", 1, 1),
	MISSILE_HARDMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/hardman_missile.png", "missiles/hardman_missile", 1, 1),
	MISSILE_TOPMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/topman_missile.png", "missiles/topman_missile", 3, 1),
	MISSILE_GEMINIMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/geminiman_missile.png", "missiles/geminiman_missile", 3, 1),
	MISSILE_MAGNETMAN(GameConstants.ATLAS_PATH_GAME, "graphics/game/missiles/magnetman_missile.png", "missiles/magnetman_missile", 1, 1),

	MENU_MEGAMAN(GameConstants.ATLAS_PATH_MENU, "graphics/menu/megaman.png", "megaman", 3, 1),
	MENU_PROTOMAN(GameConstants.ATLAS_PATH_MENU, "graphics/menu/protoman.png", "protoman", 2, 1),
	MENU_MISSLE(GameConstants.ATLAS_PATH_MENU, "graphics/menu/select_missile.png", "select_missile", 9, 1),

	HUD_LIFE(GameConstants.ATLAS_PATH_HUD, "graphics/hud/life.png", "life", 1, 1),
	HUD_MISSLES(GameConstants.ATLAS_PATH_HUD, "graphics/hud/shots.png", "shots", 1, 1);

	/**
	 * path to the texture atlas containing the texture
	 */
	private final String	textureAtlasPath;
	/**
	 * original path of the file within the assets folder
	 */
	private final String	originalFilePath;
	/**
	 * the atlas region name within the texture atlas that identifies the texture
	 */
	private final String	atlasRegionName;
	/**
	 * number of animations per row
	 */
	private final int		animationsX;
	/**
	 * number of animations per column
	 */
	private final int		animationsY;

	private TextureType(String textureAtlasPath, String originalFilePath, String atlasRegionName, int animationsX, int animationsY) {
		this.textureAtlasPath = textureAtlasPath;
		this.originalFilePath = originalFilePath;
		this.atlasRegionName = atlasRegionName;
		this.animationsX = animationsX;
		this.animationsY = animationsY;
	}

	/**
	 * returns the path to the texture atlas containing the texture
	 * 
	 * @return path to texture atlas
	 */
	public String getTextureAtlasPath() {
		return textureAtlasPath;
	}

	/**
	 * returns the original texture file path within the assets folder
	 * 
	 * @return original texture file path
	 */
	public String getOriginalFilePath() {
		return originalFilePath;
	}

	/**
	 *  returns the identifier of the texture within the texture atlas
	 *  
	 * @return AtlasRegion name for the texture within the texture atlas
	 */
	public String getAtlasRegionName() {
		return atlasRegionName;
	}

	/**
	 * returns the amount of animations the texture has per row.
	 * If getAnimationsX() and getAnimationsY() both return 1 then
	 * the texture does not contain animations
	 * 
	 * @return number of animations per row (must be greater 0)
	 */
	public int getAnimationsX() {
		return animationsX;
	}

	/**
	 * returns the amount of animations the texture has per column.
	 * If getAnimationsX() and getAnimationsY() both return 1 then
	 * the texture does not contain animations
	 * 
	 * @return number of animations per column (must be greater 0)
	 */
	public int getAnimationsY() {
		return animationsY;
	}

	/**
	 * returns the TextureType of a given AtlasRegion name. This method is called by the 
	 * ResourceManager's loadTextureAtlas() map to get the TextureType of a region.
	 * 
	 * However not every region in the texture atlas has to have a TextureType (f.e. the background
	 * images of menu pages do not need be defined within this enum).
	 *  
	 * @param atlasRegionName AtlasRegion name to be searched within the TextureType values
	 * 
	 * @return <b>null</b> if there is no value defined for the given AtlasRegion name. Otherwise it returns the corresponding TextureType
	 */
	public static TextureType getGraphicsConstantByAtlasRegionName(String atlasRegionName) {
		for (TextureType value : TextureType.values()) {
			if (atlasRegionName.equals(value.getAtlasRegionName())) {
				return value;
			}
		}

		return null;
	}
}
