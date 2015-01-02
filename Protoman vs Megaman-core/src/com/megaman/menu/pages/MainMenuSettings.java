package com.megaman.menu.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.enums.GameMenuPageType;

public class MainMenuSettings extends GameMenuPage {
	private final int	OPTION_VIDEO	= 0;
	private final int	OPTION_AUDIO	= 1;
	private final int	OPTION_CONTROLS	= 2;
	private final int	OPTION_BACK		= 3;

	public MainMenuSettings(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		addOption("video", skin.get("default", LabelStyle.class), GameConstants.MENU_OFFSET_TOP, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("audio", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("controls", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("back", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public void processSelection(int optionIndex) {
		switch (optionIndex) {
			case OPTION_VIDEO: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS_VIDEO);
				break;
			}
			case OPTION_CONTROLS: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS_CONTROLS);
				break;
			}
			case OPTION_AUDIO: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS_AUDIO);
				break;
			}
			case OPTION_BACK: {
				gameMenu.changeMenuPage(null);
				break;
			}
		}
	}

}
