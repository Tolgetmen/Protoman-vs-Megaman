package com.megaman.model;

import com.badlogic.gdx.math.MathUtils;
import com.megaman.constants.GameConstants;
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
			position.add(speedX * deltaTime, speedY * deltaTime);

			if (position.y < 0) {
				position.y = 0;
			} else if (position.y + height >= GameConstants.GAME_HEIGHT) {
				position.y = GameConstants.GAME_HEIGHT - height;
			}
		} else {
			setAnimation(0);
		}
	}
}
