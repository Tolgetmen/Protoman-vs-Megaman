package com.gdxgame.core.enums;

import com.gdxgame.core.GameMenuPage;
import com.megaman.menu.pages.GameOverPage;
import com.megaman.menu.pages.HighscorePage;
import com.megaman.menu.pages.LanguageMenuPage;
import com.megaman.menu.pages.MainMenuPage;
import com.megaman.menu.pages.SettingsMenuPage;
import com.megaman.menu.pages.AudioMenuPage;
import com.megaman.menu.pages.ControlsMenuPage;
import com.megaman.menu.pages.VideoMenuPage;

/**
 * GameMenuPageType is the configuration enum to define GameMenuPages. 
 * Each value contains the class representing the page logic, the skin to be used for the page
 * and an optional background image for the table instance of the GameMenuPage
 */
public enum GameMenuPageType {
	MAIN_MENU_MAIN(MainMenuPage.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS(SettingsMenuPage.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS_VIDEO(VideoMenuPage.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS_CONTROLS(ControlsMenuPage.class, SkinType.SKIN_MAIN_MENU, "background_infobox"),
	MAIN_MENU_SETTINGS_AUDIO(AudioMenuPage.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS_LANGUAGE(LanguageMenuPage.class, SkinType.SKIN_MAIN_MENU, "background"),

	GAME_OVER(GameOverPage.class, SkinType.SKIN_MAIN_MENU, "background"),

	HIGHSCORE(HighscorePage.class, SkinType.SKIN_MAIN_MENU, "background_infobox");

	/**
	 * class representing the page logic
	 */
	private final Class<? extends GameMenuPage>	pageClass;
	/**
	 * type of skin to be used for the options
	 */
	private final SkinType						skinType;
	/**
	 * optional background image of the texture atlas of the skin for the table instance of the GameMenuPage
	 */
	private final String						skinBackgroundImage;

	private GameMenuPageType(Class<? extends GameMenuPage> pageClass, SkinType skinType, String skinBackgroundImage) {
		this.pageClass = pageClass;
		this.skinType = skinType;
		this.skinBackgroundImage = skinBackgroundImage;
	}

	/**
	 * returns the class type containing the page logic
	 * 
	 * @return type of class containing the page logic
	 */
	public Class<? extends GameMenuPage> getPageClass() {
		return pageClass;
	}

	/**
	 * returns the type of skin to be used for the page's options
	 * 
	 * @return type of skin used for page's option
	 */
	public SkinType getSkinType() {
		return skinType;
	}

	/**
	 * returns identifier within the texture atlas of the skin that defines the background image
	 * 
	 * @return <b>null</b> if there is no background image specified. Otherwise it returns the identifier within the texture atlas
	 */
	public String getSkinBackgroundImage() {
		return skinBackgroundImage;
	}
}
