package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;

public class Protoman extends AnimatedGameObject {
	private float	speedX;
	private float	speedY;

	public Protoman(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);

		speedX = speedY = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (speedX != 0 || speedY != 0) {
			setAnimation(1);
			setPosition(getX() + speedX * deltaTime, getY() + speedY * deltaTime);

			if (getY() < 0) {
				setY(0);
			} else if (getY() + getHeight() >= GameConstants.GAME_HEIGHT) {
				setY(GameConstants.GAME_HEIGHT - getHeight());
			}
		} else {
			setAnimation(0);
		}
	}

	public void setSpeed(float speed, float angleInDegrees) {
		speedX = speed * MathUtils.cos(angleInDegrees * MathUtils.degreesToRadians);
		speedY = speed * MathUtils.sin(angleInDegrees * MathUtils.degreesToRadians);
	}
}
