package com.megaman.core.enums;

import com.megaman.core.GameMenuPage;
import com.megaman.menu.pages.GameOverPage;
import com.megaman.menu.pages.HighscorePage;
import com.megaman.menu.pages.MainMenuMain;
import com.megaman.menu.pages.MainMenuSettings;
import com.megaman.menu.pages.MainMenuSettingsAudio;
import com.megaman.menu.pages.MainMenuSettingsControls;
import com.megaman.menu.pages.MainMenuSettingsVideo;

public enum GameMenuPageType {
	MAIN_MENU_MAIN(MainMenuMain.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS(MainMenuSettings.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS_VIDEO(MainMenuSettingsVideo.class, SkinType.SKIN_MAIN_MENU, "background"),
	MAIN_MENU_SETTINGS_CONTROLS(MainMenuSettingsControls.class, SkinType.SKIN_MAIN_MENU, "background_controls"),
	MAIN_MENU_SETTINGS_AUDIO(MainMenuSettingsAudio.class, SkinType.SKIN_MAIN_MENU, "background"),

	GAME_OVER(GameOverPage.class, SkinType.SKIN_MAIN_MENU, "background_game_over"),

	HIGHSCORE(HighscorePage.class, SkinType.SKIN_MAIN_MENU, "background_high_score");

	private final Class<? extends GameMenuPage>	pageClass;
	private final SkinType						skinType;
	private final String						skinBackgroundImage;

	private GameMenuPageType(Class<? extends GameMenuPage> pageClass, SkinType skinType, String skinBackgroundImage) {
		this.pageClass = pageClass;
		this.skinType = skinType;
		this.skinBackgroundImage = skinBackgroundImage;
	}

	public Class<? extends GameMenuPage> getPageClass() {
		return pageClass;
	}

	public SkinType getSkinType() {
		return skinType;
	}

	public String getSkinBackgroundImage() {
		return skinBackgroundImage;
	}
}
