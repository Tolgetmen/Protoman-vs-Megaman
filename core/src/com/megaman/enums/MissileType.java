package com.megaman.enums;

import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;

public enum MissileType {
	MEGAMAN(TextureType.MISSILE_MEGAMAN, SoundType.SHOOT_MEGAMAN, 8, 8, 0, false, 150),
	SPARKMAN(TextureType.MISSILE_SPARKMAN, SoundType.SHOOT_SPARKMAN, 32, 32, 8, false, 180),
	SNAKEMAN(TextureType.MISSILE_SNAKEMAN, SoundType.SHOOT_SNAKEMAN, 32, 32, 12, true, 210),
	NEEDLEMAN(TextureType.MISSILE_NEEDLEMAN, SoundType.SHOOT_NEEDLEMAN, 32, 32, 0, false, 240),
	HARDMAN(TextureType.MISSILE_HARDMAN, SoundType.SHOOT_HARDMAN, 32, 32, 0, false, 270),
	TOPMAN(TextureType.MISSILE_TOPMAN, SoundType.SHOOT_TOPMAN, 32, 32, 12, true, 300),
	GEMINIMAN(TextureType.MISSILE_GEMINIMAN, SoundType.SHOOT_GEMINIMAN, 32, 32, 8, false, 330),
	MAGNETMAN(TextureType.MISSILE_MAGNETMAN, SoundType.SHOOT_MAGNETMAN, 32, 32, 0, false, 360),
	SHADOWMAN(TextureType.MISSILE_SHADOWMAN, SoundType.SHOOT_SHADOWMAN, 32, 32, 12, true, 390),
	DEATH(TextureType.EFFECT_DEATH, null, 32, 32, 24, true, 90);

	private final TextureType	textureType;
	private final SoundType		soundType;
	private final int			width;
	private final int			height;
	private final int			animationsPerSecond;
	private final boolean		loopAnimation;
	private final int			speed;

	private MissileType(TextureType textureType, SoundType soundType, int width, int height, int animationsPerSecond, boolean loopAnimation, int speed) {
		this.textureType = textureType;
		this.soundType = soundType;
		this.width = width;
		this.height = height;
		this.animationsPerSecond = animationsPerSecond;
		this.loopAnimation = loopAnimation;
		this.speed = speed;
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

	public boolean isLoopAnimation() {
		return loopAnimation;
	}

	public int getSpeed() {
		return speed;
	}
}
