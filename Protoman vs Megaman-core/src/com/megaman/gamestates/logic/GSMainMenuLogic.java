package com.megaman.gamestates.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.megaman.GDXGame;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.core.ResourceManager;
import com.megaman.enums.AudioType;
import com.megaman.enums.GameStateType;
import com.megaman.enums.SkinType;
import com.megaman.enums.TextureType;
import com.megaman.graphics.AnimatedSprite;
import com.megaman.utils.GameUtils;

public class GSMainMenuLogic extends GameLogic implements OnCompletionListener {
	private Stage			stage;
	private Table			table;

	private Group			options;

	private Label[]			lblOptions;
	private int				currentSelection;

	private AnimatedSprite	megaman;
	private AnimatedSprite	protoman;
	private AnimatedSprite	missile;

	private float			missileX;
	private float			missileSpeed;
	private float			currentMissileFrameX;
	private float			missileFPS;

	public GSMainMenuLogic(GameState gameState, GDXGame game) {
		super(gameState, game);
	}

	@Override
	public void initialize() {
		stage = new Stage(new StretchViewport(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT));

		createOptionsGroup();
		createSelectionSprites();

		currentSelection = 1;
		missileX = -100;

		table.setFillParent(true);
		//stage.addActor(background);
		stage.addActor(table);

		GameUtils.playMusic(AudioType.MUSIC_MENU, true);
	}

	private void createSelectionSprites() {
		megaman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_MENU_MEGAMAN);
		protoman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_MENU_PROTOMAN);
		missile = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.TEXTURE_MENU_MISSLE);

		protoman.setFrameIndex(1, 0);
		missile.setFrameIndex(0, 0);
		megaman.scale(1.2f);
		missile.scale(1.2f);
		protoman.scale(1.2f);
	}

	private void createOptionsGroup() {
		options = new Group();
		table = new Table();
		lblOptions = new Label[4];

		Skin skin = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU);
		lblOptions[0] = new Label("resume game", skin.get("default", LabelStyle.class));
		lblOptions[1] = new Label("new game", skin.get("default", LabelStyle.class));
		lblOptions[2] = new Label("settings", skin.get("default", LabelStyle.class));
		lblOptions[3] = new Label("exit game", skin.get("default", LabelStyle.class));

		table.add(lblOptions[0]).pad(240, 0, 50, 0).row();
		table.add(lblOptions[1]).padBottom(50).row();
		table.add(lblOptions[2]).padBottom(50).row();
		table.add(lblOptions[3]).row();

		table.setBackground(skin.getDrawable("background"));

		options.addActor(table);
	}

	private void selectOption() {
		missileSpeed = 600;
		currentMissileFrameX = 0;
		missileFPS = 10;
		missile.setFrameIndex(0, 0);
		megaman.setFrameIndex(2, 0);
		missileX = lblOptions[currentSelection].getX() - megaman.getWidth() - 30;
		GameUtils.playSound(AudioType.SOUND_MENU_SELECT_SHOOT);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		missileX += missileSpeed * deltaTime;
		currentMissileFrameX = (currentMissileFrameX + missileFPS * deltaTime) % 9;
		missile.setFrameIndex((int) currentMissileFrameX, 0);

		if (missileX + missile.getWidth() > lblOptions[currentSelection].getX() + lblOptions[currentSelection].getWidth() + 15) {
			// missile is at the end -> process the current selection
			missileSpeed = 0;
			currentMissileFrameX = 0;
			missileFPS = 0;
			megaman.setFrameIndex(0, 0);
			missileX = -100;
			processSelection();
		}
	}

	private void processSelection() {
		switch (currentSelection) {
			case (1): {
				// new game
				game.closeCurrentGameState();
				break;
			}
			case (3): {
				// quit game
				missileSpeed = 0.01f;
				GameUtils.playMusic(AudioType.MUSIC_MENU_QUIT, false, this);
				break;
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, Camera camera) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		stage.act();
		stage.draw();

		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(megaman, lblOptions[currentSelection].getX() - megaman.getWidth() - 40, lblOptions[currentSelection].getY(), megaman.getOriginX(), megaman.getOriginY(), megaman.getWidth(), megaman.getHeight(), megaman.getScaleX(), megaman.getScaleY(), megaman.getRotation());
		spriteBatch.draw(protoman, lblOptions[currentSelection].getX() + lblOptions[currentSelection].getWidth() + 40, lblOptions[currentSelection].getY() - 10, protoman.getOriginX(), protoman.getOriginY(), protoman.getWidth(), protoman.getHeight(), protoman.getScaleX(), protoman.getScaleY(),
				protoman.getRotation());
		spriteBatch.draw(missile, missileX, lblOptions[currentSelection].getY() + 4, missile.getOriginX(), missile.getOriginY(), missile.getWidth(), missile.getHeight(), missile.getScaleX(), missile.getScaleY(), missile.getRotation());
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public GameStateType getNextState() {
		switch (currentSelection) {
			case (1): {
				// new game
				return GameStateType.GAME;
			}
			case (3): {
				// quit game -> return null
				return null;
			}
			default: {
				return null;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (missileSpeed > 0) {
			// a selection is already in progress -> ignore any other key input
			return true;
		}

		switch (keycode) {
			case Keys.UP: {
				--currentSelection;
				if (currentSelection < 0)
					currentSelection = lblOptions.length - 1;
				GameUtils.playSound(AudioType.SOUND_MENU_MOVE);
				break;
			}
			case Keys.DOWN: {
				++currentSelection;
				currentSelection %= lblOptions.length;
				GameUtils.playSound(AudioType.SOUND_MENU_MOVE);
				break;
			}
			case Keys.ENTER: {
				selectOption();
				break;
			}
			case Keys.ESCAPE: {
				//TODO close menu
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
			default: {
				break;
			}
		}

		return true;
	}

	@Override
	public void onCompletion(Music music) {
		// used for "Quit game" option
		// wait until protoman music was played to close the game
		game.closeCurrentGameState();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
}
