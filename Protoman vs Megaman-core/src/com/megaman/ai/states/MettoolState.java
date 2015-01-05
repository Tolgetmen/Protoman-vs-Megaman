package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.megaman.model.Mettool;

public enum MettoolState implements State<Mettool> {
	IDLE() {
		@Override
		public void enter(Mettool mettool) {
			mettool.flip(true, false);
			mettool.setAnimation(0);
		}

		@Override
		public void update(Mettool mettool) {
			if (mettool.getStateTime() > 3.0f) {
				if (MathUtils.random(99) < 15) {
					mettool.changeState(STAND_UP);
				} else {
					mettool.changeState(IDLE);
				}
			}
		}
	},

	STAND_UP() {
		@Override
		public void enter(Mettool mettool) {
			mettool.loopAnimation(false);
			mettool.setLoopAnimations(0, 2);
		}

		@Override
		public void update(Mettool mettool) {
			if (mettool.getCurrentAnimation() == 2 && mettool.getStateTime() > 1.5) {
				mettool.changeState(IDLE);
			}
		}
	},

	FLEE() {
		@Override
		public void enter(Mettool mettool) {
			mettool.loopAnimation(false);
			mettool.setLoopAnimations(0, 3);
		}

		@Override
		public void update(Mettool mettool) {
			if (mettool.getCurrentAnimation() >= 3) {
				mettool.changeState(RUN_AWAY);
			}
		}
	},

	RUN_AWAY() {
		@Override
		public void enter(Mettool mettool) {
			mettool.flip(false, false);
			mettool.loopAnimation(true);
			mettool.setLoopAnimations(4, 5);
		}

		@Override
		public void update(Mettool mettool) {
		}
	};

	@Override
	public void exit(Mettool mettool) {
	}

	@Override
	public boolean onMessage(Mettool mettool, Telegram telegram) {
		return false;
	}
}
