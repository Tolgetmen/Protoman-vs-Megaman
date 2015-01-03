package com.megaman.gamestates;

import com.megaman.core.GameStateLogic;
import com.megaman.core.GameState;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SkinType;
import com.megaman.core.enums.SoundType;
import com.megaman.core.enums.TextureType;
import com.megaman.core.utils.ResourceManager;

public class GSMainMenu extends GameState {
	public GSMainMenu(GameStateType type, GameStateLogic logic) {
		super(type, logic);
	}

	@Override
	protected void loadResources() {
		// load skin and texture atlas
		ResourceManager.INSTANCE.loadSkin(SkinType.SKIN_MAIN_MENU);

		// load sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_PROTOMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_MISSLE);

		// load music
		ResourceManager.INSTANCE.loadMusic(MusicType.MENU);
		ResourceManager.INSTANCE.loadMusic(MusicType.GEMINIMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.HARDMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.MAGNETMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.NEEDLEMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.PROTOMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SHADOWMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SNAKEMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.SPARKMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.TOPMAN);
		ResourceManager.INSTANCE.loadMusic(MusicType.WILY_STAGE);
		ResourceManager.INSTANCE.loadMusic(MusicType.MENU_QUIT);

		// load sounds
		ResourceManager.INSTANCE.loadSound(SoundType.MENU_MOVE);
		ResourceManager.INSTANCE.loadSound(SoundType.MENU_SELECT);
		ResourceManager.INSTANCE.loadSound(SoundType.MENU_SELECT_SHOOT);
	}

	@Override
	protected void disposeResources() {
		// dispose skin, textureatlas and sprites
		ResourceManager.INSTANCE.disposeSkin(SkinType.SKIN_MAIN_MENU);

		// dispose music
		ResourceManager.INSTANCE.disposeMusic(MusicType.MENU);
		ResourceManager.INSTANCE.disposeMusic(MusicType.MENU_QUIT);
		ResourceManager.INSTANCE.disposeMusic(MusicType.GEMINIMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.HARDMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.MAGNETMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.NEEDLEMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.PROTOMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SHADOWMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SNAKEMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.SPARKMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.TOPMAN);
		ResourceManager.INSTANCE.disposeMusic(MusicType.WILY_STAGE);

		// dispose sounds
		ResourceManager.INSTANCE.disposeSound(SoundType.MENU_MOVE);
		ResourceManager.INSTANCE.disposeSound(SoundType.MENU_SELECT);
		ResourceManager.INSTANCE.disposeSound(SoundType.MENU_SELECT_SHOOT);

	}

}
