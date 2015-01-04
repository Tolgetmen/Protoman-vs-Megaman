package com.megaman.menu.pages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SoundType;
import com.megaman.core.utils.SoundManager;

public class AudioMenuPage extends GameMenuPage {
	private final int	OPTION_MUSIC			= 0;
	private final int	OPTION_INFO				= 1;
	private final int	OPTION_SOUND			= 2;
	private final int	OPTION_SOUND_INFO		= 3;
	private final int	OPTION_JUKEBOX			= 4;
	private final int	OPTION_JUKEBOX_TITLE	= 5;
	private final int	OPTION_BACK				= 6;

	private MusicType[]	jukeBoxMusic;
	private int			currentJukeboxTitle;

	public AudioMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		currentJukeboxTitle = 0;
		jukeBoxMusic = new MusicType[] { MusicType.MENU, MusicType.GEMINIMAN, MusicType.HARDMAN, MusicType.MAGNETMAN, MusicType.NEEDLEMAN, MusicType.PROTOMAN, MusicType.SHADOWMAN, MusicType.SNAKEMAN, MusicType.SPARKMAN, MusicType.TOPMAN, MusicType.WILY_STAGE };

		addOption("music", true, GameConstants.MENU_OFFSET_TOP, 0, 0, 0);
		addOption("" + SoundManager.INSTANCE.getMusicVolume(), false, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption("sound", true, 0, 0, 0, 0);
		addOption("" + SoundManager.INSTANCE.getSoundVolume(), false, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption("jukebox", true, 0, 0, 0, 0);
		addOption(jukeBoxMusic[currentJukeboxTitle].getName(), false, skin.get("normal", LabelStyle.class), 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption("back", true, 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		switch (optionIndex) {
			case OPTION_MUSIC: {
				if (Keys.LEFT == keyOrButtonCode) {
					SoundManager.INSTANCE.setMusicVolume(SoundManager.INSTANCE.getMusicVolume() - 5);
				} else if (Keys.RIGHT == keyOrButtonCode) {
					SoundManager.INSTANCE.setMusicVolume(SoundManager.INSTANCE.getMusicVolume() + 5);
				} else if (Keys.ENTER == keyOrButtonCode) {
					//return true in this case to not start the selection missile
					return true;
				}

				options.get(OPTION_INFO).setText("" + SoundManager.INSTANCE.getMusicVolume());
				break;
			}
			case OPTION_SOUND: {
				if (Keys.LEFT == keyOrButtonCode) {
					SoundManager.INSTANCE.setSoundVolume(SoundManager.INSTANCE.getSoundVolume() - 5);
					SoundManager.INSTANCE.playSound(SoundType.MENU_SELECT_SHOOT);
				} else if (Keys.RIGHT == keyOrButtonCode) {
					SoundManager.INSTANCE.setSoundVolume(SoundManager.INSTANCE.getSoundVolume() + 5);
					SoundManager.INSTANCE.playSound(SoundType.MENU_SELECT_SHOOT);
				} else if (Keys.ENTER == keyOrButtonCode) {
					//return true in this case to not start the selection missile
					return true;
				}

				options.get(OPTION_SOUND_INFO).setText("" + SoundManager.INSTANCE.getSoundVolume());
				break;
			}
			case OPTION_JUKEBOX: {
				if (Keys.LEFT == keyOrButtonCode) {
					--currentJukeboxTitle;
					if (currentJukeboxTitle < 0) {
						currentJukeboxTitle = jukeBoxMusic.length - 1;
					}
				} else if (Keys.RIGHT == keyOrButtonCode) {
					++currentJukeboxTitle;
					currentJukeboxTitle %= jukeBoxMusic.length;
				} else if (Keys.ENTER == keyOrButtonCode) {
					//return true in this case to not start the selection missile
					return true;
				}

				SoundManager.INSTANCE.playMusic(jukeBoxMusic[currentJukeboxTitle], true);
				options.get(OPTION_JUKEBOX_TITLE).setText(jukeBoxMusic[currentJukeboxTitle].getName());
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
