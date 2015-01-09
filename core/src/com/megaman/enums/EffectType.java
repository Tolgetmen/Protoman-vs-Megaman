package com.megaman.enums;

import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;

public enum EffectType {
	HIT(TextureType.EFFECT_HIT, SoundType.HIT, 42, 42, 3);

	private final TextureType	textureType;
	private final SoundType		soundType;
	private final int			width;
	private final int			height;
	private final int			animationsPerSecond;

	private EffectType(TextureType textureType, SoundType soundType, int width, int height, int animationsPerSecond) {
		this.textureType = textureType;
		this.soundType = soundType;
		this.width = width;
		this.height = height;
		this.animationsPerSecond = animationsPerSecond;
	}

	public TextureType getTextureType() {
		return textureType;
	}

	public SoundType getSoundType() {
		return soundType;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getAnimationsPerSecond() {
		return animationsPerSecond;
	}
}
