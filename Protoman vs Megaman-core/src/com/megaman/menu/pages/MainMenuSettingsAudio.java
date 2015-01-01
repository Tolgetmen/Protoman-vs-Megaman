package com.megaman.menu.pages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.megaman.constants.GameConstants;
import com.megaman.core.GameLogic;
import com.megaman.core.GameMenu;
import com.megaman.core.MenuPage;
import com.megaman.enums.AudioType;
import com.megaman.utils.GameUtils;

public class MainMenuSettingsAudio extends MenuPage {
	private final int	OPTION_MUSIC			= 0;
	private final int	OPTION_MUSIC_INFO		= 1;
	private final int	OPTION_SOUND			= 2;
	private final int	OPTION_SOUND_INFO		= 3;
	private final int	OPTION_JUKEBOX			= 4;
	private final int	OPTION_JUKEBOX_TITLE	= 5;
	private final int	OPTION_BACK				= 6;

	private int			volumeMusic;
	private int			volumeSound;
	private AudioType[]	jukeBoxMusic;
	private int			currentJukeboxTitle;

	public MainMenuSettingsAudio(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		super(gameMenu, logic, skin, fill, background);
	}

	@Override
	public void initialize() {
		volumeMusic = 100;
		volumeSound = 100;
		currentJukeboxTitle = 0;
		jukeBoxMusic = new AudioType[] { AudioType.MUSIC_MENU, AudioType.MUSIC_GEMINIMAN, AudioType.MUSIC_HARDMAN, AudioType.MUSIC_MAGNETMAN, AudioType.MUSIC_NEEDLEMAN, AudioType.MUSIC_PROTOMAN, AudioType.MUSIC_SHADOWMAN, AudioType.MUSIC_SNAKEMAN, AudioType.MUSIC_SPARKMAN, AudioType.MUSIC_TOPMAN,
				AudioType.MUSIC_WILY_STAGE };

		addOption("music", skin.get("default", LabelStyle.class), GameConstants.MENU_OFFSET_TOP, 0, 0, 0);
		addOption("" + volumeMusic, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0, false);
		addOption("sound", skin.get("default", LabelStyle.class), 0, 0, 0, 0);
		addOption("" + volumeSound, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0, false);
		addOption("jukebox", skin.get("default", LabelStyle.class), 0, 0, 0, 0);
		addOption(jukeBoxMusic[currentJukeboxTitle].getAudioName(), skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0, false);
		addOption("back", skin.get("default", LabelStyle.class), 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public boolean keyDown(int optionIndex, int keyCode) {
		switch (optionIndex) {
			case OPTION_MUSIC: {
				if (Keys.LEFT == keyCode) {
					volumeMusic = Math.max(0, volumeMusic - 5);
				} else if (Keys.RIGHT == keyCode) {
					volumeMusic = Math.min(100, volumeMusic + 5);
				} else if (Keys.ENTER == keyCode) {
					//return true in this case to not start the selection missile
					return true;
				}
				options.get(OPTION_MUSIC_INFO).setText("" + volumeMusic);
				break;
			}
			case OPTION_SOUND: {
				if (Keys.LEFT == keyCode) {
					volumeSound = Math.max(0, volumeSound - 5);
				} else if (Keys.RIGHT == keyCode) {
					volumeSound = Math.min(100, volumeSound + 5);
				} else if (Keys.ENTER == keyCode) {
					//return true in this case to not start the selection missile
					return true;
				}
				options.get(OPTION_SOUND_INFO).setText("" + volumeSound);
				break;
			}
			case OPTION_JUKEBOX: {
				if (Keys.LEFT == keyCode) {
					--currentJukeboxTitle;
					if (currentJukeboxTitle < 0)
						currentJukeboxTitle = jukeBoxMusic.length - 1;
				} else if (Keys.RIGHT == keyCode) {
					++currentJukeboxTitle;
					currentJukeboxTitle %= jukeBoxMusic.length;
				} else if (Keys.ENTER == keyCode) {
					//return true in this case to not start the selection missile
					return true;
				}
				GameUtils.playMusic(jukeBoxMusic[currentJukeboxTitle], true);
				options.get(OPTION_JUKEBOX_TITLE).setText(jukeBoxMusic[currentJukeboxTitle].getAudioName());
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
