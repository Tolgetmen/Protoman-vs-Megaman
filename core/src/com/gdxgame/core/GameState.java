package com.gdxgame.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.enums.GameStateType;

/**
 * 
 * GameState is one of the core classes besides {@link GameStateLogic} to handle game states. Each game consists of multiple gamestates
 * (f.e. show main menu, game, show highscore, game over, ...).
 * Each GameState has its own resources (f.e. audio or graphic files that are important for that state) and its own {@link GameStateLogic} instance.
 * The {@link GameStateLogic} instance is used to handle the real logic of the gamestate while the GameState instance is used to handle the resource
 * management.
 * <br><br>
 * It extends the libgdx {@link Screen} class so that the {@link GDXGame} class can use it for the {@link GDXGame#setScreen(Screen)} method. 
 * The class also forwards the important {@link Screen} methods (=show,hide,render,resize,pause,resume and dispose) to its {@link GameStateLogic} instance.
 * <br><br>
 * A GameState is configured (=which gamestate uses which logic) within the {@link GameStateType} enum.				
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
	 * automatically called whenever the {@link GDXGame#setScreen(Screen)} method is called and the gamestate gets active.
	 * This normally happens within the {@link GDXGame#setGameState(GameStateType, boolean, boolean, Object)} method.
	 * It is used to load the resources of the gamestate by calling its {@link #loadResources()} method and to make a call to the
	 * logic's {@link GameStateLogic#initialize()} or {@link GameStateLogic#resume()} method.
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
	 * automatically called whenever the {@link GDXGame#setScreen(Screen)} method is called and the gamestate gets inactive.
	 * This normally happens within the {@link GDXGame#setGameState(GameStateType, boolean, boolean, Object)} method.
	 * It can be followed up by a call to {@link #dispose()} if the gamestate is no longer needed and gets removed.
	 * The call is forwarded to the logic's {@link GameStateLogic#pause()} method.
	 */
	@Override
	public void hide() {
		logic.pause();
	}

	/**
	 * automatically called whenever the {@link GDXGame#render()} method is called and the gamestate is active.
	 * Updates the gamestate by forwarding the call to the {@link GameStateLogic#update(float)} method.
	 * 
	 * @param deltaTime time passed since last frame
	 */
	public void update(float deltaTime) {
		logic.update(deltaTime);
	}

	/**
	 * automatically called whenever the {@link GDXGame#render()} method is called and the gamestate is active.
	 * Renders the gamestate by forwarding the call to the {@link GameStateLogic#render(SpriteBatch)} method.
	 * 
	 * @param spriteBatch reference to the {@link GDXGame} SpriteBatch to draw things
	 */
	public void render(SpriteBatch spriteBatch) {
		logic.render(spriteBatch);
	}

	/**
	 * This method is not used anymore since we split up the render call into our own update
	 * and render method within the {@link GDXGame} class.<br>
	 * <b>Do not use this method</b>
	 */
	@Override
	public void render(float delta) {
	}

	/**
	 * automatically called whenever the {@link GDXGame#resize(int, int)} method is called and the gamestate is active.
	 * Handles the gamestate resize logic by forwarding the call to the {@link GameStateLogic#resize(int, int)} method.
	 */
	@Override
	public void resize(int width, int height) {
		logic.resize(width, height);
	}

	/**
	 * This method is called only on android devices whenever the game loses focus.
	 * Pauses the gamestate by forwarding the call to the {@link GameStateLogic#pause()} method.
	 */
	@Override
	public void pause() {
		logic.pause();
	}

	/**
	 * automatically called whenever the {@link GDXGame#setScreen(Screen)} method is called and the gamestate gets active and was already initialized.
	 * This normally happens within the {@link GDXGame#setGameState(GameStateType, boolean, boolean, Object)} method.
	 * It is also called on android devices whenever the game gains focus again.
	 * Resumes the gamestate by forwarding the call to the {@link GameStateLogic#resume()} method.
	 */
	@Override
	public void resume() {
		logic.resume();
	}

	/**
	 * automatically called whenever the {@link GDXGame#setScreen(Screen)} method is called and the gamestate gets inactive and is no longer needed.
	 * This normally happens within the {@link GDXGame#setGameState(GameStateType, boolean, boolean, Object)} method.
	 * Disposes the gamestate's resources by forwarding the call to the {@link GameStateLogic#dispose()} method and to the {@link #disposeResources()} method.
	 */
	@Override
	public void dispose() {
		logic.dispose();
		disposeResources();
	}

	/**
	 * This method should contain the logic to load all resources (=graphic,audio,...) for the gamestate.
	 * It is called by the {@link #show()} method.
	 */
	protected abstract void loadResources();

	/**
	 * This method should contain the logic to dispose all resources (=graphic,audio,...) for the gamestate.
	 * It is called by the {@link #dispose()} method.
	 */
	protected abstract void disposeResources();
}
