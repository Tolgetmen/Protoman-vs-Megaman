package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.constants.MegamanConstants;
import com.megaman.enums.BossType;
import com.megaman.model.Megaman;

public enum MegamanState implements State<Megaman> {
	IDLE() {
		@Override
		public void enter(Megaman megaman) {
			megaman.setY(MathUtils.random(0, GameConstants.GAME_HEIGHT - 33));
			megaman.setAnimation(1);
			megaman.fadeTo(0, MegamanConstants.MEGAMAN_FADE_IN_TIME);
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getRemainingShots() > 0 && megaman.getIdleTime() >= (megaman.getShotFrequency() - MegamanConstants.MEGAMAN_FADE_OUT_TIME)) {
				// there are still remaining shots in the megaman buster
				if (megaman.getShotCounter() < MegamanConstants.BOSS_START_DELAY) {
					megaman.changeState(ATTACK);
				} else {
					megaman.changeState(CALL_BOSS);
				}
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
			if (megaman.getIdleTime() >= MegamanConstants.MEGAMAN_FADE_OUT_TIME) {
				megaman.changeState(IDLE);
			}
		}
	},

	CALL_BOSS() {
		@Override
		public void enter(Megaman megaman) {
			megaman.shoot();
			int shotCounter = (megaman.getShotCounter() - MegamanConstants.BOSS_START_DELAY - 1) % MegamanConstants.BOSS_DURATION;
			if (shotCounter == 0) {
				// start new boss round
				megaman.setBossIndex(megaman.getBossIndex() + 1);
				if (megaman.getBossIndex() < BossType.values().length) {
					SoundManager.INSTANCE.playMusic(BossType.values()[megaman.getBossIndex()].getMusic(), true);
				}
			}

			if (shotCounter % MegamanConstants.BOSS_FREQUENCY == 0 && megaman.getBossIndex() < BossType.values().length) {
				megaman.callBoss(BossType.values()[megaman.getBossIndex()]);
			}
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getIdleTime() > MegamanConstants.MEGAMAN_FADE_OUT_TIME) {
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
		return false;
	}
}
