package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.enums.MissileType;

public class Missile extends AnimatedGameObject implements Poolable {
	private float		speedX;
	private float		speedY;
	private boolean		isAlive;
	private MissileType	missileType;

	public Missile(GameStateLogic logic) {
		super(logic, null, 0);

		reset();
	}

	public void initialize(MissileType type, float x, float y, int angle) {
		setPosition(x, y);
		setSize(type.getWidth(), type.getHeight());
		speedX = type.getSpeed() * MathUtils.cos(angle * MathUtils.degreesToRadians);
		speedY = type.getSpeed() * MathUtils.sin(angle * MathUtils.degreesToRadians);
		isAlive = true;
		missileType = type;

		setTextureType(type.getTextureType());
		setAnimationPerSecond(type.getAnimationsPerSecond());
		setLoopAnimations(0, type.getTextureType().getAnimationsX() * type.getTextureType().getAnimationsY() - 1);
		loopAnimation(type.isLoopAnimation());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		setPosition(getX() + speedX * deltaTime, getY() + speedY * deltaTime);
	}

	@Override
	public void reset() {
		setPosition(0, 0);
		setSize(0, 0);
		speedX = 0.0f;
		speedY = 0.0f;
		isAlive = false;
		missileType = null;
	}

	public void kill() {
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public MissileType getType() {
		return missileType;
	}
}
