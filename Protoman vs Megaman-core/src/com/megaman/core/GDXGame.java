package com.megaman.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.constants.GameConstants;
import com.megaman.enums.GameStateType;

public class GDXGame extends Game {
	private GameState			currentState;
	private SpriteBatch			spriteBatch;
	private OrthographicCamera	camera;
	private GameInputAdapter	currentInputAdapter;

	/**
	 * create is called once when the application is created. 
	 * 
	 * it contains the logic to load resources like textures,audiofiles,etc..
	 */
	@Override
	public void create() {
		Gdx.app.setLogLevel(GameConstants.LOG_LEVEL);

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);

		hideMouseCursor(true);

		// set the initial gamestate
		setGameState(GameStateType.getInitialState());
		// check if state was successfully set
		// if no -> close game
		if (currentState == null) {
			Gdx.app.exit();
		}
	}

	private void hideMouseCursor(boolean show) {
		Gdx.input.setCursorCatched(show);
	}

	public void setGameState(GameStateType newState) {
		if (currentState != null) {
			// clean up the resources of the current state
			currentState.dispose();
			currentState = null;
		}

		if (newState != null) {
			Class<? extends GameState> gameStateClass = newState.getGameStateClass();
			Class<? extends GameLogic> gameLogicClass = newState.getGameLogicClass();
			GameState gameState = null;

			if (gameStateClass != null) {
				try {
					GameLogic logic = gameLogicClass.getDeclaredConstructor(GDXGame.class, Camera.class, SpriteBatch.class).newInstance(this, camera, spriteBatch);
					gameState = gameStateClass.getDeclaredConstructor(GameLogic.class).newInstance(logic);
					setGameInput(logic);
				} catch (IllegalArgumentException e) {
					Gdx.app.error("SetCurrentGameState", "Illegal arguments", e);
				} catch (NoSuchMethodException e) {
					Gdx.app.error("SetCurrentGameState", "No such method", e);
				} catch (Exception e) {
					Gdx.app.error("SetCurrentGameState", "General exception", e);
				}

				if (gameState != null) {
					currentState = gameState;
					setScreen(currentState);
				}
			}
		} else {
			// there is no other state to be set -> close the game
			Gdx.app.exit();
		}
	}

	public void setGameInput(GameInputAdapter gameInputAdapter) {
		if (gameInputAdapter != null) {
			Gdx.input.setInputProcessor(gameInputAdapter);
			Controllers.addListener(gameInputAdapter);

			if (currentInputAdapter != null) {
				Controllers.removeListener(currentInputAdapter);
			}
			currentInputAdapter = gameInputAdapter;
		}
	}

	@Override
	public void render() {
		// important line -> calls the current screen's render method
		super.render();
	}

	/**
	 * dispose is called whenver the game is destroyed
	 * 
	 * the dispose() method is used to cleanup resources like textures, audiofiles, etc..
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
		if (currentState != null) {
			currentState.dispose();
		}
		ResourceManager.INSTANCE.disposeAllResources();
	}
}
