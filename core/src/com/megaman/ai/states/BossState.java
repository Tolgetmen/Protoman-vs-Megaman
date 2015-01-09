package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.megaman.constants.MegamanConstants;
import com.megaman.model.Boss;

public enum BossState implements State<Boss> {
	FADE_IN() {
		@Override
		public void enter(Boss boss) {
			boss.setTransparency(1);
			boss.setAnimation(0);
			boss.fadeTo(0, MegamanConstants.BOSS_FADE_IN_TIME);
		}

		@Override
		public void update(Boss boss) {
			if (boss.getTransparency() < 0.2f) {
				boss.changeState(ATTACK);
			}
		}

	},

	ATTACK() {
		@Override
		public void enter(Boss boss) {
			boss.loopAnimation(false);
			boss.setLoopAnimations(1, 5);
		}

		@Override
		public void update(Boss boss) {
			if (boss.getCurrentAnimation() == 5) {
				boss.shoot();
				boss.changeState(FADE_OUT);
			}
		}

	},

	FADE_OUT() {
		@Override
		public void enter(Boss boss) {
			boss.fadeTo(1, MegamanConstants.BOSS_FADE_OUT_TIME);
		}

		@Override
		public void update(Boss boss) {
			if (boss.getTransparency() == 1) {
				boss.kill();
			}
		}
	};

	@Override
	public void exit(Boss boss) {
	}

	@Override
	public boolean onMessage(Boss boss, Telegram telegram) {
		return false;
	}
}
