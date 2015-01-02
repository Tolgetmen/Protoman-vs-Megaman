package com.megaman.gamestates;

import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.utils.ResourceManager;
import com.megaman.enums.AudioType;
import com.megaman.enums.GameStateType;
import com.megaman.enums.SkinType;
import com.megaman.enums.TextureType;

public class GSMainMenu extends GameState {
	public GSMainMenu(GameStateType type, GameLogic logic) {
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
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MENU);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MENU_QUIT);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_GEMINIMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_HARDMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MAGNETMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_NEEDLEMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_PROTOMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SHADOWMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SNAKEMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_SPARKMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_TOPMAN);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_WILY_STAGE);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MENU_QUIT);

		// load sounds
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_MOVE);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_SELECT);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_SELECT_SHOOT);
	}

	@Override
	protected void disposeResources() {
		// dispose skin, textureatlas and sprites
		ResourceManager.INSTANCE.disposeSkin(SkinType.SKIN_MAIN_MENU);

		// dispose music
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MENU);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MENU_QUIT);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_GEMINIMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_HARDMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MAGNETMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_NEEDLEMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_PROTOMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SHADOWMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SNAKEMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_SPARKMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_TOPMAN);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_WILY_STAGE);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MENU_QUIT);

		// dispose sounds
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_MOVE);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_SELECT);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_SELECT_SHOOT);

	}

}
