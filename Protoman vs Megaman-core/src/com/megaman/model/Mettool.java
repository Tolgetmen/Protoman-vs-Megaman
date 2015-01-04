package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.megaman.ai.states.MettoolState;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.gamestates.logic.GSGameLogic;

public class Mettool extends AnimatedGameObject {
	private StateMachine<Mettool>	stateMachine;
	private float					idleTime;
	private boolean					isFleeing;

	public Mettool(GSGameLogic gameLogic, int numtAnimationsPerColumn, int numAnimationsPerRow, int animationsPerSecond) {
		super(gameLogic, numtAnimationsPerColumn, numAnimationsPerRow, animationsPerSecond);

		stateMachine = new StackStateMachine<Mettool>(this, MettoolState.IDLE, null);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
		idleTime += deltaTime;

		if (isFleeing) {
			setPosition(getX() + 120 * deltaTime, getY());
		}
	}

	public void changeState(MettoolState newState) {
		if (stateMachine.getCurrentState().equals(newState)) {
			return;
		}
		idleTime = 0.0f;
		stateMachine.changeState(newState);
	}

	public float getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(float idleTime) {
		this.idleTime = idleTime;
	}

	public void flee() {
		if (isFleeing) {
			return;
		}
		flip(false, false);
		loopAnimation(true);
		setLoopAnimations(4, 5);
		isFleeing = true;
	}
}
