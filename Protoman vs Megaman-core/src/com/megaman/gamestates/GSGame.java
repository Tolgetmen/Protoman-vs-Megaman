package com.megaman.gamestates;

import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.ResourceManager;
import com.megaman.enums.AudioType;
import com.megaman.enums.TextureType;

public class GSGame extends GameState {
	public GSGame(GameLogic logic) {
		super(logic);
	}

	@Override
	protected void loadResources() {
		// load texture atlas
		ResourceManager.INSTANCE.loadTextureAtlas(GameConstants.ATLAS_PATH_GAME);

		// load character sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_GEMINIMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_HARDMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_MAGNETMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_NEEDLEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_PROTOMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_SHADOWMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_SNAKEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_SPARKMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_TOPMAN);

		// load missile sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_GEMINIMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_HARDMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_MAGNETMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_NEEDLEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_SHADOWMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_SNAKEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_SPARKMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MISSILE_TOPMAN);

		// load music
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_GEMINIMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_HARDMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MAGNETMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_NEEDLEMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SHADOWMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SNAKEMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SPARKMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_TOPMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_PROTOMAN);

		// load sounds
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_BLOCK);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_GEMINIMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_HARDMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_MAGNETMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_MEGAMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_NEEDLEMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_SHADOWMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_SNAKEMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_SPARKMAN);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_SHOOT_TOPMAN);
	}

	@Override
	protected void disposeResources() {
		// free texture atlas and sprites
		ResourceManager.INSTANCE.disposeTextureAtlasAndSprites(GameConstants.ATLAS_PATH_GAME);

		// free music
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_GEMINIMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_HARDMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MAGNETMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_NEEDLEMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SHADOWMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SNAKEMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SPARKMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_TOPMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_PROTOMAN);

		// free sounds
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_BLOCK);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_GEMINIMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_HARDMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_MAGNETMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_MEGAMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_NEEDLEMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_SHADOWMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_SNAKEMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_SPARKMAN);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_SHOOT_TOPMAN);
	}
}
