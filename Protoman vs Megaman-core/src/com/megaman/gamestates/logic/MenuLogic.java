package com.megaman.gamestates.logic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.core.GDXGame;
import com.megaman.core.GameStateLogic;
import com.megaman.menu.MegamanMenu;

public abstract class MenuLogic extends GameStateLogic {
	protected MegamanMenu	menu;

	public MenuLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	@Override
	public void update(float deltaTime) {
		menu.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		menu.render(spriteBatch, camera);
	}

	@Override
	public boolean keyDown(int keycode) {
		menu.keyDown(keycode);

		return true;
	}

	@Override
	public void dispose() {
		menu.dispose();
	}
}
