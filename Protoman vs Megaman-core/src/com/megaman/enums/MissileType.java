package com.megaman.enums;

public enum MissileType {
	MEGAMAN(TextureType.TEXTURE_MISSILE_MEGAMAN, AudioType.SOUND_SHOOT_MEGAMAN, null, 150, 0, false),
	SPARKMAN(TextureType.TEXTURE_MISSILE_SPARKMAN, AudioType.SOUND_SHOOT_SPARKMAN, AudioType.MUSIC_SPARKMAN, 330, 8, false),
	SHADOWMAN(TextureType.TEXTURE_MISSILE_SHADOWMAN, AudioType.SOUND_SHOOT_SHADOWMAN, AudioType.MUSIC_SHADOWMAN, 330, 12, true),
	SNAKEMAN(TextureType.TEXTURE_MISSILE_SNAKEMAN, AudioType.SOUND_SHOOT_SNAKEMAN, AudioType.MUSIC_SNAKEMAN, 330, 12, true),
	NEEDLEMAN(TextureType.TEXTURE_MISSILE_NEEDLEMAN, AudioType.SOUND_SHOOT_NEEDLEMAN, AudioType.MUSIC_NEEDLEMAN, 330, 0, false),
	HARDMAN(TextureType.TEXTURE_MISSILE_HARDMAN, AudioType.SOUND_SHOOT_HARDMAN, AudioType.MUSIC_HARDMAN, 330, 0, false),
	TOPMAN(TextureType.TEXTURE_MISSILE_TOPMAN, AudioType.SOUND_SHOOT_TOPMAN, AudioType.MUSIC_TOPMAN, 330, 12, true),
	GEMINIMAN(TextureType.TEXTURE_MISSILE_GEMINIMAN, AudioType.SOUND_SHOOT_GEMINIMAN, AudioType.MUSIC_GEMINIMAN, 330, 8, false),
	MAGNETMAN(TextureType.TEXTURE_MISSILE_MAGNETMAN, AudioType.SOUND_SHOOT_MAGNETMAN, AudioType.MUSIC_MAGNETMAN, 330, 0, false);

	private final TextureType	graphic;
	private final AudioType		sound;
	private final AudioType		music;
	private final int			speed;
	private final int			animationsPerSecond;
	private final boolean		loopAnimation;

	private MissileType(TextureType graphic, AudioType sound, AudioType music, int speed, int animationsPerSecond, boolean loopAnimation) {
		this.graphic = graphic;
		this.sound = sound;
		this.music = music;
		this.speed = speed;
		this.animationsPerSecond = animationsPerSecond;
		this.loopAnimation = loopAnimation;
	}

	public TextureType getGraphic() {
		return graphic;
	}

	public AudioType getSound() {
		return sound;
	}

	public AudioType getMusic() {
		return music;
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
