package com.megaman.gamestates.logic;

import com.badlogic.gdx.graphics.Camera;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.menu.MegamanMenu;

public class GSGameOverLogic extends MenuLogic {
	public GSGameOverLogic(GDXGame game, Camera camera) {
		super(game, camera);
	}

	@Override
	public void initialize() {
		SoundManager.INSTANCE.playMusic(MusicType.PROTOMAN, true);
		menu = new MegamanMenu(this, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT, true, GameMenuPageType.GAME_OVER);
	}
}
