package com.megaman.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.megaman.constants.GameConstants;
import com.megaman.core.utils.SoundManager;
import com.megaman.enums.AudioType;
import com.megaman.enums.BossType;
import com.megaman.enums.MissileType;
import com.megaman.model.Megaman;

public enum MegamanState implements State<Megaman> {
	IDLE() {
		@Override
		public void update(Megaman megaman) {
			if (megaman.getIdleTime() > 1.0f) {
				if (MathUtils.random(9) < 8) {
					megaman.changeState(ATTACK);
				} else {
					// 20% chance to call a boss
					megaman.changeState(CALL_BOSS);
				}
			}
		}
	},

	ATTACK() {
		@Override
		public void enter(Megaman megaman) {
			megaman.getGameLogic().spawnMissile(MissileType.MEGAMAN, megaman.getX() + 21, megaman.getY() + 13);
			SoundManager.INSTANCE.playSound(AudioType.SOUND_SHOOT_MEGAMAN);
			megaman.setAnimation(2);
			megaman.setFadeOut(true);
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getIdleTime() > 0.5f) {
				megaman.setY(MathUtils.random(0, GameConstants.GAME_HEIGHT - 33));
				megaman.setAnimation(1);
				megaman.setFadeOut(false);
				megaman.changeState(IDLE);
			}
		}
	},

	CALL_BOSS() {
		@Override
		public void enter(Megaman megaman) {
			megaman.getGameLogic().spawnMissile(MissileType.MEGAMAN, megaman.getX() + 21, megaman.getY() + 13);
			SoundManager.INSTANCE.playSound(AudioType.SOUND_SHOOT_MEGAMAN);
			megaman.setAnimation(2);
			megaman.setFadeOut(true);

			megaman.getGameLogic().spawnBoss(BossType.values()[MathUtils.random(BossType.values().length - 1)], megaman.getX(), MathUtils.random(0, GameConstants.GAME_HEIGHT - 42));
		}

		@Override
		public void update(Megaman megaman) {
			if (megaman.getIdleTime() > 0.5f) {
				megaman.setY(MathUtils.random(0, GameConstants.GAME_HEIGHT - 33));
				megaman.setAnimation(1);
				megaman.setFadeOut(false);
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
