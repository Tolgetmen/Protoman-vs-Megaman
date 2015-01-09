package com.megaman.gamestates;

import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameState;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.utils.ResourceManager;
import com.megaman.constants.MegamanConstants;

public class GSGame extends GameState {
	public GSGame(GameStateType type, GameStateLogic logic) {
		super(type, logic);
	}

	@Override
	protected void loadResources() {
		// load texture atlas
		ResourceManager.INSTANCE.loadTextureAtlas(GameConstants.ATLAS_PATH_GAME);
		ResourceManager.INSTANCE.loadTextureAtlas(GameConstants.ATLAS_PATH_HUD);

		// hud sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.HUD_LIFE);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.HUD_MISSLES);

		// load character sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_GEMINIMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_HARDMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_MAGNETMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_NEEDLEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_PROTOMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_SHADOWMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_SNAKEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_SPARKMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_TOPMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.CHARACTER_METTOOL);

		// effect sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.EFFECT_HIT);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.EFFECT_DEATH);

		// load missile sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_GEMINIMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_HARDMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_MAGNETMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_NEEDLEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_SHADOWMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_SNAKEMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_SPARKMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.MISSILE_TOPMAN);

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
		ResourceManager.INSTANCE.loadSound(SoundType.HIT);
		ResourceManager.INSTANCE.loadSound(SoundType.DEATH);

		// load map
		ResourceManager.INSTANCE.loadTMXMap(MegamanConstants.BACKGROUND_MAP_PATH);
	}

	@Override
	protected void disposeResources() {
		// free texture atlas and sprites
		ResourceManager.INSTANCE.disposeTextureAtlasAndSprites(GameConstants.ATLAS_PATH_GAME);
		ResourceManager.INSTANCE.disposeTextureAtlasAndSprites(GameConstants.ATLAS_PATH_HUD);

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
		ResourceManager.INSTANCE.disposeSound(SoundType.DEATH);

		// load map
		ResourceManager.INSTANCE.disposeTMXMap(MegamanConstants.BACKGROUND_MAP_PATH);
	}
}
