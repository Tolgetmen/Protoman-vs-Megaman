package com.megaman.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.graphics.AnimatedSprite;
import com.gdxgame.core.utils.ResourceManager;
import com.gdxgame.core.utils.SoundManager;

public class MegamanMenu extends GameMenu {
	protected AnimatedSprite	megaman;
	protected AnimatedSprite	protoman;
	protected AnimatedSprite	missile;

	protected float				missileX;
	private float				missileSpeed;
	private float				currentMissileFrameX;
	private float				missileFPS;

	/**
	 * if true the menu will no longer call the pages keyDown() method
	 */
	private boolean				disableControls;

	public MegamanMenu(GameStateLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage) {
		super(logic, menuWidth, menuHeight, stretch, startPage);

		megaman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.MENU_MEGAMAN);
		protoman = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.MENU_PROTOMAN);
		missile = ResourceManager.INSTANCE.getAnimatedSprite(TextureType.MENU_MISSLE);

		missileX = -100;
		disableControls = false;

		protoman.setFrameIndex(1, 0);
		missile.setFrameIndex(0, 0);
		megaman.scale(1.2f);
		missile.scale(1.2f);
		protoman.scale(1.2f);
	}

	@Override
	public void increaseSelection() {
		super.increaseSelection();
		SoundManager.INSTANCE.playSound(SoundType.MENU_MOVE);
	}

	@Override
	public void decreaseSelection() {
		super.decreaseSelection();
		SoundManager.INSTANCE.playSound(SoundType.MENU_MOVE);
	}

	@Override
	public boolean keyDown(int keyCode) {
		if (!super.keyDown(keyCode)) {

			if (disableControls) {
				// a selection is already in progress -> ignore any other key input
				return true;
			}

			switch (keyCode) {
				case Keys.UP: {
					decreaseSelection();
					break;
				}
				case Keys.DOWN: {
					increaseSelection();
					break;
				}
				case Keys.ENTER: {
					startSelectionMissile();
					break;
				}
			}
		}

		return true;
	}

	@Override
	public void processSelection() {
		disableControls = false;
		super.processSelection();
	}

	public void startSelectionMissile() {
		disableControls = true;
		missileSpeed = 600;
		currentMissileFrameX = 0;
		missileFPS = 10;
		missile.setFrameIndex(0, 0);
		megaman.setFrameIndex(2, 0);
		missileX = getCurrentOption().getX() - megaman.getWidth() - 30;
		SoundManager.INSTANCE.playSound(SoundType.MENU_SELECT_SHOOT);
	}

	public void update(float deltaTime) {
		missileX += missileSpeed * deltaTime;
		currentMissileFrameX = (currentMissileFrameX + missileFPS * deltaTime) % 9;
		missile.setFrameIndex((int) currentMissileFrameX, 0);

		if (missileX + missile.getWidth() > getCurrentOption().getX() + getCurrentOption().getWidth() + 15) {
			// missile is at the end -> process the current selection
			missileSpeed = 0;
			currentMissileFrameX = 0;
			missileFPS = 0;
			megaman.setFrameIndex(0, 0);
			missileX = -100;
			processSelection();
		}
	}

	public void render(SpriteBatch spriteBatch, Camera camera) {
		super.render();

		spriteBatch.begin();
		spriteBatch.draw(megaman, getCurrentOption().getX() - megaman.getWidth() - 40, getCurrentOption().getY(), megaman.getOriginX(), megaman.getOriginY(), megaman.getWidth(), megaman.getHeight(), megaman.getScaleX(), megaman.getScaleY(), megaman.getRotation());
		spriteBatch.draw(protoman, getCurrentOption().getX() + getCurrentOption().getWidth() + 40, getCurrentOption().getY() - 10, protoman.getOriginX(), protoman.getOriginY(), protoman.getWidth(), protoman.getHeight(), protoman.getScaleX(), protoman.getScaleY(), protoman.getRotation());
		spriteBatch.draw(missile, missileX, getCurrentOption().getY() + 4, missile.getOriginX(), missile.getOriginY(), missile.getWidth(), missile.getHeight(), missile.getScaleX(), missile.getScaleY(), missile.getRotation());
		spriteBatch.end();
	}

	public void disableControls() {
		disableControls = true;
	}
}
