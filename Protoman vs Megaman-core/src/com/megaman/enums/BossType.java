package com.megaman.enums;

import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.TextureType;

public enum BossType {
	SPARKMAN(TextureType.TEXTURE_CHARACTER_SPARKMAN, MissileType.SPARKMAN, 10, MusicType.SPARKMAN),
	SNAKEMAN(TextureType.TEXTURE_CHARACTER_SNAKEMAN, MissileType.SNAKEMAN, 10, MusicType.SNAKEMAN),
	NEEDLEMAN(TextureType.TEXTURE_CHARACTER_NEEDLEMAN, MissileType.NEEDLEMAN, 10, MusicType.NEEDLEMAN),
	HARDMAN(TextureType.TEXTURE_CHARACTER_HARDMAN, MissileType.HARDMAN, 10, MusicType.HARDMAN),
	TOPMAN(TextureType.TEXTURE_CHARACTER_TOPMAN, MissileType.TOPMAN, 10, MusicType.TOPMAN),
	GEMINIMAN(TextureType.TEXTURE_CHARACTER_GEMINIMAN, MissileType.GEMINIMAN, 10, MusicType.GEMINIMAN),
	MAGNETMAN(TextureType.TEXTURE_CHARACTER_MAGNETMAN, MissileType.MAGNETMAN, 10, MusicType.MAGNETMAN),
	SHADOWMAN(TextureType.TEXTURE_CHARACTER_SHADOWMAN, MissileType.SHADOWMAN, 10, MusicType.SHADOWMAN);

	private final TextureType	graphic;
	private final MissileType	missileType;
	private final int			animationsPerSecond;
	private final MusicType		music;

	private BossType(TextureType graphic, MissileType missileType, int animationsPerSecond, MusicType music) {
		this.graphic = graphic;
		this.missileType = missileType;
		this.animationsPerSecond = animationsPerSecond;
		this.music = music;
	}

	public MusicType getMusic() {
		return music;
	}

	public TextureType getGraphic() {
		return graphic;
	}

	public MissileType getMissileType() {
		return missileType;
	}

	public int getAnimationsPerSecond() {
		return animationsPerSecond;
	}
}
