package com.megaman.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * GameStateLogic is one of the core classes besides GameState. Each game consists of multiple gamestates (f.e. show main menu, game, show highscore, game over, ...).
 * Each GameState has its own resources (f.e. audio or graphic files that are important for that state) and its own GameStateLogic instance.
 * The GameStateLogic instance is used to handle the real logic of the gamestate while the GameState instance is used to handle the resource
 * management.
 * 
 * It extends the GameInputAdapter class to listen to keyboard,mouse,touch and controller events.
 * 
 * A GameStateLogic is configured (=which logic is used for which gamestate) within the GameStateType enum.
 * 
 * GameStateLogic has the following attributes:
 * 		game:				reference to the GDXGame instance in order to call the setGameState() method
 * 
 * 		camera:				reference to the GDXGame instance's camera to be able to f.e. move the
 * 							camera around or change its viewport/projection
 * 
 * 		initialized:		flag to define if the logic was already initialized before. This is used
 * 							when a gamestate changes from the pause to the resume state in order to not
 * 							reinitialize the logic and therefore resume from its last position. Check
 * 							the GDXGame setGameState() method for more information.
 * 
 * 		data:				optional data that can be retrieved from the previous gamestate. This is used
 * 							to send information from the previous gamestate to the active gamestate (f.e.
 * 							passing highscore data from the game gamestate to the highscore gamestate that
 * 							gets active after the game gamestate).
 *
 */
public abstract class GameStateLogic extends GameInputAdapter {
	protected final GDXGame	game;
	protected final Camera	camera;
	protected boolean		initialized;
	protected Object		data;

	public GameStateLogic(GDXGame game, Camera camera) {
		super();
		this.game = game;
		this.camera = camera;
		this.initialized = false;
	}

	/**
	 * This method is called when the corresponding gamestate gets active and the logic was not yet
	 * initialized or should get reinitialized (check GDXGame setGameState() method).
	 */
	public abstract void initialize();

	/**
	 * This method is called each frame. It is used to update the logic by deltaTime steps.
	 * 
	 * @param deltaTime time passed between the previous frame and the current frame
	 */
	public abstract void update(float deltaTime);

	/**
	 * This method is called each frame. Is is used to render graphics(sprites,font,etc.).
	 * 
	 * @param spriteBatch reference to the GDXGame SpriteBatch to draw things
	 */
	public abstract void render(SpriteBatch spriteBatch);

	/**
	 * This method is called whenever the window resolution is modified.
	 * 
	 * @param width new window width
	 * @param height new window height
	 */
	public void resize(int width, int height) {

	}

	/**
	 * This method is called whenever the corresponding gamestate enters the pause state. This happens
	 * during the setGameState() call of the GDXGame instance if the corresponding gamestate is set
	 * to inactive..
	 * It can be followed by a call to dispose() if the gamestate gets removed from the game.
	 */
	public void pause() {

	}

	/**
	 * This method is called whenever the corresponding gamestate enters the resume state. This happens
	 * during the setGameState() call of the GDXGame instance if the corresponding gamestate is set to
	 * active again.
	 */
	public void resume() {

	}

	/**
	 * This method is called when the corresponding gamestate's dispose() method is called. It is used
	 * to dispose any resources that were created by the logic itself.
	 */
	public abstract void dispose();
}
