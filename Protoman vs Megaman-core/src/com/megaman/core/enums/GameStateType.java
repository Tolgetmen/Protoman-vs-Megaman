package com.megaman.core.enums;

import com.megaman.core.GameStateLogic;
import com.megaman.core.GameState;
import com.megaman.gamestates.GSGame;
import com.megaman.gamestates.GSGameOver;
import com.megaman.gamestates.GSHighscore;
import com.megaman.gamestates.GSMainMenu;
import com.megaman.gamestates.logic.GSGameLogic;
import com.megaman.gamestates.logic.GSGameOverLogic;
import com.megaman.gamestates.logic.GSHighscoreLogic;
import com.megaman.gamestates.logic.GSMainMenuLogic;

public enum GameStateType {
	MAIN_MENU(GSMainMenu.class, GSMainMenuLogic.class, true),
	GAME(GSGame.class, GSGameLogic.class, false),
	GAME_OVER(GSGameOver.class, GSGameOverLogic.class, false),
	HIGHSCORE(GSHighscore.class, GSHighscoreLogic.class, false);

	private final Class<? extends GameState>	gameStateClass;
	private final Class<? extends GameStateLogic>	gameLogicClass;
	private final boolean						isInitialState;

	private GameStateType(Class<? extends GameState> gameStateClass, Class<? extends GameStateLogic> gameInputClass, boolean isInitialState) {
		this.gameStateClass = gameStateClass;
		this.gameLogicClass = gameInputClass;
		this.isInitialState = isInitialState;
	}

	public Class<? extends GameState> getGameStateClass() {
		return gameStateClass;
	}

	public Class<? extends GameStateLogic> getGameLogicClass() {
		return gameLogicClass;
	}

	public boolean isInitialState() {
		return isInitialState;
	}

	public static GameStateType getInitialState() {
		// returns the the first state that has the intialstate flag == true
		for (GameStateType state : GameStateType.values()) {
			if (state.isInitialState())
				return state;
		}
		return null;
	}
}
