package com.gdxgame.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.enums.GameStateType;

/**
 * GameState is one of the core classes besides GameStateLogic. Each game consists of multiple gamestates (f.e. show main menu, game, show highscore, game over, ...).
 * Each GameState has its own resources (f.e. audio or graphic files that are important for that state) and its own GameStateLogic instance.
 * The GameStateLogic instance is used to handle the real logic of the gamestate while the GameState instance is used to handle the resource
 * management.
 * 
 * It extends the libgdx Screen class so that the GDXGame class can use it for the setScreen() method. The class also forwards the important
 * Screen methods (=show,hide,render,resize,pause,resume and dispose) to its GameStateLogic instance.
 * 
 * A GameState is configured (=which gamestate uses which logic) within the GameStateType enum.				
 *
 */
public abstract class GameState implements Screen {
	/**
	 * the GameStateLogic instance of the gamestate to which Screen method calls are forwarded
	 */
	protected final GameStateLogic	logic;
	/**
	 * the type of the gamestate. This attribute is used by the GDXGame class to check if
	 * a gamestate instance is already part of the gamestates stack.
	 */
	protected final GameStateType	type;

	public GameState(GameStateType type, GameStateLogic logic) {
		super();
		this.type = type;
		this.logic = logic;
	}

	/**
	 * This method is called whenever a gamestate gets active (=setScreen() call in GDXGame class). 
	 * It is used to load the resources of the gamestate and to initialize or unpause the logic.
	 */
	@Override
	public void show() {
		// check if logic is already initialized
		if (!logic.initialized) {
			// if no -> load the resources of the gamestate
			loadResources();
			// initialize the logic
			logic.initialize();
			// and set the logic's initialize flag
			logic.initialized = true;
		} else {
			// if yes -> tell the logic to unpause
			logic.resume();
		}
	}

	/**
	 * This method is called whenever a gamestate gets inactive. It can be followed up by a call to dispose()
	 * if the gamestate gets removed from the game (check setGameState() method of GDXGame class).
	 * The call is forwarded to the logic's pause method.
	 */
	@Override
	public void hide() {
		logic.pause();
	}

	/**
	 * This method is called per frame and will forward this call to the logic's update method.
	 * 
	 * @param deltaTime time passed between the previous frame and the current frame
	 */
	public void update(float deltaTime) {
		logic.update(deltaTime);
	}

	/**
	 * This method is called per frame and will forward this call to the logic's render method.
	 * 
	 * @param spriteBatch reference to the GDXGame SpriteBatch to draw things
	 */
	public void render(SpriteBatch spriteBatch) {
		logic.render(spriteBatch);
	}

	/**
	 * This method is not used anymore since we split up the render call into our own update
	 * and render method within the GDXGame class.
	 */
	@Override
	public void render(float delta) {
	}

	/**
	 * This method is called whenever the window is resized in any way. The call is forwarded
	 * to the logic's resize method.
	 */
	@Override
	public void resize(int width, int height) {
		logic.resize(width, height);
	}

	/**
	 * This method is called only on android devices whenever the game loses focus.
	 * The call is forwarded to the logic's pause method.
	 */
	@Override
	public void pause() {
		logic.pause();
	}

	/**
	 * This method is called only on android devices whenever the game gets focus again.
	 * The call is forwarded to the logic's resume method.
	 */
	@Override
	public void resume() {
		logic.resume();
	}

	/**
	 * This method is called whenever the gamestate gets removed from the game. 
	 * Is is used to dispose all the resources of the logic and the gamestate itself.
	 */
	@Override
	public void dispose() {
		logic.dispose();
		disposeResources();
	}

	/**
	 * This method should contain the logic to load all resources (=graphic,audio,...) for the gamestate.
	 * It is called by the show() method.
	 */
	protected abstract void loadResources();

	/**
	 * This method should contain the logic to dispose all resources (=graphic,audio,...) for the gamestate.
	 * It is called by the dispose() method.
	 */
	protected abstract void disposeResources();
}
