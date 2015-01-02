package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.megaman.constants.GameConstants;
import com.megaman.core.utils.SoundManager;
import com.megaman.enums.BossType;
import com.megaman.model.Megaman;

public enum MegamanState implements State<Megaman> {
	IDLE() {
		@Override
		public void update(Megaman megaman) {
			// remove fadeout time from check
			if (megaman.getRemainingShots() > 0 && megaman.getIdleTime() >= (megaman.getShotFrequency() - 0.5f)) {
				//				if (MathUtils.random(9) < 8) {
				if (megaman.getRemainingShots() > 72) {
					megaman.changeState(ATTACK);
				} else {
					megaman.changeState(CALL_BOSS);
				}
				//				} else {
				//					// 20% chance to call a boss
				//					megaman.changeState(CALL_BOSS);
				//				}
			} else {
				// set megaman to sleep
				megaman.setAnimation(0);
			}
		}
	},

	ATTACK() {
		@Override
		public void enter(Megaman megaman) {
			megaman.shoot();
		}

		@Override
		public void update(Megaman megaman) {
			// if fade out completed
			if (megaman.getIdleTime() > 0.5f) {
				megaman.setY(MathUtils.random(0, GameConstants.GAME_HEIGHT - 33));
				megaman.setAnimation(1);
				megaman.fadeTo(0, 0.75f);
				megaman.changeState(IDLE);
			}
		}
	},

	CALL_BOSS() {
		@Override
		public void enter(Megaman megaman) {
			megaman.shoot();
			if (megaman.getShotCounter() == 0) {
				megaman.setBossIndex(megaman.getBossIndex() + 1);
				if (megaman.getBossIndex() < BossType.values().length) {
					SoundManager.INSTANCE.playMusic(BossType.values()[megaman.getBossIndex()].getMusic(), true);
				}
			}

			if (megaman.getShotCounter() % 3 == 0 && megaman.getBossIndex() < BossType.values().length) {
				megaman.callBoss(BossType.values()[megaman.getBossIndex()]);
			}

			megaman.incShotCounter();
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getIdleTime() > 0.5f) {
				megaman.setY(MathUtils.random(0, GameConstants.GAME_HEIGHT - 33));
				megaman.setAnimation(1);
				megaman.fadeTo(0, 0.75f);
				megaman.changeState(IDLE);
			}
		}
	};

	@Override
	public void enter(Megaman megaman) {
	}

	@Override
	public void exit(Megaman megaman) {
	}

	@Override
	public boolean onMessage(Megaman megaman, Telegram telegram) {
		// TODO learn about telegram -> how can we use it?
		return false;
	}
}
