package com.megaman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameInputAdapter;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.ResourceManager;
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
		setCurrentGameState(GameStateType.getInitialState());
		// check if state was successfully set
		// if no -> close game
		if (currentState == null) {
			Gdx.app.exit();
		}
	}

	private void hideMouseCursor(boolean show) {
		Gdx.input.setCursorCatched(show);
	}

	private void setCurrentGameState(GameStateType newState) {
		if (newState != null) {
			Class<? extends GameState> gameStateClass = newState.getGameStateClass();
			Class<? extends GameLogic> gameLogicClass = newState.getGameLogicClass();
			GameState gameState = null;

			if (gameStateClass != null) {
				try {
					gameState = gameStateClass.getDeclaredConstructor(GDXGame.class, Camera.class, SpriteBatch.class, GameLogic.class.getClass()).newInstance(this, camera, spriteBatch, gameLogicClass);
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
		}
	}

	public void closeCurrentGameState() {
		// clean up the resources of the current state
		currentState.dispose();

		// check if there is new state to be set
		GameStateType nextState = currentState.getNextState();
		currentState = null;
		if (nextState != null) {
			// set the new state as new current state
			setCurrentGameState(nextState);
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
