package com.megaman.enums;

import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;

public enum MissileType {
	MEGAMAN(TextureType.TEXTURE_MISSILE_MEGAMAN, SoundType.SHOOT_MEGAMAN, 150, 0, false),
	SPARKMAN(TextureType.TEXTURE_MISSILE_SPARKMAN, SoundType.SHOOT_SPARKMAN, 180, 8, false),
	SNAKEMAN(TextureType.TEXTURE_MISSILE_SNAKEMAN, SoundType.SHOOT_SNAKEMAN, 210, 12, true),
	NEEDLEMAN(TextureType.TEXTURE_MISSILE_NEEDLEMAN, SoundType.SHOOT_NEEDLEMAN, 240, 0, false),
	HARDMAN(TextureType.TEXTURE_MISSILE_HARDMAN, SoundType.SHOOT_HARDMAN, 270, 0, false),
	TOPMAN(TextureType.TEXTURE_MISSILE_TOPMAN, SoundType.SHOOT_TOPMAN, 300, 12, true),
	GEMINIMAN(TextureType.TEXTURE_MISSILE_GEMINIMAN, SoundType.SHOOT_GEMINIMAN, 330, 8, false),
	MAGNETMAN(TextureType.TEXTURE_MISSILE_MAGNETMAN, SoundType.SHOOT_MAGNETMAN, 360, 0, false),
	SHADOWMAN(TextureType.TEXTURE_MISSILE_SHADOWMAN, SoundType.SHOOT_SHADOWMAN, 390, 12, true);

	private final TextureType	graphic;
	private final SoundType		sound;
	private final int			speed;
	private final int			animationsPerSecond;
	private final boolean		loopAnimation;

	private MissileType(TextureType graphic, SoundType sound, int speed, int animationsPerSecond, boolean loopAnimation) {
		this.graphic = graphic;
		this.sound = sound;
		this.speed = speed;
		this.animationsPerSecond = animationsPerSecond;
		this.loopAnimation = loopAnimation;
	}

	public TextureType getGraphic() {
		return graphic;
	}

	public SoundType getSound() {
		return sound;
	}

	public int getSpeed() {
		return speed;
	}

	public int getAnimationsPerSecond() {
		return animationsPerSecond;
	}

	public boolean isLoopAnimation() {
		return loopAnimation;
	}
}
