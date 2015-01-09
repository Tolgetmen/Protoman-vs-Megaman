package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;

public class Protoman extends AnimatedGameObject {
	private float	speedX;
	private float	speedY;
	private boolean	isAlive;

	public Protoman(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);

		speedX = speedY = 0;
		isAlive = true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (speedX != 0 || speedY != 0) {
			setAnimation(1);
			setPosition(getX() + speedX * deltaTime, getY() + speedY * deltaTime);

			if (getY() < 64) {
				setY(64);
			} else if (getY() >= GameConstants.GAME_HEIGHT - 55 - getHeight()) {
				setY(GameConstants.GAME_HEIGHT - 55 - getHeight());
			}
		} else {
			setAnimation(0);
		}
	}

	public void setSpeed(float speed, float angleInDegrees) {
		speedX = speed * MathUtils.cos(angleInDegrees * MathUtils.degreesToRadians);
		speedY = speed * MathUtils.sin(angleInDegrees * MathUtils.degreesToRadians);
	}

	public void kill() {
		setTransparency(1);
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
}
