package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.megaman.model.Mettool;

public enum MettoolState implements State<Mettool> {
	IDLE() {
		@Override
		public void update(Mettool mettool) {
			if (mettool.getIdleTime() > 3.0f) {
				if (mettool.getCurrentAnimation() == 2) {
					mettool.setAnimation(0);
				}
				if (MathUtils.random(99) < 25) {
					//25% chance to display animation
					mettool.loopAnimation(false);
					mettool.setLoopAnimations(0, 2);
				} else {
					mettool.setIdleTime(0);
				}
			}
		}
	},

	FLEE() {
		@Override
		public void enter(Mettool mettool) {
			mettool.loopAnimation(false);
			mettool.setAnimation(0);
			mettool.setLoopAnimations(0, 3);
		}

		@Override
		public void update(Mettool mettool) {
			if (mettool.getCurrentAnimation() >= 3) {
				mettool.flee();
			}
		}
	};

	@Override
	public void enter(Mettool mettool) {
	}

	@Override
	public void exit(Mettool mettool) {
	}

	@Override
	public boolean onMessage(Mettool megaman, Telegram telegram) {
		// TODO learn about telegram -> how can we use it?
		return false;
	}
}
