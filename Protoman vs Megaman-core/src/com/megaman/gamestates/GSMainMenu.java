package com.megaman.gamestates;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.GDXGame;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.ResourceManager;
import com.megaman.enums.AudioType;
import com.megaman.enums.SkinType;
import com.megaman.enums.TextureType;

public class GSMainMenu extends GameState {
	public GSMainMenu(GDXGame game, Camera camera, SpriteBatch spriteBatch, Class<? extends GameLogic> logicClass) {
		super(game, camera, spriteBatch, logicClass);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// load skin and texture atlas
		ResourceManager.INSTANCE.loadSkin(SkinType.SKIN_MAIN_MENU);

		// load sprites
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_MEGAMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_PROTOMAN);
		ResourceManager.INSTANCE.loadAnimatedSprite(TextureType.TEXTURE_MENU_MISSLE);

		// load music
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MENU);
		ResourceManager.INSTANCE.loadMusic(AudioType.MUSIC_MENU_QUIT);

		// load sounds
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_MOVE);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_SELECT);
		ResourceManager.INSTANCE.loadSound(AudioType.SOUND_MENU_SELECT_SHOOT);

		logic.initialize();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// dispose skin, textureatlas and sprites
		ResourceManager.INSTANCE.disposeSkin(SkinType.SKIN_MAIN_MENU);

		// dispose music
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MENU);
		ResourceManager.INSTANCE.disposeMusic(AudioType.MUSIC_MENU_QUIT);

		// dispose sounds
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_MOVE);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_SELECT);
		ResourceManager.INSTANCE.disposeSound(AudioType.SOUND_MENU_SELECT_SHOOT);

		logic.dispose();
	}

}
