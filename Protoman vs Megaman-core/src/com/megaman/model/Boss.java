package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.enums.BossType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Boss extends AnimatedGameObject implements Poolable {
	private boolean		fadeOut;
	private boolean		shoot;
	private BossType	bossType;
	private boolean		isAlive;

	public Boss(GSGameLogic gameLogic) {
		super(gameLogic, 1, 1, 0);
		reset();
	}

	public void initialize(BossType type, float x, float y, float width, float height) {
		setPosition(x, y);
		setSize(width, height);
		isAlive = true;
		bossType = type;

		setAnimationPerSecond(type.getAnimationsPerSecond());
		setAnimationsPerColumn(type.getGraphic().getNumColumns());
		setAnimationsPerRow(type.getGraphic().getNumRows());
		setLoopAnimations(0, type.getGraphic().getNumColumns() * type.getGraphic().getNumRows() - 1);
		loopAnimation(false);
		startAnimation();
	}

	public void update(float deltaTime) {
		super.update(deltaTime);

		if (!fadeOut) {
			// fade in over 0.75 seconds
			float transparency = Math.max(getTransparency() - (1.0f / 0.75f) * deltaTime, 0.0f);
			setTransparency(transparency);
		} else if (fadeOut) {
			// fade out over 0.5 seconds
			float transparency = Math.min(getTransparency() + (1.0f / 0.5f) * deltaTime, 1.0f);
			setTransparency(transparency);
		}

		if (getTransparency() == 0.0f) {
			// ready for attack
			setLoopAnimations(1, 5);
		}

		if (shoot && getCurrentAnimation() == 5) {
			shoot = false;
			stopAnimation();
			fadeOut = true;
			gameLogic.spawnMissile(bossType.getMissileType(), position.x + 16, position.y + 12);
		}

		if (!shoot && getTransparency() >= 1.0f) {
			isAlive = false;
		}
	}

	@Override
	public void reset() {
		fadeOut = false;
		shoot = true;
		bossType = null;
		isAlive = false;
		setPosition(0, 0);
		setSize(0, 0);
		setAnimation(0);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
