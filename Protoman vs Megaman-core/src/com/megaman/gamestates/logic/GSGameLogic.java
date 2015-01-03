package com.megaman.gamestates.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.megaman.ai.states.MettoolState;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SoundType;
import com.megaman.core.enums.TextureType;
import com.megaman.core.graphics.AnimatedSprite;
import com.megaman.core.model.AnimatedGameObject;
import com.megaman.core.model.GameObject;
import com.megaman.core.utils.GameUtils;
import com.megaman.core.utils.ResourceManager;
import com.megaman.core.utils.SoundManager;
import com.megaman.enums.BossType;
import com.megaman.enums.MissileType;
import com.megaman.model.Boss;
import com.megaman.model.Megaman;
import com.megaman.model.Mettool;
import com.megaman.model.Missile;
import com.megaman.model.Protoman;
import com.megaman.model.SpecialFX;

public class GSGameLogic extends GameStateLogic {
	private Array<GameObject>				gameObjects;
	// create two maps for linking character/sprite and missile/sprite
	// since characters share one texture and missiles share one texture
	//
	// this should avoid texture binding calls and therefore increase render performance
	private Map<GameObject, AnimatedSprite>	animatedCharacters;
	private Map<GameObject, AnimatedSprite>	animatedMissiles;
	private Array<Missile>					activeMissiles;
	private Pool<Missile>					poolMissiles;
	private Array<Boss>						activeBosses;
	private Pool<Boss>						poolBosses;
	private Array<Mettool>					mettools;
	private Array<SpecialFX>				activeEffects;
	private Pool<SpecialFX>					poolEffects;

	private Megaman							megaman;
	private Protoman						protoman;

	private int								life;
	private int								blockedNormal;
	private int								blockedBoss;

	private MusicType						currentMusic;
	private float							lastMusicPosition;

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
		animatedCharacters = new HashMap<GameObject, AnimatedSprite>();
		animatedMissiles = new HashMap<GameObject, AnimatedSprite>();
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

