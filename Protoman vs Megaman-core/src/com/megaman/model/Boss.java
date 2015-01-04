package com.megaman.model;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.constants.MegamanConstants;
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

	public void initialize(BossType type, float x, float y) {
		bossType = type;
		isAlive = true;
		setPosition(x, y);
		setSize(type.getWidth(), type.getHeight());
		setAnimationPerSecond(type.getAnimationsPerSecond());
		setTextureType(type.getGraphic());
		loopAnimation(false);
		setAnimation(0);
		fadeTo(0, MegamanConstants.BOSS_FADE_IN_TIME);
	}

	public void update(float deltaTime) {
		super.update(deltaTime);

		if (getTransparency() > 0.8f && getCurrentAnimation() == 0) {
			// completely visible -> ready for attack
			setLoopAnimations(1, 5);
		}

		if (shoot && getCurrentAnimation() == 5) {
			// boss is showing attack animation and can still shoot
			shoot = false;
			fadeTo(1, MegamanConstants.BOSS_FADE_OUT_TIME);
			((GSGameLogic) logic).spawnMissile(bossType.getMissileType(), getX() + 16, getY() + 12);
		} else if (!shoot && getTransparency() >= 1.0f) {
			// boss already attacked and is invisible
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
		setTransparency(1);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
