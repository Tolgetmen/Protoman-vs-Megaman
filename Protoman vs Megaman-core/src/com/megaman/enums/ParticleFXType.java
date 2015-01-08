package com.megaman.enums;

import com.gdxgame.constants.GameConstants;

public enum ParticleFXType {
	FLAME("particles/flame.p", GameConstants.ATLAS_PATH_GAME, 0.7f);

	private final String	filePath;
	private final String	textureAtlasPath;
	private final float		scaleFactor;

	private ParticleFXType(String filePath, String textureAtlasPath, float scaleFactor) {
		this.filePath = filePath;
		this.textureAtlasPath = textureAtlasPath;
		this.scaleFactor = scaleFactor;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getTextureAtlasPath() {
		return textureAtlasPath;
	}

	public float getScaleFactor() {
		return scaleFactor;
	}
}
