package com.megaman.menu.pages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.constants.MegamanConstants;
import com.megaman.menu.MegamanMenu;

public class MainMenuPage extends GameMenuPage implements OnCompletionListener {
	private final int	OPTION_RESUME	= 0;
	private final int	OPTION_NEW		= 1;
	private final int	OPTION_SETTINGS	= 2;
	private final int	OPTION_EXIT		= 3;

	public MainMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		boolean isGameRunning = game.isGameStateAvailable(GameStateType.GAME);

		addOption(GameUtils.getLocalizedLabel("MainMenu.option.resume"), isGameRunning, isGameRunning ? skin.get("default", LabelStyle.class) : skin.get("menu_option_disabled", LabelStyle.class), MegamanConstants.MENU_OFFSET_TOP, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.new"), true, 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.settings"), true, 0, 0, MegamanConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.quit"), true, 0, 0, MegamanConstants.MENU_OFFSET_BOTTOM, 0);
	}

	@Override
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		if (Keys.ESCAPE == keyOrButtonCode && game.isGameStateAvailable(GameStateType.GAME)) {
			game.setGameState(GameStateType.GAME, true, false, null);
		}

		return false;
	}

	@Override
	public void processSelection(int optionIndex) {
		switch (optionIndex) {
			case OPTION_RESUME: {
				game.setGameState(GameStateType.GAME, true, false, null);
				break;
			}
			case OPTION_NEW: {
				game.setGameState(GameStateType.GAME, true, true, null);
				break;
			}
			case OPTION_SETTINGS: {
				gameMenu.changeMenuPage(GameMenuPageType.MAIN_MENU_SETTINGS);
				break;
			}
			case OPTION_EXIT: {
				// wait until protoman music was played to close the game
				// check onCompletion(Music music) method
				SoundManager.INSTANCE.playMusic(MusicType.MENU_QUIT, false, this);
				((MegamanMenu) gameMenu).disableControls();
				break;
			}
		}
	}

	@Override
	public void onCompletion(Music music) {
		// wait until protoman music was played to close the game
		game.setGameState(null, true, true, null);
	}
}
