package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.MathUtils;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.ai.states.MegamanState;
import com.megaman.constants.MegamanConstants;
import com.megaman.enums.BossType;
import com.megaman.enums.MissileType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Megaman extends AnimatedGameObject {
	private StateMachine<Megaman>	stateMachine;
	private float					stateTime;
	private float					shotFrequency;
	private int						shotCounter;
	private boolean					isAlive;

	public Megaman(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);
		stateMachine = new StackStateMachine<Megaman>(this, MegamanState.WAKE_UP, null);
		shotCounter = 0;
		shotFrequency = MegamanConstants.MEGAMAN_SHOOT_START_FREQUENCY;
		stateTime = 0.0f;
		isAlive = true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
		stateTime += deltaTime;
	}

	public void changeState(MegamanState newState) {
		stateTime = 0.0f;
		stateMachine.changeState(newState);
	}

	public void shoot() {
		++shotCounter;
		((GSGameLogic) logic).createMissile(MissileType.MEGAMAN, getX() + 21, getY() + 13);

		if (shotCounter % MegamanConstants.MEGAMAN_FREQUENCY_CHANGE == 0) {
			shotFrequency -= MegamanConstants.MEGAMAN_SHOOT_FREQUENCY_DECREMENT;
		}
	}

	public void callBoss(BossType bossType) {
		((GSGameLogic) logic).createBoss(bossType, getX(), MathUtils.random(64, GameConstants.GAME_HEIGHT - 64 - bossType.getHeight()));
	}

	public int getShotCounter() {
		return shotCounter;
	}

	public float getStateTime() {
		return stateTime;
	}

	public float getShotFrequency() {
		return shotFrequency;
	}

	public void kill() {
		isAlive = false;
		setTransparency(1);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
