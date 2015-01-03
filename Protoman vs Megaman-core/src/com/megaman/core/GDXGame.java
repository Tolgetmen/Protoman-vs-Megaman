package com.megaman.core;

import java.util.Iterator;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.constants.GameConstants;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.utils.ResourceManager;

/**
 * GDXGame is the entrypoint for each game made with libgdx. It extends the libgdx Game class
 * to have the screen switching functionality available (check Screen class of libgdx).
 * 
 * We use the Screen functionality to switch between game states
 * 
 * GDXGame has the following attributes:
 * 
 * 		gameStates: 			stack of gamestates. the stack always contains at least one gamestate but 
 * 								can also contain multiple gamestates at once. Only the gamestate at the top
 * 								is updated and rendered. If the stack is empty then the game closes.
 * 
 * 		spriteBatch: 			a single reference to a SpriteBatch. it is shared between gamestates to render stuff.
 * 
 * 		camera: 				a single reference to the Camera object of the game. It is also shared between gamestates.
 * 
 * 		currentInputAdapter: 	the current input adapter of the game that is currently listening to 
 * 							 	key/mouse/touch/controller events. 
 *
 */
public class GDXGame extends Game {
	private Stack<GameState>	gameStates;
	private SpriteBatch			spriteBatch;
	private OrthographicCamera	camera;
	private GameInputAdapter	currentInputAdapter;

	/**
	 * create() is called once when the game is created.
	 * 
	 * We use it to initialize the first gamestate and to create the SpriteBatch and Camera.
	 */
	@Override
	public void create() {
		// set the log level taken from the game constants
		// to change the log level (ALL,info,error,none) you need to change the LOG_LEVE constant
		Gdx.app.setLogLevel(GameConstants.LOG_LEVEL);

		// initialize the spritebatch and camera
		spriteBatch = new SpriteBatch();
		// use an orthographiccamera for 2D games to have an intuitive two dimensional viewport
		// for the game. 
		// we do not support different resolutions for the game right now. Everything is stretched
		// to fit higher resolutions. 
		camera = new OrthographicCamera();
		// the fixed viewport size of the camera is taken from the gameconstants. Change the GAME_WIDTH
		// and GAME_HEIGHT value if you need different game resolutions.
		camera.setToOrtho(false, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);

		// set the startup gamestate of the game. Gamestates are defined in the GameStateType
		// enum. Check the "isInitialState" attribute there.
		setGameState(GameStateType.getInitialState(), true, true, null);

		// show(=false) or hide(=true) the mousecursor of the game inside the game window
		Gdx.input.setCursorCatched(GameConstants.HIDE_MOUSE);
	}

