package com.megaman.enums;

public enum BossType {
	SPARKMAN(TextureType.TEXTURE_CHARACTER_SPARKMAN, MissileType.SPARKMAN, 10),
	SHADOWMAN(TextureType.TEXTURE_CHARACTER_SHADOWMAN, MissileType.SHADOWMAN, 10),
	SNAKEMAN(TextureType.TEXTURE_CHARACTER_SNAKEMAN, MissileType.SNAKEMAN, 10),
	NEEDLEMAN(TextureType.TEXTURE_CHARACTER_NEEDLEMAN, MissileType.NEEDLEMAN, 10),
	HARDMAN(TextureType.TEXTURE_CHARACTER_HARDMAN, MissileType.HARDMAN, 10),
	TOPMAN(TextureType.TEXTURE_CHARACTER_TOPMAN, MissileType.TOPMAN, 10),
	GEMINIMAN(TextureType.TEXTURE_CHARACTER_GEMINIMAN, MissileType.GEMINIMAN, 10),
	MAGNETMAN(TextureType.TEXTURE_CHARACTER_MAGNETMAN, MissileType.MAGNETMAN, 10);

	private final TextureType	graphic;
	private final MissileType			missileType;
	private final int					animationsPerSecond;

	private BossType(TextureType graphic, MissileType missileType, int animationsPerSecond) {
		this.graphic = graphic;
		this.missileType = missileType;
		this.animationsPerSecond = animationsPerSecond;
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
