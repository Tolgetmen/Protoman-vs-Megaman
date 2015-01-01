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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.megaman.GDXGame;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.ResourceManager;
import com.megaman.enums.BossType;
import com.megaman.enums.AudioType;
import com.megaman.enums.GameStateType;
import com.megaman.enums.TextureType;
import com.megaman.enums.MissileType;
import com.megaman.graphics.AnimatedSprite;
import com.megaman.model.AnimatedGameObject;
import com.megaman.model.Boss;
import com.megaman.model.GameObject;
import com.megaman.model.Megaman;
import com.megaman.model.Missile;
import com.megaman.model.Protoman;
import com.megaman.utils.GameUtils;

public class GSGameLogic extends GameLogic {
	private Array<GameObject>				gameObjects;
	private Map<GameObject, AnimatedSprite>	animatedGameObjects;
	private Array<Missile>					activeMissiles;
	private Pool<Missile>					poolMissiles;
	private Array<Boss>						activeBosses;
	private Pool<Boss>						poolBosses;

	private Megaman							megaman;
	private Protoman						protoman;

	private GameStateType					nextState;

	private final int						PROTOMAN_SPEED	= 600;

	public GSGameLogic(GameState gameState, GDXGame game) {
		super(gameState, game);
	}

	@Override
	public void initialize() {
		GameUtils.playMusic(AudioType.MUSIC_PROTOMAN, true);

		nextState = null;
		gameObjects = new Array<GameObject>();
		animatedGameObjects = new HashMap<GameObject, AnimatedSprite>();
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

		final AnimatedSprite sprMegaman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_CHARACTER_MEGAMAN);
		final AnimatedSprite sprProtoman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_CHARACTER_PROTOMAN);

		megaman = new Megaman(this, 3, 1, 10);
		megaman.setLoopAnimations(0, 1);
		megaman.setPosition(0, GameConstants.GAME_HEIGHT / 2 - 16);
		megaman.setSize(sprMegaman.getWidth(), sprMegaman.getHeight());
		gameObjects.add(megaman);
		animatedGameObjects.put(megaman, sprMegaman);

		protoman = new Protoman(this, 2, 1, 10);
		protoman.setPosition(GameConstants.GAME_WIDTH - 80, GameConstants.GAME_HEIGHT / 2 - 16);
		protoman.setSize(sprProtoman.getWidth(), sprProtoman.getHeight());
		gameObjects.add(protoman);
		animatedGameObjects.put(protoman, sprProtoman);
	}

	@Override
	public void update(float deltaTime) {
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
				animatedGameObjects.remove(boss);
				iterBosses.remove();
			}
		}

		Iterator<Missile> iterMissiles = activeMissiles.iterator();
		while (iterMissiles.hasNext()) {
			Missile missile = iterMissiles.next();
			missile.update(deltaTime);

			if (missile.isAlive() && Intersector.overlaps(protoman.getBoundingRectangle(), missile.getBoundingRectangle())) {
				missile.kill();

				switch (missile.getType()) {
					case MEGAMAN: {
						GameUtils.playSound(AudioType.SOUND_BLOCK);
						break;
					}
					default: {
						GameUtils.playMusic(missile.getType().getMusic(), true);
						break;
					}
				}

			}

			if (!missile.isAlive()) {
				poolMissiles.free(missile);
				animatedGameObjects.remove(missile);
				iterMissiles.remove();
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, Camera camera) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		//		Gdx.app.log("Num animated objects", "" + animatedGameObjects.size());

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Map.Entry<GameObject, AnimatedSprite> entry : animatedGameObjects.entrySet()) {
			GameObject gameObj = entry.getKey();
			AnimatedSprite sprite = entry.getValue();

			if (GameUtils.isWithinCameraView(camera, gameObj)) {
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
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void spawnMissile(MissileType type, float startX, float startY) {
		final AnimatedSprite sprMissile = ResourceManager.INSTANCE.getAnimatedSprite(type.getGraphic());

		Missile missile = poolMissiles.obtain();
		missile.initialize(type, startX, startY, sprMissile.getWidth(), sprMissile.getHeight(), 0);
		activeMissiles.add(missile);
		animatedGameObjects.put(missile, sprMissile);

		GameUtils.playSound(type.getSound());
	}

	public void spawnBoss(BossType type, float spawnX, float spawnY) {
		final AnimatedSprite sprite = ResourceManager.INSTANCE.getAnimatedSprite(type.getGraphic());

		Boss boss = poolBosses.obtain();
		boss.initialize(type, spawnX, spawnY, sprite.getWidth(), sprite.getHeight());
		activeBosses.add(boss);
		animatedGameObjects.put(boss, sprite);
	}

	public void setProtomanSpeed(float speed, float angleInDegrees) {
		protoman.setSpeed(speed, angleInDegrees);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.UP: {
				// move protoman up
				setProtomanSpeed(PROTOMAN_SPEED, 90);
				break;
			}
			case Keys.DOWN: {
				// move protoman down
				setProtomanSpeed(PROTOMAN_SPEED, 270);
				break;
			}
			case Keys.ESCAPE: {
				// switch to state menu
				nextState = GameStateType.MAIN_MENU;
				game.closeCurrentGameState();
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
					setProtomanSpeed(PROTOMAN_SPEED, 270);
				} else if (Gdx.input.isKeyPressed(Keys.UP)) {
					// up key still pressed -> move protoman up
					setProtomanSpeed(PROTOMAN_SPEED, 90);
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
	public GameStateType getNextState() {
		return nextState;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}
}
