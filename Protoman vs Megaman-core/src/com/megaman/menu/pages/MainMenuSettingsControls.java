package com.megaman.menu.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;

public class MainMenuSettingsControls extends GameMenuPage {
	private final int	OPTION_BACK	= 0;

	public MainMenuSettingsControls(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		addOption("back", skin.get("default", LabelStyle.class), 400, 0, 0, 0);
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
