package com.megaman.gamestates.logic;

import com.badlogic.gdx.graphics.Camera;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.menu.HighscoreMenu;

public class GSHighscoreLogic extends MenuLogic {
	public GSHighscoreLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	@Override
	public void initialize() {
		SoundManager.INSTANCE.playMusic(MusicType.WILY_STAGE, true);
		menu = new HighscoreMenu(GameUtils.getLocalizedLabel("HighScore.title"), this, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT, true, GameMenuPageType.HIGHSCORE, data);
	}
}
