package com.megaman.menu.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.enums.GameStateType;
import com.megaman.gamestates.logic.GSGameOverLogic;

public class GameOverPage extends GameMenuPage {
	private final int	OPTION_RETRY	= 0;
	private final int	OPTION_QUIT		= 1;

	public GameOverPage(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		addOption("retry", skin.get("default", LabelStyle.class), 400, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("exit game", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public void processSelection(int optionIndex) {
		GSGameOverLogic menuLogic = (GSGameOverLogic) logic;

		switch (optionIndex) {
			case OPTION_RETRY: {
				menuLogic.changeGameState(GameStateType.GAME, true);
				break;
			}
			case OPTION_QUIT: {
				menuLogic.changeGameState(null, true);
				break;
			}
		}
	}

}
