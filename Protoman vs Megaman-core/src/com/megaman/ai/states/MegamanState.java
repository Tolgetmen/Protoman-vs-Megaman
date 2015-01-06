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
	WAKE_UP() {
		@Override
		public void enter(Megaman megaman) {
			bossIndex = -1;
			megaman.setTransparency(0);
			megaman.loopAnimation(true);
			megaman.setAnimationPerSecond(1);
			megaman.setLoopAnimations(0, 1);
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getStateTime() > 3) {
				megaman.changeState(FADE_OUT);
			} else if (megaman.getStateTime() > 2) {
				megaman.setAnimationPerSecond(4);
			} else if (megaman.getStateTime() > 1) {
				megaman.setAnimationPerSecond(2);
			}
		}
	},

	FADE_IN() {
		@Override
		public void enter(Megaman megaman) {
			megaman.setTransparency(1);
			megaman.setY(MathUtils.random(64, GameConstants.GAME_HEIGHT - 64 - megaman.getHeight()));
			megaman.setAnimation(1);
			megaman.fadeTo(0, megaman.getShotFrequency() - MegamanConstants.MEGAMAN_FADE_OUT_TIME);
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getTransparency() == 0) {
				if (megaman.getShotCounter() < MegamanConstants.MEGAMAN_MAX_MISSILES) {
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
		}
	},

	ATTACK() {
		@Override
		public void enter(Megaman megaman) {
			megaman.shoot();
			megaman.changeState(FADE_OUT);
		}

		@Override
		public void update(Megaman megaman) {
		}
	},

	CALL_BOSS() {
		@Override
		public void enter(Megaman megaman) {
			megaman.shoot();

			int shotCounter = (megaman.getShotCounter() - MegamanConstants.BOSS_START_DELAY - 1) % MegamanConstants.BOSS_DURATION;
			if (shotCounter == 0) {
				// start new boss round
				++bossIndex;
				if (bossIndex < BossType.values().length) {
					SoundManager.INSTANCE.playMusic(BossType.values()[bossIndex].getMusicType(), true);
				}
			}

			if (shotCounter % MegamanConstants.BOSS_FREQUENCY == 0 && bossIndex < BossType.values().length) {
				megaman.callBoss(BossType.values()[bossIndex]);
			}

			megaman.changeState(FADE_OUT);
		}

		@Override
		public void update(Megaman megaman) {
		}
	},

	FADE_OUT() {
		@Override
		public void enter(Megaman megaman) {
			megaman.setTransparency(0);
			megaman.setAnimation(2);
			megaman.fadeTo(1, MegamanConstants.MEGAMAN_FADE_OUT_TIME);
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getTransparency() == 1) {
				megaman.changeState(FADE_IN);
			}
		}
	};

	@Override
	public void exit(Megaman megaman) {
	}

	@Override
	public boolean onMessage(Megaman megaman, Telegram telegram) {
		return false;
	}

	private static int	bossIndex;
}
