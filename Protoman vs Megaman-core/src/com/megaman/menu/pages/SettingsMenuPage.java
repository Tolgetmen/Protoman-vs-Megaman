package com.megaman.menu.pages;

import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.utils.GameUtils;
import com.megaman.constants.MegamanConstants;

public class SettingsMenuPage extends GameMenuPage {
	private final int	OPTION_VIDEO	= 0;
	private final int	OPTION_AUDIO	= 1;
	private final int	OPTION_CONTROLS	= 2;
	private final int	OPTION_LANGUAGE	= 3;
	private final int	OPTION_BACK		= 4;

	public SettingsMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.video"), true, MegamanConstants.MENU_OFFSET_TOP, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS - 10, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.audio"), true, 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS - 10, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.controls"), true, 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS - 10, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.language"), true, 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS - 10, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.back"), true, 0, 0, MegamanConstants.MENU_OFFSET_BOTTOM, 0);
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
			case OPTION_LANGUAGE: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS_LANGUAGE);
				break;
			}
			case OPTION_BACK: {
				gameMenu.changeMenuPage(null);
				break;
			}
		}
	}

}