	/**
	 * render() is called per frame and will forward this call to the current active gamestate.
	 * Each frame the entire screen is cleared by specified color within the GameConstants.
	 * 
	 * We use it to call the current gamestate's update() and render() method
	 */
	@Override
	public void render() {
		if (gameStates.size() > 0) {
			// split this call into an update and a render call to seperate the logic accordingly
			// call the update method of the active gamestate with the current deltatime(=time between previous and current frame)
			gameStates.peek().update(Gdx.graphics.getDeltaTime());
		}

		if (gameStates.size() > 0) {
			// start the render process by first clearing the screen
			Gdx.gl.glClearColor(GameConstants.CLEAR_COLOR_RED, GameConstants.CLEAR_COLOR_GREEN, GameConstants.CLEAR_COLOR_BLUE, GameConstants.CLEAR_COLOR_ALPHA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// update any camera transformations
			camera.update();
			// set the current projection matrix to the current camera's combined view and projection matrix
			spriteBatch.setProjectionMatrix(camera.combined);

			// start the render process and call the active gamestate's render method
			gameStates.peek().render(spriteBatch);
		}
	}

	/**
	 * dispose() is called when the game is closing or the game process is getting killed/terminated.
	 * 
	 * We use it to cleanup any game resources that are still not yet disposed by the game.
	 */
	@Override
	public void dispose() {
		// call the dispose method of still opened gamestates
		for (GameState gs : gameStates) {
			gs.dispose();
		}
		// dispose all resources of the game
		ResourceManager.INSTANCE.disposeAllResources();
		// finally dispose the spritebatch that was created within this class
		spriteBatch.dispose();
	}

	/**
	 * This method is called whenever the current gamestate should be changed. If the passed <b>newState</b>
	 * parameter is <b>null</b> then the game will be closed.
	 * 
	 * It automatically creates the gamestate and the gamestate's logic instance that are defined in the
	 * GameStateType enum if the gamestate of type <b>newState</b> is not yet available in the gamestates stack.
	 * Otherwise we will reuse the already existing gamestate.
	 * 
	 * If we want to just pause the active gamestate (f.e. to pause the game to switch to a menu) and put
	 * another gamestate on top, simply pass <b>false</b> for the <b>disposeActiveState</b> parameter. This will
	 * keep the current active gamestate in the gamestates stack and will ignore the dispose() call to this gamestate.
	 * 
	 * When switching back to an already existing gamestate we can choose to reinitialize it (=calling its initialize()
	 * method) or to just continue updating/rendering.
	 * 
	 * @param newState 				new state that should get active. <b>null</b> to close the game.
	 * @param disposeActiveState 	<b>true</b> to call the dispose() method of the current active gamestate and to remove it
	 * 								from the gameStates stack.
	 * 								<b>false</b> to keep the current active state in the gamestates stack and to avoid calling
	 * 								its dispose() method.
	 * @param reinitializeExisting	<b>true</b> to call initialize() method of already existing gamestate when switching to it
	 * 								<b>false</b> to ignore the initialize() call
	 * @param data					Data that will be forwarded to the active gamestate that is set with this call. Can be null.
	 */
	public void setGameState(GameStateType newState, boolean disposeActiveState, boolean reinitializeExisting, Object data) {
		if (gameStates == null) {
			// initialize the gamestates stack
			// the state at the top of the stack is the current active state
			gameStates = new Stack<GameState>();
		}

		if (gameStates.size() > 0 && disposeActiveState) {
			// clean up the resources of the current state and remove it from the stack
			gameStates.pop().dispose();
		}

		if (newState != null) {
			// a new active gamestate should be set
			// gameState is the state that will get active at the end of this method
			GameState gameState = null;

			// first check if there is already an existing gamestate of type newState
			Iterator<GameState> iterator = gameStates.iterator();
			while (iterator.hasNext()) {
				GameState gs = iterator.next();
				if (gs.type.equals(newState)) {
					// found gamestate of type newState
					// remove it from the stack to put it on top at the end
					iterator.remove();
					// remember the state for later
					gameState = gs;
					// check if initialize() method of the state should be called
					if (reinitializeExisting) {
						// if yes -> clear the initialized flag of the gamestate
						// this will result in an initialize() call when the gamestate
						// gets active
						gameState.logic.initialized = false;
					}
					break;
				}
			}

			if (gameState == null) {
				// did not find a gamestate of type newState in the gamestate stack
				// --> create a new GameState and GameStateLogic instance that must be defined
				// in the GameStateType enum.
				Class<? extends GameState> gameStateClass = newState.getGameStateClass();
				Class<? extends GameStateLogic> gameLogicClass = newState.getGameLogicClass();

				if (gameStateClass != null && gameLogicClass != null) {
					try {
						GameStateLogic logic = gameLogicClass.getDeclaredConstructor(GDXGame.class, Camera.class).newInstance(this, camera);
						gameState = gameStateClass.getDeclaredConstructor(GameStateType.class, GameStateLogic.class).newInstance(newState, logic);
					} catch (IllegalArgumentException e) {
						Gdx.app.error(GameConstants.LOG_TAG_ERROR, "Illegal arguments for setGameState", e);
					} catch (NoSuchMethodException e) {
						Gdx.app.error(GameConstants.LOG_TAG_ERROR, "No such method for setGameState", e);
					} catch (Exception e) {
						Gdx.app.error(GameConstants.LOG_TAG_ERROR, "General exception for setGameState", e);
					}
				} else {
					Gdx.app.error(GameConstants.LOG_TAG_ERROR, "GameStateType enum is not properly configured. Either a GameStateLogic or GameState class is not defined for type: " + newState);
				}
			}

			if (gameState != null) {
				// found an existing gamestate of type newState or successfully created a new instance for type newState
				// put it on top of the stack to set it as active
				gameStates.push(gameState);
				// forward any data to the new active gamestate
				gameState.logic.data = data;
				// setScreen calls the pause() method of the previous gameState and the initialize() or resume() method
				// of the active gameState
				setScreen(gameState);
				// set the active gamestate's logic as inputAdapter of the game
				setGameInput(gameState.logic);
			} else {
				// Could not find existing gamestate or could not create a new instance -> close the game
				Gdx.app.error(GameConstants.LOG_TAG_ERROR, "Could not find/create gamestate of type: " + newState);
				Gdx.app.exit();
			}
		} else {
			// there is no other state to be set -> close the game
			Gdx.app.debug(GameConstants.LOG_TAG_DEBUG, "Setting gamestate null. Game is getting closed now.");
			Gdx.app.exit();
		}
	}

	/**
	 * Checks if a gamestate of type <b>type</b> is available in the gamestates stack.
	 * 
	 * @param type type of gamestate to be checked
	 * @return <b>true</b> when a gamestate of type <b>type</b> was found. <b>false</b> otherwise.
	 */
	public boolean isGameStateAvailable(GameStateType type) {
		for (GameState gs : gameStates) {
			if (gs.type.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is automatically called from the setGameState method when a gamestate gets active.
	 * 
	 * Sets the active inputadapter of the game. Each GameStateLogic instance is a GameInputAdapter
	 * to listen to keyboard/mouse/touch/controller events.
	 *  
	 * @param gameInputAdapter
	 */
	private void setGameInput(GameInputAdapter gameInputAdapter) {
		if (currentInputAdapter != null) {
			// remove the previous inputadapter as Controllerlistener
			Controllers.removeListener(currentInputAdapter);
		}

		// set the active inputadapter as new libgdx inputprocessor to listen to keyboard,mouse and touch events
		Gdx.input.setInputProcessor(gameInputAdapter);
		// add the active inputadapter as Controllerlistener to listen to controller events
		Controllers.addListener(gameInputAdapter);
		// remember the active inputadapter for later to remove it as Controllerlistener if no longer needed
		currentInputAdapter = gameInputAdapter;
	}
}
