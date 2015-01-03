package com.megaman.menu.pages;

import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameMenuPageType;

public class SettingsMenuPage extends GameMenuPage {
	private final int	OPTION_VIDEO	= 0;
	private final int	OPTION_AUDIO	= 1;
	private final int	OPTION_CONTROLS	= 2;
	private final int	OPTION_BACK		= 3;

	public SettingsMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		addOption("video", true, GameConstants.MENU_OFFSET_TOP, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("audio", true, 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("controls", true, 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("back", true, 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
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
