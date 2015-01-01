package com.megaman.enums;

import com.megaman.core.GameLogic;
import com.megaman.core.GameState;
import com.megaman.gamestates.GSGame;
import com.megaman.gamestates.GSMainMenu;
import com.megaman.gamestates.logic.GSGameLogic;
import com.megaman.gamestates.logic.GSMainMenuLogic;

public enum GameStateType {
	MAIN_MENU(GSMainMenu.class, GSMainMenuLogic.class, true),
	GAME(GSGame.class, GSGameLogic.class, false),
	PAUSE(null, null, false),
	GAME_OVER(null, null, false),
	HIGHSCORE(null, null, false);

	private final Class<? extends GameState>	gameStateClass;
	private final Class<? extends GameLogic>	gameLogicClass;
	private final boolean						isInitialState;

	private GameStateType(Class<? extends GameState> gameStateClass, Class<? extends GameLogic> gameInputClass, boolean isInitialState) {
		this.gameStateClass = gameStateClass;
		this.gameLogicClass = gameInputClass;
		this.isInitialState = isInitialState;
	}

	public Class<? extends GameState> getGameStateClass() {
		return gameStateClass;
	}

	public Class<? extends GameLogic> getGameLogicClass() {
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
