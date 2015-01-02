package com.megaman.gamestates.logic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameLogic;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.utils.SoundManager;
import com.megaman.menu.HighscoreMenu;

public class GSHighscoreLogic extends GameLogic implements OnCompletionListener {
	private HighscoreMenu	menu;
	private boolean			quitGame;

	public GSHighscoreLogic(GDXGame game, Camera camera, SpriteBatch spriteBatch) {
		super(game, camera, spriteBatch);
	}

	@Override
	public void initialize() {
		SoundManager.INSTANCE.playMusic(MusicType.WILY_STAGE, true);

		menu = new HighscoreMenu(this, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT, true, GameMenuPageType.HIGHSCORE, data);
	}

	@Override
	public void update(float deltaTime) {
		menu.update(deltaTime);
	}

	@Override
	public void render() {
		menu.render(spriteBatch, camera);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!quitGame) {
			menu.keyDown(keycode);
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
			default: {
				break;
			}
		}

		return true;
	}

	@Override
	public void onCompletion(Music music) {
		// used for "Quit game" option
		// wait until protoman music was played to close the game
		game.setGameState(null, true, true);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		menu.dispose();
	}

	public void changeGameState(GameStateType newState, boolean resetExisting) {
		if (newState != null) {
			game.setGameState(newState, true, resetExisting);
		} else {
			// quit game
			SoundManager.INSTANCE.playMusic(MusicType.MENU_QUIT, false, this);
			quitGame = true;
		}
	}
}
