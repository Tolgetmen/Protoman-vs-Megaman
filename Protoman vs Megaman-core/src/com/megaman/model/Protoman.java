package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.megaman.constants.GameConstants;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.gamestates.logic.GSGameLogic;

public class Protoman extends AnimatedGameObject {
	private float	speedX;
	private float	speedY;

	public Protoman(GSGameLogic gameLogic, int numtAnimationsPerColumn, int numAnimationsPerRow, int animationsPerSecond) {
		super(gameLogic, numtAnimationsPerColumn, numAnimationsPerRow, animationsPerSecond);

		speedX = speedY = 0;
	}

	public void setSpeed(float speed, float angleInDegrees) {
		speedX = speed * MathUtils.cos(angleInDegrees * MathUtils.degreesToRadians);
		speedY = speed * MathUtils.sin(angleInDegrees * MathUtils.degreesToRadians);
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
}
