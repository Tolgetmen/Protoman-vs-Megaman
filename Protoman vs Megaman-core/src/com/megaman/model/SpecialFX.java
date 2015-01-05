package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.enums.EffectType;

public class SpecialFX extends AnimatedGameObject implements Poolable {
	private boolean	isAlive;
	private float	durationMax;
	private float	currentDuration;

	public SpecialFX(GameStateLogic logic) {
		super(logic, null, 0);

		reset();
	}

	public void initialize(EffectType type, float x, float y, float duration) {
		setPosition(x, y);
		setSize(type.getWidth(), type.getHeight());
		isAlive = true;

		setTextureType(type.getTextureType());
		setAnimationPerSecond(type.getAnimationsPerSecond());
		startAnimation();

		durationMax = duration;
		currentDuration = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		currentDuration += deltaTime;
		if (currentDuration >= durationMax) {
			kill();
		}
	}

	@Override
	public void reset() {
		isAlive = false;
		setPosition(0, 0);
		setSize(0, 0);
		durationMax = currentDuration = 0;
	}

	public void kill() {
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
}
