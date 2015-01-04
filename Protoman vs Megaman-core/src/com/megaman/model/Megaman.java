package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.MathUtils;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.ai.states.MegamanState;
import com.megaman.constants.MegamanConstants;
import com.megaman.enums.BossType;
import com.megaman.enums.MissileType;
import com.megaman.gamestates.logic.GSGameLogic;

public class Megaman extends AnimatedGameObject {
	private StateMachine<Megaman>	stateMachine;
	private float					idleTime;
	private int						remainingShots;
	private float					shotFrequency;
	private int						bossIndex;
	private int						shotCounter;

	public Megaman(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);

		shotCounter = 0;
		bossIndex = -1;
		shotFrequency = MegamanConstants.MEGAMAN_SHOOT_START_FREQUENCY;
		remainingShots = MegamanConstants.MEGAMAN_MAX_MISSILES;
		idleTime = 0.0f;
		stateMachine = new StackStateMachine<Megaman>(this, MegamanState.IDLE, null);
	}

	public int getShotCounter() {
		return shotCounter;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
		idleTime += deltaTime;
	}

	public int getBossIndex() {
		return bossIndex;
	}

	public void setBossIndex(int bossIndex) {
		this.bossIndex = bossIndex;
	}

	public float getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(float idleTime) {
		this.idleTime = idleTime;
	}

	public void changeState(MegamanState newState) {
		idleTime = 0.0f;
		stateMachine.changeState(newState);
	}

	public int getRemainingShots() {
		return remainingShots;
	}

	public float getShotFrequency() {
		return shotFrequency;
	}

	public void shoot() {
		++shotCounter;
		--remainingShots;
		((GSGameLogic) logic).spawnMissile(MissileType.MEGAMAN, getX() + 21, getY() + 13);
		SoundManager.INSTANCE.playSound(SoundType.SHOOT_MEGAMAN);
		setAnimation(2);
		fadeTo(1, MegamanConstants.MEGAMAN_FADE_OUT_TIME);

		if (shotCounter % MegamanConstants.MEGAMAN_FREQUENCY_CHANGE == 0) {
			shotFrequency -= MegamanConstants.MEGAMAN_SHOOT_FREQUENCY_DECREMENT;
		}
	}

	public void callBoss(BossType bossType) {
		((GSGameLogic) logic).spawnBoss(bossType, getX(), MathUtils.random(0, GameConstants.GAME_HEIGHT - 42));
	}
}
