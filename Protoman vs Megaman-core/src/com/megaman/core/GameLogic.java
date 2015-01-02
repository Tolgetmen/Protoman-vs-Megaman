package com.megaman.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameLogic extends GameInputAdapter {
	// each state holds a reference to the "Game" instance in order to call the "closeCurrentGameState" method
	protected final GDXGame		game;
	// each state holds a reference to the spritebatch of the game in order to avoid creating multiple spritebatches
	protected final SpriteBatch	spriteBatch;
	// each state holds a reference to the camera of the game
	protected final Camera		camera;
	protected boolean			initialized;
	protected Object			data;

	public GameLogic(GDXGame game, Camera camera, SpriteBatch spriteBatch) {
		super();
		this.game = game;
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		this.initialized = false;
	}

	public abstract void initialize();

	public void pause() {

	}

	public void resume() {

	}

	public abstract void update(float deltaTime);

	public abstract void render();

	public abstract void resize(int width, int height);

	public abstract void dispose();
}
