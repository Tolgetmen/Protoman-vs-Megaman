package com.gdxgame.core;

import java.util.Iterator;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.constants.GameConstants;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.ResourceManager;

/**
 * GDXGame is the entrypoint for each game made with libgdx. It extends the libgdx {@link Game} class
 * to have the screen switching functionality available (check {@link com.badlogic.gdx.Screen} class of libgdx).
 * 
 * We use the Screen functionality to switch between game states
 * 
 */
public class GDXGame extends Game {
	/**
	 * stack of gamestates. the stack always contains at least one gamestate but 
	 * can also contain multiple gamestates at once. Only the gamestate at the top
	 * is updated and rendered. If the stack is empty then the game closes.
	 */
	private Stack<GameState>	gameStates;
	/**
	 * a single reference to a SpriteBatch. it is shared between gamestates to render stuff.
	 */
	private SpriteBatch			spriteBatch;
	/**
	 * a single reference to the Camera object of the game. It is also shared between gamestates.
	 */
	private OrthographicCamera	camera;
	/**
	 * the current input adapter of the game that is currently listening to key/mouse/touch/controller events. 
	 */
	private GameInputAdapter	currentInputAdapter;
	/**
	 * cursor pixmap that is used to hide the mouse cursor. it is disposed at the end of the game
	 */
	private Pixmap				cursor;

	/**
	 * automatically called by {@link Game#create()} method once the game gets created.
	 * Initializes the first game state, the spritebatch, the camera and the game config preference
	 */
	@Override
	public void create() {
		// load the config preference or initialize it to store setting changes
		GameUtils.loadCfgPreference();

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
		if (GameConstants.HIDE_MOUSE) {
			// hide mouse -> create an empty pixmap to set it as a new cursor image
			cursor = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
			Gdx.input.setCursorImage(cursor, 0, 0);
		} else {
			cursor = null;
		}
	}

	/**
	 * automatically called by {@link Game#render()} method per frame.
	 * Each frame the entire screen is cleared by a specified color within the {@link GameConstants}.
	 * Updates and renders the current active game state.
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
	 * automatically called by {@link Game#dispose()} method once the game gets closed or the process gets killed/terminated. 
	 * Cleans up the game's resources by disposing all game states, the resourcemanager and the cursor pixmap.
	 */
	@Override
	public void dispose() {
		// call the dispose method of still opened gamestates
		for (GameState gs : gameStates) {
			gs.dispose();
		}

		// dispose all resources of the game
		ResourceManager.INSTANCE.disposeAllResources();

		// dispose mouse cursor pixmap
		if (cursor != null) {
			cursor.dispose();
		}

		// finally dispose the spritebatch that was created within this class
		spriteBatch.dispose();
	}

	/**
	 * Changes the current gamestate to the given {@code newState}. If the passed {@code newState}
	 * parameter is <b>null</b> then the game will be closed.
	 * <br><br>
	 * Creates the gamestate and the gamestate's logic instance that are defined in the
	 * {@link GameStateType} enum if the gamestate of type {@code newState} is not yet available in the gamestates stack.
	 * Otherwise the existing gamestate will be reused.
	 * <br><br>
	 * To pause the active gamestate (f.e. to pause the game to switch to a menu) and put
	 * another gamestate on top, pass <b>false</b> for the {@code disposeActiveState} parameter. This will
	 * keep the current active gamestate in the gamestates stack and will ignore the {@link GameState#dispose()} call to this gamestate.
	 * <br><br>
	 * When switching back to an already existing gamestate you can choose to reinitialize it (=calling its {@link GameStateLogic#initialize()} method)
	 * or to just continue updating/rendering. To reinizialite it pass <b>true</b> for {@code reinitializeExisting} parameter.
	 * 
	 * @param newState 				new state that should get active. <b>null</b> to close the game.
	 * @param disposeActiveState 	<b>true</b> to call the {@link GameState#dispose()} method of the current active gamestate and to remove it
	 * 								from the gameStates stack.
	 * 								<b>false</b> to keep the current active state in the gamestates stack and to avoid calling
	 * 								its {@link GameState#dispose()} method.
	 * @param reinitializeExisting	<b>true</b> to call {@link GameStateLogic#initialize()} method of already existing gamestate when switching to it
	 * 								<b>false</b> to ignore the {@link GameStateLogic#initialize()} call
	 * @param data					Data that will be forwarded to the active gamestate that is set with this call. Can be <b>null</b>.
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
	 * Checks if a gamestate of type {@code type} is available in the gamestates stack.
	 * 
	 * @param type type of gamestate to be checked
	 * 
	 * @return <b>true</b> when a gamestate of type {@code type} was found. <b>false</b> otherwise.
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
	 * automatically called when a gamestate gets active within a call to {@link #setGameState(GameStateType, boolean, boolean, Object)}
	 * Sets the active inputadapter of the game. Each {@link GameStateLogic} instance is a {@link GameInputAdapter}
	 * to listen to keyboard/mouse/touch/controller events.
	 *  
	 * @param gameInputAdapter active input adapater to listen to keyboard,mouse,touch and controller events
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
