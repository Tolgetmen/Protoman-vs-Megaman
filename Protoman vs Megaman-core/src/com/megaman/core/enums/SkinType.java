package com.megaman.core.enums;

import com.megaman.constants.GameConstants;

/**
 * SkinType enum is the configuration enum for libgdx Skins. Each value contains
 * the path to the skin file and a path to the texture atlas that the skin uses.
 */
public enum SkinType {
	SKIN_MAIN_MENU("skin.json", GameConstants.ATLAS_PATH_MENU);

	/**
	 * path to the skin JSON file
	 */
	private final String	filePath;
	/**
	 * path to the texture atlas that the skin uses
	 */
	private final String	textureAtlasFilePath;

	private SkinType(String skinFilePath, String skinTextureAtlasFilePath) {
		this.filePath = skinFilePath;
		this.textureAtlasFilePath = skinTextureAtlasFilePath;
	}

	/**
	 * returns the file path of the JSON file of the skin
	 * 
	 * @return file path to JSON skin file
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 *  returns the filepath to the skin's texture atlas
	 *  
	 * @return file path of texture atlas that is used by the skin
	 */
	public String getTextureAtlasFilePath() {
		return textureAtlasFilePath;
	}
}
