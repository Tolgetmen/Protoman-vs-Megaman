package com.megaman.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.megaman.menu.pages.HighscorePage;

public class HighscoreMenu extends MegamanMenu {
	public HighscoreMenu(String title, GameStateLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage, Object highscore) {
		super(title, logic, menuWidth, menuHeight, stretch, startPage);

		GameMenuPage currentPage = getCurrentPage();
		if (currentPage instanceof HighscorePage) {
			((HighscorePage) currentPage).setHighscore(highscore);
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		super.render(spriteBatch);

	}
}
