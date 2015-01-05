package com.megaman.gamestates.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.graphics.AnimatedSprite;
import com.gdxgame.core.model.AnimatedGameObject;
import com.gdxgame.core.model.GameObject;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.ResourceManager;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.ai.states.MegamanState;
import com.megaman.ai.states.MettoolState;
import com.megaman.constants.MegamanConstants;
import com.megaman.enums.BossType;
import com.megaman.enums.CharacterType;
import com.megaman.enums.EffectType;
import com.megaman.enums.MissileType;
import com.megaman.model.Boss;
import com.megaman.model.Megaman;
import com.megaman.model.Mettool;
import com.megaman.model.Missile;
import com.megaman.model.Protoman;
import com.megaman.model.Effect;

public class GSGameLogic extends GameStateLogic {
	private MusicType								currentMusicType;
	private float									lastMusicPosition;

	private Array<GameObject>						gameObjects;
	private Map<AnimatedGameObject, AnimatedSprite>	animatedObjects;

	private Array<Missile>							activeMissiles;
	private Pool<Missile>							poolMissiles;

	private Array<Boss>								activeBosses;
	private Pool<Boss>								poolBosses;

	private Array<Effect>							activeEffects;
	private Pool<Effect>							poolEffects;

	private Megaman									megaman;

	private Protoman								protoman;

	private Array<Mettool>							mettools;

	private int										life;
	private int										blockedNormal;
	private int										blockedBoss;

	public GSGameLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	@Override
	public void initialize() {
		// start initial background music
		playMusic(MusicType.PROTOMAN);

		// create array to store all game objects to update them within the update() method
		gameObjects = new Array<GameObject>();
		// create a map to link animated game objects with their corresponding animated sprite
		animatedObjects = new HashMap<AnimatedGameObject, AnimatedSprite>();

		// create missile pool
		activeMissiles = new Array<Missile>();
		poolMissiles = new Pool<Missile>() {
			@Override
			protected Missile newObject() {
				return new Missile(GSGameLogic.this);
			}
		};

		// create boss pool
		activeBosses = new Array<Boss>();
		poolBosses = new Pool<Boss>() {
			@Override
			protected Boss newObject() {
				return new Boss(GSGameLogic.this);
			}
		};

		// create effect pool
		activeEffects = new Array<Effect>();
		poolEffects = new Pool<Effect>() {
			@Override
			protected Effect newObject() {
				return new Effect(GSGameLogic.this);
			}
		};

		// create megaman on the left side of the screen
		megaman = new Megaman(this, CharacterType.MEGAMAN.getTextureType(), CharacterType.MEGAMAN.getAnimationsPerSeconds());
		megaman.setPosition(0, GameConstants.GAME_HEIGHT / 2 - 16);
		megaman.setSize(CharacterType.MEGAMAN.getWidth(), CharacterType.MEGAMAN.getHeight());
		megaman.changeState(MegamanState.WAKE_UP);
		gameObjects.add(megaman);
		animatedObjects.put(megaman, ResourceManager.INSTANCE.getAnimatedSprite(CharacterType.MEGAMAN.getTextureType()));

		// create protoman on the right side of the screen
		protoman = new Protoman(this, CharacterType.PROTOMAN.getTextureType(), CharacterType.PROTOMAN.getAnimationsPerSeconds());
		protoman.setPosition(GameConstants.GAME_WIDTH - 90, GameConstants.GAME_HEIGHT / 2 - 16);
		protoman.setSize(CharacterType.PROTOMAN.getWidth(), CharacterType.PROTOMAN.getHeight());
		gameObjects.add(protoman);
		animatedObjects.put(protoman, ResourceManager.INSTANCE.getAnimatedSprite(CharacterType.PROTOMAN.getTextureType()));

		// initialize game values and highscore values
		life = MegamanConstants.MAX_LIFE;
		blockedNormal = 0;
		blockedBoss = 0;

		// create mettools to represent the amount of remaining lives
		// mettools are centered behind protoman (they are building two rows)
		final AnimatedSprite sprMettool = ResourceManager.INSTANCE.getAnimatedSprite(CharacterType.METTOOL.getTextureType());
		final int mettoolSpaceHeight = 32;
		final int mettoolSpaceColumn = mettoolSpaceHeight * (MegamanConstants.MAX_LIFE / 2);
		final int mettoolOffsetCenter = mettoolSpaceColumn / 2;
		final int mettoolStartY = (GameConstants.GAME_HEIGHT / 2) - mettoolOffsetCenter;

		mettools = new Array<Mettool>();
		for (int i = 0; i < life; ++i) {
			Mettool mettool = new Mettool(this, CharacterType.METTOOL.getTextureType(), CharacterType.METTOOL.getAnimationsPerSeconds());
			if (i < life / 2) {
				mettool.setPosition(GameConstants.GAME_WIDTH - 32, mettoolStartY + mettoolSpaceHeight * i);
			} else {
				mettool.setPosition(GameConstants.GAME_WIDTH - 64, mettoolStartY + mettoolSpaceHeight * (i - life / 2));
			}
			mettool.setSize(CharacterType.METTOOL.getWidth(), CharacterType.METTOOL.getHeight());
			mettool.changeState(MettoolState.IDLE);
			mettools.add(mettool);
			animatedObjects.put(mettool, sprMettool);
		}
	}

