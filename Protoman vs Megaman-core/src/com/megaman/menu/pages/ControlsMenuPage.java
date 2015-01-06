package com.megaman.menu.pages;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.SkinType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.ResourceManager;

public class ControlsMenuPage extends GameMenuPage {
	private final int	OPTION_BACK	= 0;

	private BitmapFont	font;

	public ControlsMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
		font = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU).getFont("minecraft_26");
	}

	@Override
	public void initialize() {
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.back"), true, 490, 0, 0, 0);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		super.render(spriteBatch);

		spriteBatch.begin();
		// header
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.keys"), 145, 400);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.xbox"), 310, 400);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description"), 440, 400);
		font.draw(spriteBatch, "-----------------------", 145, 370);

		// keys
		font.draw(spriteBatch, "<Arrow", 145, 330);
		font.draw(spriteBatch, ">Arrow", 145, 295);
		font.draw(spriteBatch, "^Arrow", 145, 260);
		font.draw(spriteBatch, "vArrow", 145, 225);
		font.draw(spriteBatch, "Enter", 145, 190);
		font.draw(spriteBatch, "Escape", 145, 155);

		// xbox
		font.draw(spriteBatch, "<Pad", 310, 330);
		font.draw(spriteBatch, ">Pad", 310, 295);
		font.draw(spriteBatch, "^Pad", 310, 260);
		font.draw(spriteBatch, "vPad", 310, 225);
		font.draw(spriteBatch, "A", 310, 190);
		font.draw(spriteBatch, "Start", 310, 155);

		// controls
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.menudec"), 440, 330);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.menuinc"), 440, 295);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.moveup"), 440, 260);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.movedown"), 440, 225);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.menuselect"), 440, 190);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("MainMenu.option.settings.controls.description.pause"), 440, 155);
		spriteBatch.end();
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
