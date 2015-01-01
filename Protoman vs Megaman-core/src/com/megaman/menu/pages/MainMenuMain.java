package com.megaman.menu.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.MenuPage;
import com.megaman.enums.GameMenuPageType;
import com.megaman.enums.GameStateType;
import com.megaman.gamestates.logic.GSMainMenuLogic;

public class MainMenuMain extends MenuPage {
	private final int	OPTION_RESUME	= 0;
	private final int	OPTION_NEW		= 1;
	private final int	OPTION_SETTINGS	= 2;
	private final int	OPTION_EXIT		= 3;

	public MainMenuMain(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		addOption("resume game", skin.get("title_disabled", LabelStyle.class), GameConstants.MENU_OFFSET_TOP, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0, false);
		addOption("new game", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("settings", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("exit game", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public void processSelection(int optionIndex) {
		GSMainMenuLogic menuLogic = (GSMainMenuLogic) logic;

		switch (optionIndex) {
			case OPTION_RESUME: {
				break;
			}
			case OPTION_NEW: {
				menuLogic.changeGameState(GameStateType.GAME);
				break;
			}
			case OPTION_SETTINGS: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS);
				break;
			}
			case OPTION_EXIT: {
				menuLogic.changeGameState(null);
				break;
			}
		}
	}

}
