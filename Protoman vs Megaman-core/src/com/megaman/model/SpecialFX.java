package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.TextureType;
import com.megaman.core.model.AnimatedGameObject;

public class SpecialFX extends AnimatedGameObject implements Poolable {
	private boolean	isAlive;
	private int		maxAnimation;

	public SpecialFX(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);

		reset();
	}

	public void initialize(float x, float y, float width, float height, TextureType type, int animationsPerSecond) {
		setPosition(x, y);
		setSize(width, height);
		isAlive = true;

		setAnimationPerSecond(animationsPerSecond);
		setTextureType(type);
		maxAnimation = type.getNumColumns() * type.getNumRows() - 1;
		setLoopAnimations(0, maxAnimation);
		loopAnimation(false);
		startAnimation();
	}

	public void update(float deltaTime) {
		super.update(deltaTime);

		if (getCurrentAnimation() == maxAnimation) {
			isAlive = false;
		}
	}

	@Override
	public void reset() {
		isAlive = false;
		setPosition(0, 0);
		setSize(0, 0);
		setAnimation(0);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
