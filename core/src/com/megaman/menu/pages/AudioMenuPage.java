package com.megaman.menu.pages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.constants.MegamanConstants;

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
	private final float	volumeUpdateTime		= 0.25f;
	private float		volumeUpdateTimeCurrent;
	private int			volumeChange;
	private boolean		music;

	public AudioMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		currentJukeboxTitle = 0;
		jukeBoxMusic = new MusicType[] { MusicType.MENU, MusicType.GEMINIMAN, MusicType.HARDMAN, MusicType.MAGNETMAN, MusicType.NEEDLEMAN, MusicType.PROTOMAN, MusicType.SHADOWMAN, MusicType.SNAKEMAN, MusicType.SPARKMAN, MusicType.TOPMAN, MusicType.WILY_STAGE };

		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.audio.music"), true, MegamanConstants.MENU_OFFSET_TOP, 0, 0, 0);
		addOption("" + SoundManager.INSTANCE.getMusicVolume(), false, skin.get("menu_suboption", LabelStyle.class), 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.audio.sound"), true, 0, 0, 0, 0);
		addOption("" + SoundManager.INSTANCE.getSoundVolume(), false, skin.get("menu_suboption", LabelStyle.class), 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings.audio.jukebox"), true, 0, 0, 0, 0);
		addOption(jukeBoxMusic[currentJukeboxTitle].getName(), false, skin.get("menu_suboption", LabelStyle.class), 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS / 2, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.back"), true, 0, 0, MegamanConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (volumeChange != 0) {
			volumeUpdateTimeCurrent += deltaTime;
			if (volumeUpdateTimeCurrent >= volumeUpdateTime) {
				if (music) {
					SoundManager.INSTANCE.setMusicVolume(SoundManager.INSTANCE.getMusicVolume() + volumeChange);
					options.get(OPTION_INFO).setText("" + SoundManager.INSTANCE.getMusicVolume());
				} else {
					SoundManager.INSTANCE.setSoundVolume(SoundManager.INSTANCE.getSoundVolume() + volumeChange);
					options.get(OPTION_SOUND_INFO).setText("" + SoundManager.INSTANCE.getSoundVolume());
				}
				volumeUpdateTimeCurrent = 0;
			}
		}
	}

	@Override
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		switch (optionIndex) {
			case OPTION_MUSIC: {
				if (Keys.LEFT == keyOrButtonCode) {
					music = true;
					volumeUpdateTimeCurrent = 0;
					volumeChange = -5;
					SoundManager.INSTANCE.setMusicVolume(SoundManager.INSTANCE.getMusicVolume() - 5);
				} else if (Keys.RIGHT == keyOrButtonCode) {
					music = true;
					volumeUpdateTimeCurrent = 0;
					volumeChange = 5;
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
					music = false;
					volumeUpdateTimeCurrent = 0;
					volumeChange = -5;
					SoundManager.INSTANCE.setSoundVolume(SoundManager.INSTANCE.getSoundVolume() - 5);
					SoundManager.INSTANCE.playSound(SoundType.MENU_SELECT_SHOOT);
				} else if (Keys.RIGHT == keyOrButtonCode) {
					music = false;
					volumeUpdateTimeCurrent = 0;
					volumeChange = 5;
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
	public boolean keyUp(int optionIndex, int keyOrButtonCode) {
		switch (optionIndex) {
			case OPTION_MUSIC: {
				if (Keys.LEFT == keyOrButtonCode || Keys.RIGHT == keyOrButtonCode) {
					volumeChange = 0;
				}
				break;
			}
			case OPTION_SOUND: {
				if (Keys.LEFT == keyOrButtonCode || Keys.RIGHT == keyOrButtonCode) {
					volumeChange = 0;
				}
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
