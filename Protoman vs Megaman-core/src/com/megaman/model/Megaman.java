package com.megaman.model;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.megaman.ai.states.MegamanState;
import com.megaman.gamestates.logic.GSGameLogic;

public class Megaman extends AnimatedGameObject {
	private StateMachine<Megaman>	stateMachine;
	private float					idleTime;
	private boolean					fadeOut;

	public Megaman(GSGameLogic gameLogic, int numtAnimationsPerColumn, int numAnimationsPerRow, int animationsPerSecond) {
		super(gameLogic, numtAnimationsPerColumn, numAnimationsPerRow, animationsPerSecond);

		idleTime = 0.0f;
		fadeOut = false;
		stateMachine = new StackStateMachine<Megaman>(this, MegamanState.IDLE, null);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		stateMachine.update();
		idleTime += deltaTime;

		if (!fadeOut) {
			// fade in over 0.75 seconds
			float transparency = Math.max(getTransparency() - (1.0f / 0.75f) * deltaTime, 0.0f);
			setTransparency(transparency);
		} else if (fadeOut) {
			// fade out over 0.5 seconds
			float transparency = Math.min(getTransparency() + (1.0f / 0.5f) * deltaTime, 1.0f);
			setTransparency(transparency);
		}
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

	public void setFadeOut(boolean fadeOut) {
		this.fadeOut = fadeOut;
	}
}
