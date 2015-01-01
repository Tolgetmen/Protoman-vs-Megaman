package com.megaman.core;

import com.badlogic.gdx.Screen;

public abstract class GameState implements Screen {
	// each state has its own logic class to process events,etc.
	protected final GameLogic	logic;

	public GameState(GameLogic logic) {
		super();
		this.logic = logic;
	}

	@Override
	public void show() {
		loadResources();
		logic.initialize();
	}

	protected abstract void loadResources();

	@Override
	public void hide() {
		logic.dispose();
		disposeResources();
	}

	protected abstract void disposeResources();

	@Override
	public void render(float deltaTime) {
		logic.update(deltaTime);
		logic.render();
	}

	@Override
	public void resize(int width, int height) {
		logic.resize(width, height);
	}

	@Override
	public void pause() {
		// only important for android games -> ignore for now
	}

	@Override
	public void resume() {
		// only important for android games -> ignore for now
	}

	@Override
	public void dispose() {
		// ignore those calls since we call it automatically when "hide" is called
	}
}
