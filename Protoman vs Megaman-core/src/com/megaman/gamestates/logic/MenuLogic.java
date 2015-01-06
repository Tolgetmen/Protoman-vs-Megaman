package com.megaman.gamestates.logic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameStateLogic;
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
		menu.render(spriteBatch);
	}

	@Override
	public boolean keyDown(int keycode) {
		menu.keyDown(keycode);

		return true;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		switch (buttonCode) {
			case 0: {
				// xbox A button
				keyDown(Keys.ENTER);
				break;
			}
			case 7: {
				// xbox START button
				keyDown(Keys.ESCAPE);
				break;
			}
		}

		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		switch (value) {
			case north:
			case northEast:
			case northWest: {
				keyDown(Keys.UP);
				break;
			}
			case south:
			case southEast:
			case southWest: {
				keyDown(Keys.DOWN);
				break;
			}
			case east: {
				keyDown(Keys.RIGHT);
				break;
			}
			case west: {
				keyDown(Keys.LEFT);
				break;
			}
			default: {
				break;
			}
		}

		return true;
	}

	@Override
	public void dispose() {
		menu.dispose();
	}
}
