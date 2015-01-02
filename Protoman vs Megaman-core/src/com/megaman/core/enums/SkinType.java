package com.megaman.core.enums;

import com.megaman.constants.GameConstants;

public enum SkinType {
	SKIN_MAIN_MENU("skin.json", GameConstants.ATLAS_PATH_MENU);

	private final String	filePath;
	private final String	textureAtlasFilePath;

	private SkinType(String skinFilePath, String skinTextureAtlasFilePath) {
		this.filePath = skinFilePath;
		this.textureAtlasFilePath = skinTextureAtlasFilePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getTextureAtlasFilePath() {
		return textureAtlasFilePath;
	}
}
