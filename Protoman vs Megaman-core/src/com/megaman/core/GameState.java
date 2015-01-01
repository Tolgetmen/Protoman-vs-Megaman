package com.megaman.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.GDXGame;
import com.megaman.enums.GameStateType;

public abstract class GameState implements Screen {
	// each state holds a reference to the "Game" instance in order to call the "closeCurrentGameState" method
	protected final GDXGame		game;
	// each state holds a reference to the spritebatch of the game in order to avoid creating multiple spritebatches
	protected final SpriteBatch	spriteBatch;
	// each state holds a reference to the camera of the game
	protected final Camera		camera;
	// each state has its own logic class to process events,etc.
	protected final GameLogic	logic;

	public GameState(GDXGame game, Camera camera, SpriteBatch spriteBatch, Class<? extends GameLogic> logicClass) {
		super();
		this.game = game;
		this.camera = camera;
		this.spriteBatch = spriteBatch;

		GameLogic logic = null;
		try {
			logic = logicClass.getDeclaredConstructor(GameState.class, GDXGame.class).newInstance(this, game);
			game.setGameInput(logic);
		} catch (IllegalArgumentException e) {
			Gdx.app.error("GameState constructor", "Illegal arguments", e);
		} catch (NoSuchMethodException e) {
			Gdx.app.error("GameState constructor", "No such method", e);
		} catch (Exception e) {
			Gdx.app.error("GameState constructor", "General exception", e);
		}

		this.logic = logic;
	}

	public GameLogic getLogic() {
		return logic;
	}

	public GameStateType getNextState() {
		return logic.getNextState();
	}

	public void update(float deltaTime) {
		logic.update(deltaTime);
	}

	public void render() {
		logic.render(spriteBatch, camera);
	}

	@Override
	public abstract void show();

	@Override
	public abstract void hide();

	@Override
	public void render(float deltaTime) {
		update(deltaTime);
		render();
	}

	@Override
	public abstract void dispose();

	@Override
	public void resize(int width, int height) {
		logic.resize(width, height);
	}
}
