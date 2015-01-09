package com.megaman.menu.pages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.utils.GameUtils;
import com.megaman.constants.MegamanConstants;

public class LanguageMenuPage extends GameMenuPage {
	private final int	OPTION_LANGUAGE	= 1;
	private final int	OPTION_INFO		= 2;
	private final int	OPTION_BACK		= 3;

	private String[]	availableLocales;
	private int			currentLocale;

	public LanguageMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		currentLocale = 0;
		availableLocales = new String[] { "de", "en" };
		String currentLanguage = GameUtils.getCfgPreferenceValue(GameConstants.PREFERENCE_KEY_LANGUAGE);
		if (currentLanguage != null) {
			for (int i = 0; i < availableLocales.length; ++i) {
				if (currentLanguage.equals(availableLocales[i])) {
					currentLocale = i;
					break;
				}
			}
		}

		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.language.changesinfo"), false, skin.get("menu_option_red", LabelStyle.class), MegamanConstants.MENU_OFFSET_TOP, 0, 30, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.language.language"), true, 30, 0, 10, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.language." + availableLocales[currentLocale]), false, skin.get("menu_suboption", LabelStyle.class), 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.back"), true, 30, 0, MegamanConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		switch (optionIndex) {
			case OPTION_LANGUAGE: {
				if (Keys.LEFT == keyOrButtonCode) {
					--currentLocale;
					if (currentLocale < 0) {
						currentLocale = availableLocales.length - 1;
					}
				} else if (Keys.RIGHT == keyOrButtonCode) {
					++currentLocale;
					currentLocale %= availableLocales.length;
				} else if (Keys.ENTER == keyOrButtonCode) {
					//return true in this case to not start the selection missile
					return true;
				}

				GameUtils.setCfgPreferenceValue(GameConstants.PREFERENCE_KEY_LANGUAGE, availableLocales[currentLocale]);
				options.get(OPTION_INFO).setText(GameUtils.getLocalizedLabel("MainMenu.option.settings.language." + availableLocales[currentLocale]));
				break;
			}
		}

		return false;
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
