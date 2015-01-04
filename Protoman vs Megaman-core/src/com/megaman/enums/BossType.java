package com.megaman.enums;

import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.TextureType;

public enum BossType {
	SPARKMAN(TextureType.TEXTURE_CHARACTER_SPARKMAN, MissileType.SPARKMAN, MusicType.SPARKMAN, 48, 48, 10),
	SNAKEMAN(TextureType.TEXTURE_CHARACTER_SNAKEMAN, MissileType.SNAKEMAN, MusicType.SNAKEMAN, 48, 48, 10),
	NEEDLEMAN(TextureType.TEXTURE_CHARACTER_NEEDLEMAN, MissileType.NEEDLEMAN, MusicType.NEEDLEMAN, 48, 48, 10),
	HARDMAN(TextureType.TEXTURE_CHARACTER_HARDMAN, MissileType.HARDMAN, MusicType.HARDMAN, 48, 48, 10),
	TOPMAN(TextureType.TEXTURE_CHARACTER_TOPMAN, MissileType.TOPMAN, MusicType.TOPMAN, 48, 48, 10),
	GEMINIMAN(TextureType.TEXTURE_CHARACTER_GEMINIMAN, MissileType.GEMINIMAN, MusicType.GEMINIMAN, 48, 48, 10),
	MAGNETMAN(TextureType.TEXTURE_CHARACTER_MAGNETMAN, MissileType.MAGNETMAN, MusicType.MAGNETMAN, 48, 48, 10),
	SHADOWMAN(TextureType.TEXTURE_CHARACTER_SHADOWMAN, MissileType.SHADOWMAN, MusicType.SHADOWMAN, 48, 48, 10);

	private final TextureType	graphic;
	private final MissileType	missileType;
	private final MusicType		music;
	private final int			width;
	private final int			height;

	private final int			animationsPerSecond;

	private BossType(TextureType graphic, MissileType missileType, MusicType music, int width, int height, int animationsPerSecond) {
		this.graphic = graphic;
		this.missileType = missileType;
		this.music = music;
		this.width = width;
		this.height = height;
		this.animationsPerSecond = animationsPerSecond;
	}

	public TextureType getGraphic() {
		return graphic;
	}

	public MissileType getMissileType() {
		return missileType;
	}

	public MusicType getMusic() {
		return music;
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
