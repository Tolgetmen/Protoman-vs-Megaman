package com.megaman.gamestates.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.constants.GameConstants;
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
import com.megaman.enums.CharacterType;
import com.megaman.enums.EffectType;
import com.megaman.enums.MissileType;
import com.megaman.enums.ParticleFXType;
import com.megaman.model.Boss;
import com.megaman.model.Effect;
import com.megaman.model.Megaman;
import com.megaman.model.Mettool;
import com.megaman.model.Missile;
import com.megaman.model.ParticleFX;
import com.megaman.model.Protoman;

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
	private float									deathAnimationTime;

	private Array<Mettool>							mettools;

	private int										life;
	private int										blockedNormal;
	private int										blockedBoss;

	private OrthogonalTiledMapRenderer				renderer;

	private AnimatedSprite							hudIconLife;
	private AnimatedSprite							hudIconMissile;
	private BitmapFont								font;

	private Array<ParticleFX>						activeParticleEffects;
	private Pool<ParticleFX>						poolParticleEffects;
	private ParticleFX								protomanFlame;

	public GSGameLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	@Override
	public void initialize() {
		// start initial background music
		playMusic(MusicType.PROTOMAN);

		// load font and icons for hud
		font = new BitmapFont(Gdx.files.internal("fonts/minecraft_18.fnt"));
		hudIconLife = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.HUD_LIFE);
		hudIconMissile = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.HUD_MISSLES);

		// load tmx background map
		renderer = new OrthogonalTiledMapRenderer(ResourceManager.INSTANCE.getTMXMap(MegamanConstants.BACKGROUND_MAP_PATH));

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

		// create particle effect pool
		activeParticleEffects = new Array<ParticleFX>();
		poolParticleEffects = new Pool<ParticleFX>() {
			@Override
			protected ParticleFX newObject() {
				return new ParticleFX(GSGameLogic.this);
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
		if (!megaman.isAlive() || !protoman.isAlive()) {
			// megaman or protoman is dead -> wait 5 seconds until death animation is over
			if (deathAnimationTime <= 0) {
				if (!megaman.isAlive()) {
					// survived -> show highscore
					Map<String, Integer> highscore = new HashMap<String, Integer>();
					highscore.put("blocked", blockedNormal + blockedBoss);
					highscore.put("blocked_normal", blockedNormal);
					highscore.put("blocked_boss", blockedBoss);
					highscore.put("leaked", MegamanConstants.MAX_LIFE - life);
					highscore.put("life", life);
					highscore.put("points", (life * 1000) + (blockedBoss * 300) + (blockedNormal * 100));
					game.setGameState(GameStateType.HIGHSCORE, true, true, highscore);
				} else {
					// game over
					game.setGameState(GameStateType.GAME_OVER, true, true, null);
				}
			}
			return true;
		} else if (megaman.getShotCounter() >= MegamanConstants.MEGAMAN_MAX_MISSILES && activeMissiles.size == 0 && life > 0) {
			for (int i = 0; i < 16; ++i) {
				Missile missile = createMissile(MissileType.DEATH, megaman.getX(), megaman.getY());
				missile.setColor(0, 0.44f, 0.93f);
				missile.setSpeed((i / 8) * 80 + 45, (360 / 8) * (i % 8));
			}
			deathAnimationTime = 5.0f;
			megaman.kill();
			SoundManager.INSTANCE.stopCurrentMusic();
			SoundManager.INSTANCE.playSound(SoundType.DEATH);
			return true;
		} else if (life <= 0) {
			for (int i = 0; i < 16; ++i) {
				Missile missile = createMissile(MissileType.DEATH, protoman.getX(), protoman.getY());
				missile.setColor(1, 0.22f, 0);
				missile.setSpeed((i / 8) * 80 + 45, (360 / 8) * (i % 8));
			}
			deathAnimationTime = 5.0f;
			protoman.kill();
			protoman.setSpeed(0, 0);
			if (protomanFlame != null) {
				protomanFlame.stop();
				protomanFlame = null;
			}
			SoundManager.INSTANCE.stopCurrentMusic();
			SoundManager.INSTANCE.playSound(SoundType.DEATH);
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
		if (checkEndCondition()) {
			deathAnimationTime -= deltaTime;
			// update death animation missiles
			Iterator<Missile> iterMissiles = activeMissiles.iterator();
			while (iterMissiles.hasNext()) {
				Missile missile = iterMissiles.next();
				missile.update(deltaTime);

				if (deathAnimationTime <= 0) {
					missile.kill();
				}

				if (!missile.isAlive()) {
					poolMissiles.free(missile);
					animatedObjects.remove(missile);
					iterMissiles.remove();
				}
			}
		} else {
			// update particle effects
			Iterator<ParticleFX> iterParticleFX = activeParticleEffects.iterator();
			while (iterParticleFX.hasNext()) {
				ParticleFX particleFX = iterParticleFX.next();
				particleFX.update(deltaTime);

				if (particleFX.isComplete()) {
					poolParticleEffects.free(particleFX);
					iterParticleFX.remove();
				}
			}

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
		// render background tmx map
		renderer.setView((OrthographicCamera) camera);
		renderer.render();

		spriteBatch.begin();
		// render particle effects
		for (ParticleFX particleFX : activeParticleEffects) {
			particleFX.draw(spriteBatch);
		}

		// render game objects
		for (Map.Entry<AnimatedGameObject, AnimatedSprite> entry : animatedObjects.entrySet()) {
			GameUtils.renderGameObject(spriteBatch, camera, entry.getKey(), entry.getValue());
		}

		// render hud
		spriteBatch.draw(hudIconLife, 330, 22);
		spriteBatch.draw(hudIconMissile, 415, 26);
		font.draw(spriteBatch, "" + life, 355, 29);
		font.draw(spriteBatch, "" + (MegamanConstants.MEGAMAN_MAX_MISSILES - megaman.getShotCounter()), 430, 29);
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
		renderer.dispose();
		font.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.UP: {
				// move protoman up
				if (protoman.isAlive()) {
					protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
					if (protomanFlame == null) {
						protomanFlame = createParticleFX(ParticleFXType.FLAME, protoman, 13, 5, 0, 0.15f);
					}
				}
				break;
			}
			case Keys.DOWN: {
				// move protoman down
				if (protoman.isAlive()) {
					protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
					if (protomanFlame == null) {
						protomanFlame = createParticleFX(ParticleFXType.FLAME, protoman, 13, 5, 0, 0.15f);
					}
				}
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
				if (protoman.isAlive()) {
					if (Gdx.input.isKeyPressed(Keys.DOWN)) {
						// down key still pressed -> move protoman down
						protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 270);
					} else if (Gdx.input.isKeyPressed(Keys.UP)) {
						// up key still pressed -> move protoman up
						protoman.setSpeed(MegamanConstants.PROTOMAN_SPEED, 90);
					} else {
						// both keys released -> stop protoman
						protoman.setSpeed(0, 0);
						if (protomanFlame != null) {
							protomanFlame.stop();
							protomanFlame = null;
						}
					}
				}
				break;
			}
		}

		return true;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		switch (buttonCode) {
			case 7: {
				// XBOX START button
				keyDown(Keys.ESCAPE);
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

	public Effect createEffect(EffectType type, float startX, float startY, float duration) {
		Effect effect = poolEffects.obtain();

		effect.initialize(type, startX, startY, duration);
		activeEffects.add(effect);
		animatedObjects.put(effect, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));

		if (type.getSoundType() != null) {
			SoundManager.INSTANCE.playSound(type.getSoundType());
		}

		return effect;
	}

	public ParticleFX createParticleFX(ParticleFXType type, GameObject attachedObj, int objOffsetX, int objOffsetY, float duration, float loopPosition) {
		ParticleFX effect = poolParticleEffects.obtain();

		effect.initialize(type, attachedObj, objOffsetX, objOffsetY, duration, loopPosition);
		activeParticleEffects.add(effect);

		return effect;
	}

	public Missile createMissile(MissileType type, float startX, float startY) {
		Missile missile = poolMissiles.obtain();

		missile.initialize(type, startX, startY, 0);
		activeMissiles.add(missile);
		animatedObjects.put(missile, ResourceManager.INSTANCE.getAnimatedSprite(type.getTextureType()));

		if (type.getSoundType() != null) {
			SoundManager.INSTANCE.playSound(type.getSoundType());
		}

		return missile;
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
