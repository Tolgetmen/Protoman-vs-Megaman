package com.megaman.menu.pages;

import com.megaman.core.GDXGame;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameMenuPageType;

public class ControlsMenuPage extends GameMenuPage {
	private final int	OPTION_BACK	= 0;

	public ControlsMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		addOption("back", true, 450, 0, 0, 0);
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
