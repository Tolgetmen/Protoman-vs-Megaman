package com.megaman.menu.pages;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.menu.MegamanMenu;

public class GameOverPage extends GameMenuPage implements OnCompletionListener {
	private final int	OPTION_RETRY	= 0;
	private final int	OPTION_QUIT		= 1;

	public GameOverPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
	}

	@Override
	public void initialize() {
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.retry"), true, 490, 0, 20, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.quit"), true, 0, 0, 0, 0);
	}

	@Override
	public void processSelection(int optionIndex) {
		switch (optionIndex) {
			case OPTION_RETRY: {
				game.setGameState(GameStateType.GAME, true, true, null);
				break;
			}
			case OPTION_QUIT: {
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
