package com.megaman.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.GDXGame;
import com.megaman.enums.GameStateType;

public abstract class GameLogic extends GameInputAdapter {
	protected final GameState	gameState;
	protected final GDXGame		game;

	public GameLogic(GameState gameState, GDXGame game) {
		super();
		this.game = game;
		this.gameState = gameState;
	}

	public abstract void initialize();

	public abstract void update(float deltaTime);

	public abstract void render(SpriteBatch spriteBatch, Camera camera);

	public abstract void resize(int width, int height);

	public abstract void dispose();

	// return null to just terminate this state
	// return any other gamestatetype to set that type of state as next state when closing this state
	public abstract GameStateType getNextState();
}
