package com.megaman.enums;

import com.gdxgame.constants.GameConstants;

public enum ParticleFXType {
	FLAME("particles/flame.p", GameConstants.ATLAS_PATH_GAME, "effects/", 0.7f);

	private final String	filePath;
	private final String	textureAtlasPath;
	private final String	atlasPrefix;
	private final float		scaleFactor;

	private ParticleFXType(String filePath, String textureAtlasPath, String atlasPrefix, float scaleFactor) {
		this.filePath = filePath;
		this.textureAtlasPath = textureAtlasPath;
		this.atlasPrefix = atlasPrefix;
		this.scaleFactor = scaleFactor;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getTextureAtlasPath() {
		return textureAtlasPath;
	}

	public String getAtlasPrefix() {
		return atlasPrefix;
	}

	public float getScaleFactor() {
		return scaleFactor;
	}
}
