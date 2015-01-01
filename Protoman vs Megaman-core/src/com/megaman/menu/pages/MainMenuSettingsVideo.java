package com.megaman.menu.pages;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.MenuPage;

public class MainMenuSettingsVideo extends MenuPage {
	private final int				OPTION_FULLSCREEN		= 0;
	private final int				OPTION_FULLSCREEN_INFO	= 1;
	private final int				OPTION_WINDOW_SIZE		= 2;
	private final int				OPTION_WINDOW_SIZE_INFO	= 3;
	private final int				OPTION_BACK				= 4;

	private boolean					fullscreen;
	private Map<Integer, Integer>	availableDisplayModes;
	private int						currentMode;

	public MainMenuSettingsVideo(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		fullscreen = false;
		currentMode = 800;
		availableDisplayModes = new TreeMap<Integer, Integer>();
		DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();
		// store all remaining 4:3 resolutions
		final double aspect43 = 4.0 / 3.0;
		for (DisplayMode mode : displayModes) {
			// get current game resolution mode
			double aspect = 1.0 * mode.width / mode.height;
			if (aspect == aspect43 && !availableDisplayModes.containsKey(mode.width)) {
				availableDisplayModes.put(mode.width, mode.height);
			}
		}

		addOption("fullscreen", skin.get("default", LabelStyle.class), GameConstants.MENU_OFFSET_TOP, 0, 0, 0);
		addOption("" + fullscreen, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0, false);
		addOption("window size", skin.get("default", LabelStyle.class), 0, 0, 0, 0, !fullscreen);
		addOption("" + currentMode + " x " + availableDisplayModes.get(currentMode), skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0, false);
		addOption("back", skin.get("default", LabelStyle.class), 0, 0, 0, 0);
	}

	private int getPreviousModeKey(int currentMode) {
		Iterator<Integer> iterator = availableDisplayModes.keySet().iterator();
		int previous = iterator.next();
		while (iterator.hasNext()) {
			int width = iterator.next();
			if (width == currentMode) {
				return previous;
			}
			previous = width;
		}
		return previous;
	}

	private int getNextModeKey(int currentMode) {
		Iterator<Integer> iterator = availableDisplayModes.keySet().iterator();
		int smallestWidth = iterator.next();
		if (smallestWidth == currentMode && iterator.hasNext()) {
			return iterator.next();
		}

		while (iterator.hasNext()) {
			int width = iterator.next();
			if (width == currentMode) {
				if (iterator.hasNext()) {
					return iterator.next();
				} else {
					return smallestWidth;
				}
			}
		}
		return smallestWidth;
	}

	@Override
	public boolean keyDown(int optionIndex, int keyCode) {
		switch (optionIndex) {
			case OPTION_FULLSCREEN: {
				if (Keys.LEFT == keyCode || Keys.RIGHT == keyCode) {
					fullscreen = !fullscreen;
				} else if (Keys.ENTER == keyCode) {
					//return true in this case to not start the selection missile
					return true;
				}
				if (fullscreen) {
					// set window configuration to primary device
					DisplayMode desktopDisplayMode = Gdx.graphics.getDesktopDisplayMode();
					Gdx.graphics.setDisplayMode(desktopDisplayMode.width, desktopDisplayMode.height, fullscreen);
					options.get(OPTION_WINDOW_SIZE_INFO).setText("" + desktopDisplayMode.width + " x " + desktopDisplayMode.height);
				} else {
					Gdx.graphics.setDisplayMode(currentMode, availableDisplayModes.get(currentMode), fullscreen);
					options.get(OPTION_WINDOW_SIZE_INFO).setText("" + currentMode + " x " + availableDisplayModes.get(currentMode));
				}
				options.get(OPTION_FULLSCREEN_INFO).setText("" + fullscreen);
				enableOption(OPTION_WINDOW_SIZE, !fullscreen, fullscreen ? skin.get("title_disabled", LabelStyle.class) : skin.get("default", LabelStyle.class));
				break;
			}
			case OPTION_WINDOW_SIZE: {
				if (isOptionEnabled(OPTION_WINDOW_SIZE)) {
					if (Keys.LEFT == keyCode) {
						currentMode = getPreviousModeKey(currentMode);
					} else if (Keys.RIGHT == keyCode) {
						currentMode = getNextModeKey(currentMode);
					} else if (Keys.ENTER == keyCode) {
						//return true in this case to not start the selection missile
						return true;
					}
					Gdx.graphics.setDisplayMode(currentMode, availableDisplayModes.get(currentMode), fullscreen);
					options.get(OPTION_WINDOW_SIZE_INFO).setText("" + currentMode + " x " + availableDisplayModes.get(currentMode));
				}
				break;
			}
		}

		return false;
	}

	@Override
	public void processSelection(int optionIndex) {
		switch (optionIndex) {
			case OPTION_BACK: {
				gameMenu.changeMenuPage(null);
				break;
			}
		}
	}
}
