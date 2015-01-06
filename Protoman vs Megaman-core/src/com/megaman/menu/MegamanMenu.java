package com.megaman.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.SkinType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.graphics.AnimatedSprite;
import com.gdxgame.core.utils.ResourceManager;
import com.gdxgame.core.utils.SoundManager;

public class MegamanMenu extends GameMenu {
	private final String		menuTitle;
	private final BitmapFont	font;

	private AnimatedSprite		megaman;
	private AnimatedSprite		protoman;
	private AnimatedSprite		missile;

	private float				missileX;
	private float				missileSpeed;
	private float				currentMissileFrameX;
	private float				missileFPS;

	/**
	 * if true the menu will no longer call the pages keyDown() method
	 */
	private boolean				disableControls;

	public MegamanMenu(String title, GameStateLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage) {
		super(logic, menuWidth, menuHeight, stretch, startPage);

		font = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU).getFont("minecraft_68_bold");
		menuTitle = title;

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
		missileSpeed = (getCurrentOption().getX() + getCurrentOption().getWidth() + 40) - (getCurrentOption().getX() - 40);
		currentMissileFrameX = 0;
		missileFPS = 9;
		missile.setFrameIndex(0, 0);
		megaman.setFrameIndex(2, 0);
		missileX = getCurrentOption().getX() - megaman.getWidth() - 30;

		// missile should only take 0.6 seconds to reach protoman
		missileSpeed /= 0.6;
		missileFPS /= 0.6;

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

	@Override
	public void render(SpriteBatch spriteBatch) {
		super.render(spriteBatch);

		spriteBatch.begin();
		font.drawWrapped(spriteBatch, menuTitle, 0, GameConstants.GAME_HEIGHT - 60, GameConstants.GAME_WIDTH, HAlignment.CENTER);

		spriteBatch.draw(megaman, getCurrentOption().getX() - megaman.getWidth() - 40, getCurrentOption().getY() + 20, megaman.getOriginX(), megaman.getOriginY(), megaman.getWidth(), megaman.getHeight(), megaman.getScaleX(), megaman.getScaleY(), megaman.getRotation());
		spriteBatch.draw(protoman, getCurrentOption().getX() + getCurrentOption().getWidth() + 40, getCurrentOption().getY() + 10, protoman.getOriginX(), protoman.getOriginY(), protoman.getWidth(), protoman.getHeight(), protoman.getScaleX(), protoman.getScaleY(), protoman.getRotation());
		spriteBatch.draw(missile, missileX, getCurrentOption().getY() + 24, missile.getOriginX(), missile.getOriginY(), missile.getWidth(), missile.getHeight(), missile.getScaleX(), missile.getScaleY(), missile.getRotation());
		spriteBatch.end();
	}

	public void disableControls() {
		disableControls = true;
	}
}
