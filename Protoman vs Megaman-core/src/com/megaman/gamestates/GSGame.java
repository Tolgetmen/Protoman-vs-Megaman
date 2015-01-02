package com.megaman.gamestates;

import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SoundType;
import com.megaman.core.enums.TextureType;
import com.megaman.core.utils.ResourceManager;

public class GSGame extends GameState {
	public GSGame(GameStateType type, GameLogic logic) {
		super(type, logic);
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
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_CHARACTER_METTOOL);

		// effect sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_EFFECT_HIT);

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
		ResourceManager.INSTANCE.loadMusic(MusicType.GEMINIMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.HARDMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.MAGNETMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.NEEDLEMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SHADOWMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SNAKEMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SPARKMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.TOPMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.PROTOMAN);

		// load sounds
		ResourceManager.INSTANCE.loadSound(SoundType.BLOCK);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_GEMINIMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_HARDMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_MAGNETMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_MEGAMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_NEEDLEMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_SHADOWMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_SNAKEMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_SPARKMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_TOPMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.SHOOT_TOPMAN);
		ResourceManager.INSTANCE.loadSound(SoundType.HIT);
	}

	@Override
	protected void disposeResources() {
		// free texture atlas and sprites
		ResourceManager.INSTANCE.disposeTextureAtlasAndSprites(GameConstants.ATLAS_PATH_GAME);

		// free music
		ResourceManager.INSTANCE.disposeMusic(MusicType.GEMINIMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.HARDMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.MAGNETMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.NEEDLEMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SHADOWMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SNAKEMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SPARKMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.TOPMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.PROTOMAN);

		// free sounds
		ResourceManager.INSTANCE.disposeSound(SoundType.BLOCK);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_GEMINIMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_HARDMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_MAGNETMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_MEGAMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_NEEDLEMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_SHADOWMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_SNAKEMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_SPARKMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.SHOOT_TOPMAN);
		ResourceManager.INSTANCE.disposeSound(SoundType.HIT);
	}
}
