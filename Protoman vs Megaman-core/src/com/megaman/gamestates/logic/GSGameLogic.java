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
import com.gdxgame.core.enums.TextureType;
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
import com.megaman.enums.EffectType;
import com.megaman.enums.MissileType;
import com.megaman.model.Boss;
import com.megaman.model.Megaman;
import com.megaman.model.Mettool;
import com.megaman.model.Missile;
import com.megaman.model.Protoman;
import com.megaman.model.SpecialFX;

public class GSGameLogic extends GameStateLogic {
	private Array<GameObject>						gameObjects;
	// create two maps for linking character/sprite and missile/sprite
	// since characters share one texture and missiles share one texture
	//
	// this should avoid texture binding calls and therefore increase render performance
	private Map<AnimatedGameObject, AnimatedSprite>	animatedCharacters;
	private Map<AnimatedGameObject, AnimatedSprite>	animatedMissiles;
	private Array<Missile>							activeMissiles;
	private Pool<Missile>							poolMissiles;
	private Array<Boss>								activeBosses;
	private Pool<Boss>								poolBosses;
	private Array<Mettool>							mettools;
	private Array<SpecialFX>						activeEffects;
	private Pool<SpecialFX>							poolEffects;

	private Megaman									megaman;
	private Protoman								protoman;

	private int										life;
	private int										blockedNormal;
	private int										blockedBoss;

	private MusicType								currentMusic;
	private float									lastMusicPosition;

	public GSGameLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	private void playMusic(MusicType type) {
		currentMusic = type;
		SoundManager.INSTANCE.playMusic(currentMusic, true);
	}

