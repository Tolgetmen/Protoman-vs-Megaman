package com.megaman.enums;

import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.TextureType;

public enum BossType {
	SPARKMAN(TextureType.CHARACTER_SPARKMAN, MissileType.SPARKMAN, MusicType.SPARKMAN, 48, 48, 10),
	SNAKEMAN(TextureType.CHARACTER_SNAKEMAN, MissileType.SNAKEMAN, MusicType.SNAKEMAN, 48, 48, 10),
	NEEDLEMAN(TextureType.CHARACTER_NEEDLEMAN, MissileType.NEEDLEMAN, MusicType.NEEDLEMAN, 48, 48, 10),
	HARDMAN(TextureType.CHARACTER_HARDMAN, MissileType.HARDMAN, MusicType.HARDMAN, 48, 48, 10),
	TOPMAN(TextureType.CHARACTER_TOPMAN, MissileType.TOPMAN, MusicType.TOPMAN, 48, 48, 10),
	GEMINIMAN(TextureType.CHARACTER_GEMINIMAN, MissileType.GEMINIMAN, MusicType.GEMINIMAN, 48, 48, 10),
	MAGNETMAN(TextureType.CHARACTER_MAGNETMAN, MissileType.MAGNETMAN, MusicType.MAGNETMAN, 48, 48, 10),
	SHADOWMAN(TextureType.CHARACTER_SHADOWMAN, MissileType.SHADOWMAN, MusicType.SHADOWMAN, 48, 48, 10);

	private final TextureType	textureType;
	private final MissileType	missileType;
	private final MusicType		musicType;
	private final int			width;
	private final int			height;

	private final int			animationsPerSecond;

	private BossType(TextureType textureType, MissileType missileType, MusicType musicType, int width, int height, int animationsPerSecond) {
		this.textureType = textureType;
		this.missileType = missileType;
		this.musicType = musicType;
		this.width = width;
		this.height = height;
		this.animationsPerSecond = animationsPerSecond;
	}

	public TextureType getTextureType() {
		return textureType;
	}

	public MissileType getMissileType() {
		return missileType;
	}

	public MusicType getMusicType() {
		return musicType;
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
