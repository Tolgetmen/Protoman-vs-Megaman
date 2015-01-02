package com.megaman.core;

import java.util.Iterator;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.constants.GameConstants;
import com.megaman.core.utils.ResourceManager;
import com.megaman.enums.GameStateType;

public class GDXGame extends Game {
	private Stack<GameState>	currentState;
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
		currentState = new Stack<GameState>();

		hideMouseCursor(true);

		// set the initial gamestate
		setGameState(GameStateType.getInitialState(), false, false);
		// check if state was successfully set
		// if no -> close game
		if (currentState == null) {
			Gdx.app.exit();
		}
	}

	private void hideMouseCursor(boolean show) {
		Gdx.input.setCursorCatched(show);
	}

	public void setGameState(GameStateType newState, boolean disposeCurrent, boolean resetExisting) {
		if (currentState.size() > 0 && disposeCurrent) {
			// clean up the resources of the current state
			currentState.pop().dispose();
		}

		if (newState != null) {
			// loop through all states and check if newState == already existing state
			// if yes -> set existing state active
			// else -> create a new state
			GameState gameState = null;
			Iterator<GameState> iterator = currentState.iterator();
			while (iterator.hasNext()) {
				GameState gs = iterator.next();
				if (gs.getType() == newState) {
					iterator.remove();
					gameState = gs;
					if (resetExisting) {
						gameState.logic.initialized = false;
					}
					break;
				}
			}

			if (gameState == null) {
				Class<? extends GameState> gameStateClass = newState.getGameStateClass();
				Class<? extends GameLogic> gameLogicClass = newState.getGameLogicClass();

				if (gameStateClass != null) {
					try {
						GameLogic logic = gameLogicClass.getDeclaredConstructor(GDXGame.class, Camera.class, SpriteBatch.class).newInstance(this, camera, spriteBatch);
						gameState = gameStateClass.getDeclaredConstructor(GameStateType.class, GameLogic.class).newInstance(newState, logic);
					} catch (IllegalArgumentException e) {
						Gdx.app.error("SetCurrentGameState", "Illegal arguments", e);
					} catch (NoSuchMethodException e) {
						Gdx.app.error("SetCurrentGameState", "No such method", e);
					} catch (Exception e) {
						Gdx.app.error("SetCurrentGameState", "General exception", e);
					}
				}
			}

			if (gameState != null) {
				currentState.push(gameState);
				setScreen(gameState);
				setGameInput(gameState.logic);
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
		for (GameState gs : currentState) {
			gs.dispose();
		}
		ResourceManager.INSTANCE.disposeAllResources();
	}

	public boolean isGameStateAvailable(GameStateType game) {
		for (GameState gs : currentState) {
			if (gs.getType().equals(game)) {
				return true;
			}
		}
		return false;
	}
}