	private boolean checkEndCondition() {
		// check for end condition
		if (megaman.getShotCounter() >= MegamanConstants.MEGAMAN_MAX_MISSILES && activeMissiles.size == 0 && life > 0) {
			// survived -> show highscore
			Map<String, Integer> highscore = new HashMap<String, Integer>();
			highscore.put("blocked", blockedNormal + blockedBoss);
			highscore.put("blocked_normal", blockedNormal);
			highscore.put("blocked_boss", blockedBoss);
			highscore.put("leaked", MegamanConstants.MAX_LIFE - life);
			highscore.put("life", life);
			highscore.put("points", (life * 1000) + (blockedBoss * 300) + (blockedNormal * 100));
			game.setGameState(GameStateType.HIGHSCORE, true, true, highscore);
			return true;
		} else if (life <= 0) {
			// game over
			game.setGameState(GameStateType.GAME_OVER, true, true, null);
			return true;
		}

		return false;
	}

	private void reduceLife(int amount) {
		while (amount > 0 && life > 0) {
			--amount;
			--life;
			if (mettools.size > life) {
				mettools.get(life).changeState(MettoolState.FLEE);
				createEffect(EffectType.HIT, mettools.get(life).getX(), mettools.get(life).getY(), 1);
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		if (!checkEndCondition()) {
			// update all game objects except bosses, effects, missiles and mettools
			Iterator<GameObject> iterGameObj = gameObjects.iterator();
			while (iterGameObj.hasNext()) {
				GameObject gameObj = iterGameObj.next();
				gameObj.update(deltaTime);
			}

			// update bosses
			Iterator<Boss> iterBosses = activeBosses.iterator();
			while (iterBosses.hasNext()) {
				Boss boss = iterBosses.next();

				boss.update(deltaTime);
				if (!boss.isAlive()) {
					poolBosses.free(boss);
					animatedObjects.remove(boss);
					iterBosses.remove();
				}
			}

			// update effects
			Iterator<Effect> iterEffects = activeEffects.iterator();
			while (iterEffects.hasNext()) {
				Effect effect = iterEffects.next();

				effect.update(deltaTime);
				if (!effect.isAlive()) {
					poolEffects.free(effect);
					animatedObjects.remove(effect);
					iterEffects.remove();
				}
			}

			// update missiles
			Iterator<Missile> iterMissiles = activeMissiles.iterator();
			while (iterMissiles.hasNext()) {
				Missile missile = iterMissiles.next();
				missile.update(deltaTime);

				if (missile.isAlive() && GameUtils.intersects(protoman, missile)) {
					// protoman blocked missile -> increase block counter
					missile.kill();
					SoundManager.INSTANCE.playSound(SoundType.BLOCK);
					if (MissileType.MEGAMAN.equals(missile.getType())) {
						++blockedNormal;
					} else {
						++blockedBoss;
					}
				} else if (missile.isAlive() && missile.getX() > GameConstants.GAME_WIDTH) {
					// missile left screen -> reduce life
					missile.kill();
					if (MissileType.MEGAMAN.equals(missile.getType())) {
						reduceLife(1);
					} else {
						reduceLife(2);
					}
				}

				if (!missile.isAlive()) {
					poolMissiles.free(missile);
					animatedObjects.remove(missile);
					iterMissiles.remove();
				}
			}

			// update mettools
			Iterator<Mettool> iterMettool = mettools.iterator();
			while (iterMettool.hasNext()) {
				Mettool mettool = iterMettool.next();
				mettool.update(deltaTime);
				if (!GameUtils.isWithinCameraView(camera, mettool)) {
					// mettool left game area -> remove it
					animatedObjects.remove(mettool);
					iterMettool.remove();
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		for (Map.Entry<AnimatedGameObject, AnimatedSprite> entry : animatedObjects.entrySet()) {
			GameUtils.renderGameObject(spriteBatch, camera, entry.getKey(), entry.getValue());
		}
		spriteBatch.end();
	}

	@Override
	public void pause() {
		// remember music position for resume() call
		lastMusicPosition = SoundManager.INSTANCE.stopCurrentMusic();
	}

	@Override
	public void resume() {
		// continue music playback from last position
		SoundManager.INSTANCE.playMusic(currentMusicType, true);
		SoundManager.INSTANCE.setMusicPosition(lastMusicPosition);
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.UP: {
				// move protoman up
				protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
				break;
			}
			case Keys.DOWN: {
				// move protoman down
				protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
				break;
			}
			case Keys.ESCAPE: {
				// switch to state menu
				game.setGameState(GameStateType.MAIN_MENU, false, true, null);
				break;
			}
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Keys.DOWN:
			case Keys.UP: {
				if (Gdx.input.isKeyPressed(Keys.DOWN)) {
					// down key still pressed -> move protoman down
					protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
				} else if (Gdx.input.isKeyPressed(Keys.UP)) {
					// up key still pressed -> move protoman up
					protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
				} else {
					// both keys released -> stop protoman
					protoman.setSpeed(0, 0);
				}
				break;
			}
		}

		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		switch (value) {
			case north:
			case northEast:
			case northWest: {
				keyDown(Keys.UP);
				break;
			}
			case south:
			case southEast:
			case southWest: {
				keyDown(Keys.DOWN);
				break;
			}
			case center: {
				keyUp(Keys.DOWN);
				break;
			}
			default: {
				break;
			}
		}

		return true;
	}

	public void createEffect(EffectType type, float startX, float startY, float duration) {
		Effect effect = poolEffects.obtain();

		effect.initialize(type, startX, startY, duration);
		activeEffects.add(effect);
		animatedObjects.put(effect, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));

		SoundManager.INSTANCE.playSound(type.getSoundType());
	}

	public void createMissile(MissileType type, float startX, float startY) {
		Missile missile = poolMissiles.obtain();

		missile.initialize(type, startX, startY, 0);
		activeMissiles.add(missile);
		animatedObjects.put(missile, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));

		SoundManager.INSTANCE.playSound(type.getSoundType());
	}

	public void createBoss(BossType type, float spawnX, float spawnY) {
		Boss boss = poolBosses.obtain();

		boss.initialize(type, spawnX, spawnY);
		activeBosses.add(boss);
		animatedObjects.put(boss, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));
	}

	private void playMusic(MusicType type) {
		// store the current musictype to resume it when the game is resumed out of the menu game state
		currentMusicType = type;
		SoundManager.INSTANCE.playMusic(currentMusicType, true);
	}
}
