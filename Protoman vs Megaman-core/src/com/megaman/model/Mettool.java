package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.model.AnimatedGameObject;
import com.megaman.ai.states.MettoolState;

public class Mettool extends AnimatedGameObject {
	private StateMachine<Mettool>	stateMachine;
	private float					stateTime;

	public Mettool(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic, textureType, animationsPerSecond);

		stateMachine = new StackStateMachine<Mettool>(this, MettoolState.IDLE, null);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
		stateTime += deltaTime;

		if (stateMachine.getCurrentState().equals(MettoolState.RUN_AWAY)) {
			setPosition(getX() + 120 * deltaTime, getY());
		}
	}

	public void changeState(MettoolState newState) {
		stateTime = 0.0f;
		stateMachine.changeState(newState);
	}

	public float getStateTime() {
		return stateTime;
	}
}
