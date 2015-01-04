package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.TextureType;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.enums.BossType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Boss extends AnimatedGameObject implements Poolable {
	private boolean		shoot;
	private BossType	bossType;
	private boolean		isAlive;

	public Boss(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);
		reset();
	}

	public void initialize(BossType type, float x, float y, float width, float height) {
		setPosition(x, y);
		setSize(width, height);
		isAlive = true;
		bossType = type;

		setAnimationPerSecond(type.getAnimationsPerSecond());
		setTextureType(type.getGraphic());
		setLoopAnimations(0, type.getGraphic().getAnimationsX() * type.getGraphic().getAnimationsY() - 1);
		loopAnimation(false);
		startAnimation();
		fadeTo(0, 0.75f);
	}

	public void update(float deltaTime) {
		super.update(deltaTime);

		if (getTransparency() == 0.0f) {
			// ready for attack
			setLoopAnimations(1, 5);
		}

		if (shoot && getCurrentAnimation() == 5) {
			shoot = false;
			stopAnimation();
			fadeTo(1, 0.5f);
			((GSGameLogic) logic).spawnMissile(bossType.getMissileType(), getX() + 16, getY() + 12);
		}

		if (!shoot && getTransparency() >= 1.0f) {
			isAlive = false;
		}
	}

	@Override
	public void reset() {
		shoot = true;
		bossType = null;
		isAlive = false;
		setPosition(0, 0);
		setSize(0, 0);
		setAnimation(0);
		setTransparency(1);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