	@Override
	public void initialize() {
		playMusic(MusicType.PROTOMAN);

		gameObjects = new Array<GameObject>();
		animatedCharacters = new HashMap<AnimatedGameObject, AnimatedSprite>();
		animatedMissiles = new HashMap<AnimatedGameObject, AnimatedSprite>();
		activeMissiles = new Array<Missile>();
		poolMissiles = new Pool<Missile>() {
			@Override
			protected Missile newObject() {
				return new Missile(GSGameLogic.this);
			}
		};
		activeBosses = new Array<Boss>();
		poolBosses = new Pool<Boss>() {
			@Override
			protected Boss newObject() {
				return new Boss(GSGameLogic.this);
			}
		};
		activeEffects = new Array<SpecialFX>();
		poolEffects = new Pool<SpecialFX>() {
			@Override
			protected SpecialFX newObject() {
				return new SpecialFX(GSGameLogic.this);
			}
		};

		final AnimatedSprite sprMegaman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.CHARACTER_MEGAMAN);
		final AnimatedSprite sprProtoman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.CHARACTER_PROTOMAN);
		final AnimatedSprite sprMettool = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.CHARACTER_METTOOL);

		megaman = new Megaman(this, TextureType.CHARACTER_MEGAMAN, 10);
		megaman.setPosition(0, GameConstants.GAME_HEIGHT / 2 - 16);
		megaman.setSize(sprMegaman.getWidth(), sprMegaman.getHeight());
		gameObjects.add(megaman);
		animatedCharacters.put(megaman, sprMegaman);
		megaman.changeState(MegamanState.WAKE_UP);

		protoman = new Protoman(this, TextureType.CHARACTER_PROTOMAN, 10);
		protoman.setPosition(GameConstants.GAME_WIDTH - 90, GameConstants.GAME_HEIGHT / 2 - 16);
		protoman.setSize(sprProtoman.getWidth(), sprProtoman.getHeight());
		gameObjects.add(protoman);
		animatedCharacters.put(protoman, sprProtoman);

		life = MegamanConstants.MAX_LIFE;
		blockedNormal = 0;
		blockedBoss = 0;

		mettools = new Array<Mettool>();
		for (int i = 0; i < life; ++i) {
			Mettool mettool = new Mettool(this, TextureType.CHARACTER_METTOOL, 3);
			if (i < 10) {
				mettool.setPosition(GameConstants.GAME_WIDTH - 32, 160 + 32 * i);
			} else {
				mettool.setPosition(GameConstants.GAME_WIDTH - 64, 160 + 32 * (i - 10));
			}
			mettool.setSize(40, 40);
			mettool.changeState(MettoolState.IDLE);
			mettools.add(mettool);
			animatedCharacters.put(mettool, sprMettool);
		}
	}

	@Override
	public void update(float deltaTime) {
		// check for end condition
		if (megaman.getShotCounter() >= MegamanConstants.MEGAMAN_MAX_MISSILES && activeMissiles.size == 0 && life > 0) {
			// survived ! -> show highscore
			Map<String, Integer> highscore = new HashMap<String, Integer>();
			highscore.put("blocked", blockedNormal + blockedBoss);
			highscore.put("blocked_normal", blockedNormal);
			highscore.put("blocked_boss", blockedBoss);
			highscore.put("leaked", MegamanConstants.MAX_LIFE - life);
			highscore.put("life", life);
			highscore.put("points", (life * 1000) + (blockedBoss * 300) + (blockedNormal * 100));
			game.setGameState(GameStateType.HIGHSCORE, true, true, highscore);
			return;
		} else if (life <= 0) {
			// game over
			game.setGameState(GameStateType.GAME_OVER, true, true, null);
			return;
		}

		Iterator<GameObject> iterGameObj = gameObjects.iterator();
		while (iterGameObj.hasNext()) {
			GameObject gameObj = iterGameObj.next();
			gameObj.update(deltaTime);
		}

		Iterator<Boss> iterBosses = activeBosses.iterator();
		while (iterBosses.hasNext()) {
			Boss boss = iterBosses.next();

			boss.update(deltaTime);
			if (!boss.isAlive()) {
				poolBosses.free(boss);
				animatedCharacters.remove(boss);
				iterBosses.remove();
			}
		}

		Iterator<SpecialFX> iterEffects = activeEffects.iterator();
		while (iterEffects.hasNext()) {
			SpecialFX effect = iterEffects.next();

			effect.update(deltaTime);
			if (!effect.isAlive()) {
				poolEffects.free(effect);
				animatedCharacters.remove(effect);
				iterEffects.remove();
			}
		}

		Iterator<Missile> iterMissiles = activeMissiles.iterator();
		while (iterMissiles.hasNext()) {
			Missile missile = iterMissiles.next();
			missile.update(deltaTime);

			if (missile.isAlive() && GameUtils.intersects(protoman, missile)) {
				missile.kill();
				SoundManager.INSTANCE.playSound(SoundType.BLOCK);
				if (MissileType.MEGAMAN.equals(missile.getType())) {
					++blockedNormal;
				} else {
					++blockedBoss;
				}
			} else if (missile.isAlive() && missile.getX() > GameConstants.GAME_WIDTH) {
				missile.kill();
				if (MissileType.MEGAMAN.equals(missile.getType())) {
					--life;
					if (mettools.size > life) {
						mettools.get(life).changeState(MettoolState.FLEE);
						createSpecialFX(EffectType.HIT, mettools.get(life).getX(), mettools.get(life).getY(), 1);
					}
				} else {
					--life;
					if (mettools.size > life) {
						mettools.get(life).changeState(MettoolState.FLEE);
						createSpecialFX(EffectType.HIT, mettools.get(life).getX(), mettools.get(life).getY(), 1);
					}
					--life;
					if (mettools.size > life) {
						mettools.get(life).changeState(MettoolState.FLEE);
						createSpecialFX(EffectType.HIT, mettools.get(life).getX(), mettools.get(life).getY(), 1);
					}
				}
			}

			if (!missile.isAlive()) {
				poolMissiles.free(missile);
				animatedMissiles.remove(missile);
				iterMissiles.remove();
			}
		}

		Iterator<Mettool> iterMettool = mettools.iterator();
		while (iterMettool.hasNext()) {
			Mettool mettool = iterMettool.next();
			mettool.update(deltaTime);
			if (!GameUtils.isWithinCameraView(camera, mettool)) {
				// mettool left game area -> remove it
				animatedCharacters.remove(mettool);
				iterMettool.remove();
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		for (Map.Entry<AnimatedGameObject, AnimatedSprite> entry : animatedCharacters.entrySet()) {
			GameUtils.renderGameObject(spriteBatch, camera, entry.getKey(), entry.getValue());
		}
		for (Map.Entry<AnimatedGameObject, AnimatedSprite> entry : animatedMissiles.entrySet()) {
			GameUtils.renderGameObject(spriteBatch, camera, entry.getKey(), entry.getValue());
		}
		spriteBatch.end();
	}

	public void createSpecialFX(EffectType type, float startX, float startY, float duration) {
		SpecialFX effect = poolEffects.obtain();
		effect.initialize(type, startX, startY, duration);
		activeEffects.add(effect);
		animatedCharacters.put(effect, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));
		SoundManager.INSTANCE.playSound(type.getSoundType());
	}

	public void spawnMissile(MissileType type, float startX, float startY) {
		final AnimatedSprite sprMissile = ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType());

		Missile missile = poolMissiles.obtain();
		missile.initialize(type, startX, startY, 0);
		activeMissiles.add(missile);
		animatedMissiles.put(missile, sprMissile);

		SoundManager.INSTANCE.playSound(type.getSoundType());
	}

	public void spawnBoss(BossType type, float spawnX, float spawnY) {
		Boss boss = poolBosses.obtain();
		boss.initialize(type, spawnX, spawnY);
		activeBosses.add(boss);
		animatedCharacters.put(boss, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));
	}

	public void setProtomanSpeed(float speed, float angleInDegrees) {
		protoman.setSpeed(speed, angleInDegrees);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.UP: {
				// move protoman up
				setProtomanSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
				break;
			}
			case Keys.DOWN: {
				// move protoman down
				setProtomanSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
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
					setProtomanSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
				} else if (Gdx.input.isKeyPressed(Keys.UP)) {
					// up key still pressed -> move protoman up
					setProtomanSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
				} else {
					// both keys released -> stop protoman
					setProtomanSpeed(0, 0);
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

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {
		lastMusicPosition = SoundManager.INSTANCE.stopCurrentMusic();
	}

	@Override
	public void resume() {
		SoundManager.INSTANCE.playMusic(currentMusic, true);
		SoundManager.INSTANCE.setMusicPosition(lastMusicPosition);
	}
}
