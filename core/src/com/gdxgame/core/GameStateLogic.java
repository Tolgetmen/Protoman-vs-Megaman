package com.gdxgame.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * GameStateLogic is one of the core classes besides {@link GameState} to handle game states. Each game consists of multiple 
 * gamestates (f.e. show main menu, game, show highscore, game over, ...).
 * Each {@link GameState} has its own resources (f.e. audio or graphic files that are important for that state) and its own GameStateLogic instance.
 * The GameStateLogic instance is used to handle the real logic of the game state while the {@link GameState} instance is used to handle the resource
 * management.
 * <br><br>
 * It extends the {@link GameInputAdapter} class to listen to keyboard,mouse,touch and controller events.
 * <br><br>
 * A GameStateLogic is configured (=which logic is used for which gamestate) within the {@link com.gdxgame.core.enums.GameStateType} enum.					
 *
 */
public abstract class GameStateLogic extends GameInputAdapter {
	/**
	 * reference to the GDXGame instance in order to call the setGameState() method
	 */
	protected final GDXGame	game;
	/**
	 * reference to the GDXGame instance's camera to be able to f.e. move the camera around or change its viewport/projection
	 */
	protected final Camera	camera;
	/**
	 * flag to define if the logic was already initialized before. This is used
	 * when a gamestate changes from the pause to the resume state in order to not
	 * reinitialize the logic and therefore resume from its last position. Check
	 * the GDXGame setGameState() method for more information.
	 */
	protected boolean		initialized;
	/**
	 * optional data that can be retrieved from the previous gamestate. This is used
	 * to send information from the previous gamestate to the active gamestate (f.e.
	 * passing highscore data from the game gamestate to the highscore gamestate that
	 * gets active after the game gamestate).
	 */
	protected Object		data;

	public GameStateLogic(GDXGame game, Camera camera) {
		super();
		this.game = game;
		this.camera = camera;
		this.initialized = false;
	}

	/**
	 * automatically called whenever the {@link GameState#show()} method is called and the logic was not initialized yet.
	 * Initializes the game state logic.
	 */
	public abstract void initialize();

	/**
	 * automatically called whenever the {@link GameState#update(float)} method is called.
	 * Updates the game state logic.
	 * 
	 * @param deltaTime time passed since last frame
	 */
	public abstract void update(float deltaTime);

	/**
	 * automatically called whenever the {@link GameState#render(SpriteBatch)} method is called.
	 * Renders the game state logic.
	 * 
	 * @param spriteBatch reference to the {@link GDXGame} SpriteBatch to draw things
	 */
	public abstract void render(SpriteBatch spriteBatch);

	/**
	 * automatically called whenever the {@link GameState#resize(int, int)} method is called.
	 * Handles any resize logic.
	 * 
	 * @param width new window width
	 * @param height new window height
	 */
	public void resize(int width, int height) {

	}

	/**
	 * automatically called whenever the {@link GameState#hide()} or {@link GameState#pause()} method is called.
	 * It can be followed by a call to {@link #dispose()} if the game state logic is no longer needed.
	 * Pauses the game state logic.
	 */
	public void pause() {

	}

	/**
	 * automatically called whenever the {@link GameState#show()} or {@link GameState#resume()} method is called.
	 * Resumes the game state logic.
	 */
	public void resume() {

	}

	/**
	 * automatically called whenever the {@link GameState#dispose()} method is called.
	 * Disposes all resources of the game state logic.
	 */
	public abstract void dispose();
}