		final AnimatedSprite sprMegaman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_CHARACTER_MEGAMAN);
		final AnimatedSprite sprProtoman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_CHARACTER_PROTOMAN);
		final AnimatedSprite sprMettool = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_CHARACTER_METTOOL);

		megaman = new Megaman(this, 3, 1, 10);
		megaman.setAnimation(0);
		megaman.setPosition(0, GameConstants.GAME_HEIGHT / 2 - 16);
		megaman.setSize(sprMegaman.getWidth(), sprMegaman.getHeight());
		gameObjects.add(megaman);
		animatedCharacters.put(megaman, sprMegaman);

		protoman = new Protoman(this, 2, 1, 10);
		protoman.setPosition(GameConstants.GAME_WIDTH - 90, GameConstants.GAME_HEIGHT / 2 - 16);
		protoman.setSize(sprProtoman.getWidth(), sprProtoman.getHeight());
		gameObjects.add(protoman);
		animatedCharacters.put(protoman, sprProtoman);

		life = GameConstants.MAX_LIFE;
		blockedNormal = 0;
		blockedBoss = 0;

		mettools = new Array<Mettool>();
		for (int i = 0; i < life; ++i) {
			Mettool mettool = new Mettool(this, TextureType.TEXTURE_CHARACTER_METTOOL.getNumColumns(), TextureType.TEXTURE_CHARACTER_METTOOL.getNumRows(), 3);
			mettool.setAnimation(0);
			if (i < 10) {
				mettool.setPosition(GameConstants.GAME_WIDTH - 32, 160 + 32 * i);
			} else {
				mettool.setPosition(GameConstants.GAME_WIDTH - 64, 160 + 32 * (i - 10));
			}
			mettool.setSize(40, 40);
			mettool.flip(true, false);
			mettools.add(mettool);
			animatedCharacters.put(mettool, sprMettool);
		}
	}

	@Override
	public void update(float deltaTime) {
		// check for end condition
		if (megaman.getRemainingShots() <= 0 && activeMissiles.size == 0 && life > 0) {
			// survived ! -> show highscore
			Map<String, Integer> highscore = new HashMap<String, Integer>();
			highscore.put("blocked", blockedNormal + blockedBoss);
			highscore.put("blocked_normal", blockedNormal);
			highscore.put("blocked_boss", blockedBoss);
			highscore.put("leaked", GameConstants.MAX_LIFE - life);
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
						createSpecialFX(mettools.get(life).getX(), mettools.get(life).getY());
					}
				} else {
					--life;
					if (mettools.size > life) {
						mettools.get(life).changeState(MettoolState.FLEE);
						createSpecialFX(mettools.get(life).getX(), mettools.get(life).getY());
					}
					--life;
					if (mettools.size > life) {
						mettools.get(life).changeState(MettoolState.FLEE);
						createSpecialFX(mettools.get(life).getX(), mettools.get(life).getY());
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

	private void renderSprites(SpriteBatch spriteBatch, Map<GameObject, AnimatedSprite> sprites) {
		for (Map.Entry<GameObject, AnimatedSprite> entry : sprites.entrySet()) {
			GameObject gameObj = entry.getKey();
			AnimatedSprite sprite = entry.getValue();

			if (GameUtils.isWithinCameraView(camera, gameObj)) {
				sprite.flip(gameObj.isFlipX(), gameObj.isFlipY());
				if (gameObj instanceof AnimatedGameObject) {
					AnimatedGameObject aniGameObj = (AnimatedGameObject) gameObj;
					sprite.setFrameIndex(aniGameObj.getCurrentColumn(), aniGameObj.getCurrentRow());
				}
				sprite.setPosition(gameObj.getX(), gameObj.getY());
				sprite.setSize(gameObj.getWidth(), gameObj.getHeight());
				final Color color = sprite.getColor();
				spriteBatch.setColor(color.r, color.g, color.b, 1.0f - gameObj.getTransparency());
				spriteBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		renderSprites(spriteBatch, animatedCharacters);
		renderSprites(spriteBatch, animatedMissiles);
		spriteBatch.end();
	}

	public void createSpecialFX(float startX, float startY) {
		SpecialFX effect = poolEffects.obtain();
		effect.initialize(startX, startY, 42, 42, 3, 1, 3);
		activeEffects.add(effect);
		animatedCharacters.put(effect, ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_EFFECT_HIT));
		SoundManager.INSTANCE.playSound(SoundType.HIT);
	}

	public void spawnMissile(MissileType type, float startX, float startY) {
		final AnimatedSprite sprMissile = ResourceManager.INSTANCE.getAnimatedSprite(type.getGraphic());

		Missile missile = poolMissiles.obtain();
		missile.initialize(type, startX, startY, sprMissile.getWidth(), sprMissile.getHeight(), 0);
		activeMissiles.add(missile);
		animatedMissiles.put(missile, sprMissile);

		SoundManager.INSTANCE.playSound(type.getSound());
	}

	public void spawnBoss(BossType type, float spawnX, float spawnY) {
		final AnimatedSprite sprite = ResourceManager.INSTANCE.getAnimatedSprite(type.getGraphic());

		Boss boss = poolBosses.obtain();
		boss.initialize(type, spawnX, spawnY, sprite.getWidth(), sprite.getHeight());
		activeBosses.add(boss);
		animatedCharacters.put(boss, sprite);
	}

	public void setProtomanSpeed(float speed, float angleInDegrees) {
		protoman.setSpeed(speed, angleInDegrees);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.UP: {
				// move protoman up
				setProtomanSpeed(GameConstants.PROTOMAN_SPEED, 90);
				break;
			}
			case Keys.DOWN: {
				// move protoman down
				setProtomanSpeed(GameConstants.PROTOMAN_SPEED, 270);
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
					setProtomanSpeed(GameConstants.PROTOMAN_SPEED, 270);
				} else if (Gdx.input.isKeyPressed(Keys.UP)) {
					// up key still pressed -> move protoman up
					setProtomanSpeed(GameConstants.PROTOMAN_SPEED, 90);
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
