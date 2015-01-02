package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.enums.MissileType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Missile extends AnimatedGameObject implements Poolable {
	private float		speedX;
	private float		speedY;
	private boolean		isAlive;
	private MissileType	missileType;

	public Missile(GSGameLogic gameLogic) {
		super(gameLogic, 1, 1, 0);
		reset();
	}

	public void initialize(MissileType type, float x, float y, float width, float height, int angle) {
		setPosition(x, y);
		setSize(width, height);
		speedX = type.getSpeed() * MathUtils.cos(angle * MathUtils.degreesToRadians);
		speedY = type.getSpeed() * MathUtils.sin(angle * MathUtils.degreesToRadians);
		isAlive = true;
		this.missileType = type;

		setAnimationPerSecond(type.getAnimationsPerSecond());
		setAnimationsPerColumn(type.getGraphic().getNumColumns());
		setAnimationsPerRow(type.getGraphic().getNumRows());
		setLoopAnimations(0, type.getGraphic().getNumColumns() * type.getGraphic().getNumRows() - 1);
		loopAnimation(type.isLoopAnimation());
		startAnimation();
	}

	@Override
	public void reset() {
		setPosition(0, 0);
		setSize(0, 0);
		setAnimation(0);
		speedX = 0.0f;
		speedY = 0.0f;
		isAlive = false;
		missileType = null;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		position.add(speedX * deltaTime, speedY * deltaTime);
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
