package com.megaman.menu.pages;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.utils.GameUtils;

public class VideoMenuPage extends GameMenuPage {
	private final int				OPTION_FULLSCREEN		= 0;
	private final int				OPTION_FULLSCREEN_INFO	= 1;
	private final int				OPTION_WINDOW_SIZE		= 2;
	private final int				OPTION_WINDOW_SIZE_INFO	= 3;
	private final int				OPTION_BACK				= 4;

	// key == window width
	// value == window height
	private Map<Integer, Integer>	availableResolutions43;
	private int						currentMode;

	public VideoMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		currentMode = GameConstants.GAME_WIDTH;
		availableResolutions43 = new TreeMap<Integer, Integer>();
		DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();
		// store all remaining 4:3 resolutions
		final double aspect43 = 4.0 / 3.0;
		for (DisplayMode mode : displayModes) {
			// get current game resolution mode
			double aspect = 1.0 * mode.width / mode.height;
			if (aspect == aspect43 && !availableResolutions43.containsKey(mode.width)) {
				availableResolutions43.put(mode.width, mode.height);
			}
		}

		addOption("fullscreen", true, GameConstants.MENU_OFFSET_TOP, 0, 0, 0);
		addOption("" + GameUtils.getCfgFileValue("fullscreen", Boolean.class), false, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption("window size", !GameUtils.getCfgFileValue("fullscreen", Boolean.class), 0, 0, 0, 0);
		addOption("" + currentMode + " x " + availableResolutions43.get(currentMode), false, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption("back", true, 0, 0, 0, 0);
	}

	private int getPreviousModeKey(int currentMode) {
		Iterator<Integer> iterator = availableResolutions43.keySet().iterator();
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
		Iterator<Integer> iterator = availableResolutions43.keySet().iterator();
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

	private void updateVideoConfig(int width, int height, boolean fullscreen) {
		GameUtils.setCfgFileValue(GameConstants.CFG_KEY_FULLSCREEN, "" + fullscreen);
		GameUtils.setCfgFileValue(GameConstants.CFG_KEY_WIDTH, "" + width);
		GameUtils.setCfgFileValue(GameConstants.CFG_KEY_HEIGHT, "" + height);

		Gdx.graphics.setDisplayMode(currentMode, availableResolutions43.get(currentMode), fullscreen);

		options.get(OPTION_WINDOW_SIZE_INFO).setText("" + width + " x " + height);
		options.get(OPTION_FULLSCREEN_INFO).setText("" + fullscreen);
		enableOption(OPTION_WINDOW_SIZE, !fullscreen, fullscreen ? skin.get("title_disabled", LabelStyle.class) : skin.get("default", LabelStyle.class));
	}

	@Override
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		switch (optionIndex) {
			case OPTION_FULLSCREEN: {
				if (Keys.LEFT == keyOrButtonCode || Keys.RIGHT == keyOrButtonCode) {
					boolean fullscreen = GameUtils.getCfgFileValue("fullscreen", Boolean.class);
					fullscreen = !fullscreen;

					if (fullscreen) {
						// set window configuration to primary device
						DisplayMode desktopDisplayMode = Gdx.graphics.getDesktopDisplayMode();
						updateVideoConfig(desktopDisplayMode.width, desktopDisplayMode.height, fullscreen);
					} else {
						updateVideoConfig(currentMode, availableResolutions43.get(currentMode), fullscreen);
						Gdx.graphics.setDisplayMode(currentMode, availableResolutions43.get(currentMode), fullscreen);
					}
				} else if (Keys.ENTER == keyOrButtonCode) {
					//return true in this case to not start the selection missile
					return true;
				}

				break;
			}
			case OPTION_WINDOW_SIZE: {
				if (optionEnabled.get(OPTION_WINDOW_SIZE)) {
					if (Keys.LEFT == keyOrButtonCode) {
						currentMode = getPreviousModeKey(currentMode);
						updateVideoConfig(currentMode, availableResolutions43.get(currentMode), GameUtils.getCfgFileValue("fullscreen", Boolean.class));
					} else if (Keys.RIGHT == keyOrButtonCode) {
						currentMode = getNextModeKey(currentMode);
						updateVideoConfig(currentMode, availableResolutions43.get(currentMode), GameUtils.getCfgFileValue("fullscreen", Boolean.class));
					} else if (Keys.ENTER == keyOrButtonCode) {
						//return true in this case to not start the selection missile
						return true;
					}
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
