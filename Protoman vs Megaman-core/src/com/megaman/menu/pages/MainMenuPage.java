package com.megaman.menu.pages;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;
import com.megaman.core.GameMenu;
import com.megaman.core.GameMenuPage;
import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.enums.GameStateType;
import com.megaman.core.enums.MusicType;
import com.megaman.core.utils.SoundManager;
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

		addOption("resume game", isGameRunning, isGameRunning ? skin.get("default", LabelStyle.class) : skin.get("title_disabled", LabelStyle.class), GameConstants.MENU_OFFSET_TOP, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("new game", true, 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("settings", true, 0, 0, GameConstants.MENU_PADDING_BETWEEN_OPTIONS, 0);
		addOption("exit game", true, 0, 0, GameConstants.MENU_OFFSET_BOTTOM, 0);
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
				((MegamanMenu) gameMenu).quitGame();
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
