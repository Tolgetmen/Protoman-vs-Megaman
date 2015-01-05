package com.megaman.enums;

import com.gdxgame.core.enums.TextureType;

public enum CharacterType {
	MEGAMAN(TextureType.CHARACTER_MEGAMAN, 32, 32, 0),
	PROTOMAN(TextureType.CHARACTER_PROTOMAN, 32, 32, 0),
	METTOOL(TextureType.CHARACTER_METTOOL, 40, 40, 3);

	private final TextureType	textureType;
	private final int			width;
	private final int			height;
	private final int			animationsPerSeconds;

	private CharacterType(TextureType textureType, int width, int height, int animationsPerSeconds) {
		this.textureType = textureType;
		this.width = width;
		this.height = height;
		this.animationsPerSeconds = animationsPerSeconds;
	}

	public TextureType getTextureType() {
		return textureType;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getAnimationsPerSeconds() {
		return animationsPerSeconds;
	}
}
