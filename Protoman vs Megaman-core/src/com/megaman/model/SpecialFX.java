package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.gamestates.logic.GSGameLogic;

public class SpecialFX extends AnimatedGameObject implements Poolable {
	private boolean	isAlive;
	private int		maxAnimation;

	public SpecialFX(GSGameLogic gameLogic) {
		super(gameLogic, 1, 1, 0);
		reset();
	}

	public void initialize(float x, float y, float width, float height, int numColumns, int numRows, int animationsPerSecond) {
		setPosition(x, y);
		setSize(width, height);
		isAlive = true;

		setAnimationPerSecond(animationsPerSecond);
		setAnimationsPerColumn(numColumns);
		setAnimationsPerRow(numRows);
		maxAnimation = numColumns * numRows - 1;
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
