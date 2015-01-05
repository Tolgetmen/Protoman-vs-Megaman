package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.ai.states.BossState;
import com.megaman.enums.BossType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Boss extends AnimatedGameObject implements Poolable {
	private boolean				isAlive;
	private BossType			bossType;
	private StateMachine<Boss>	stateMachine;

	public Boss(GameStateLogic logic) {
		super(logic, null, 0);
		stateMachine = new StackStateMachine<Boss>(this, BossState.FADE_IN, null);
		reset();
		loopAnimation(false);
	}

	public void initialize(BossType type, float x, float y) {
		isAlive = true;
		bossType = type;
		setPosition(x, y);
		setSize(type.getWidth(), type.getHeight());
		setAnimationPerSecond(type.getAnimationsPerSecond());
		setTextureType(type.getTextureType());
		stateMachine.changeState(BossState.FADE_IN);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
	}

	@Override
	public void reset() {
		bossType = null;
		isAlive = false;
		setPosition(0, 0);
		setSize(0, 0);
	}

	public void changeState(BossState newState) {
		stateMachine.changeState(newState);
	}

	public void shoot() {
		((GSGameLogic) logic).createMissile(bossType.getMissileType(), getX() + 16, getY() + 12);
	}

	public void kill() {
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
}
