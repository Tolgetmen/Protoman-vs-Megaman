package com.megaman.core;

import com.badlogic.gdx.Screen;
import com.megaman.core.enums.GameStateType;

public abstract class GameState implements Screen {
	// each state has its own logic class to process events,etc.
	protected final GameLogic		logic;
	protected final GameStateType	type;

	public GameState(GameStateType type, GameLogic logic) {
		super();
		this.type = type;
		this.logic = logic;
	}

	@Override
	public void show() {
		loadResources();
		if (!logic.initialized) {
			logic.initialize();
			logic.initialized = true;
		} else {
			logic.resume();
		}
	}

	protected abstract void loadResources();

	@Override
	public void hide() {
		logic.pause();
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
		logic.dispose();
		disposeResources();
	}
}
