package com.megaman.menu;

import java.util.Map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.SkinType;
import com.gdxgame.core.utils.ResourceManager;

public class HighscoreMenu extends MegamanMenu {
	private BitmapFont				font;
	private Map<String, Integer>	highscore;

	@SuppressWarnings("unchecked")
	public HighscoreMenu(GameStateLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage, Object highscore) {
		super(logic, menuWidth, menuHeight, stretch, startPage);

		font = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU).getFont("8bit_25");
		this.highscore = (Map<String, Integer>) highscore;
	}

	@Override
	public void render(SpriteBatch spriteBatch, Camera camera) {
		super.render(spriteBatch, camera);

		spriteBatch.begin();
		font.draw(spriteBatch, "" + highscore.get("blocked"), 500, 600 - 201 + 25);
		font.draw(spriteBatch, "" + highscore.get("blocked_normal"), 500, 600 - 230 + 25);
		font.draw(spriteBatch, "" + highscore.get("blocked_boss"), 500, 600 - 264 + 25);
		font.draw(spriteBatch, "" + highscore.get("leaked"), 500, 600 - 297 + 25);
		font.draw(spriteBatch, "" + highscore.get("life"), 500, 600 - 345 + 25);
		font.draw(spriteBatch, "" + highscore.get("points"), 500, 600 - 440 + 25);
		spriteBatch.end();
	}
}
