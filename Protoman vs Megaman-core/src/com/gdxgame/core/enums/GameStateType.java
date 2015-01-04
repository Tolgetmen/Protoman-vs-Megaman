package com.gdxgame.core.enums;

import com.gdxgame.core.GameState;
import com.gdxgame.core.GameStateLogic;
import com.megaman.gamestates.GSGame;
import com.megaman.gamestates.GSGameOver;
import com.megaman.gamestates.GSHighscore;
import com.megaman.gamestates.GSMainMenu;
import com.megaman.gamestates.logic.GSGameLogic;
import com.megaman.gamestates.logic.GSGameOverLogic;
import com.megaman.gamestates.logic.GSHighscoreLogic;
import com.megaman.gamestates.logic.GSMainMenuLogic;

/**
 * GameStateType enum is the configuration enum for game states.
 * Each value contains the GameState class and GameStateLogic class that are used for a specific game state.
 * In addition one of the game states can be set as initial state that will then be loaded at the beginning
 * of the GDXGame class.
 */
public enum GameStateType {
	MAIN_MENU(GSMainMenu.class, GSMainMenuLogic.class, true),
	GAME(GSGame.class, GSGameLogic.class, false),
	GAME_OVER(GSGameOver.class, GSGameOverLogic.class, false),
	HIGHSCORE(GSHighscore.class, GSHighscoreLogic.class, false);

	/**
	 * game state class containing the game state's logic for loading/disposing resources
	 */
	private final Class<? extends GameState>		gameStateClass;
	/**
	 * game state logic class containing the game state's logic
	 */
	private final Class<? extends GameStateLogic>	gameLogicClass;
	/**
	 * flag to define if the state is the initial state or not
	 */
	private final boolean							isInitialState;

	private GameStateType(Class<? extends GameState> gameStateClass, Class<? extends GameStateLogic> gameInputClass, boolean isInitialState) {
		this.gameStateClass = gameStateClass;
		this.gameLogicClass = gameInputClass;
		this.isInitialState = isInitialState;
	}

	/**
	 * returns the class representing the game state's logic for loading/disposing resources
	 * 
	 * @return type of GameState class to be used for the gamestate
	 */
	public Class<? extends GameState> getGameStateClass() {
		return gameStateClass;
	}

	/**
	 * returns the class representing the game state's logic
	 * 
	 * @return type of GameStateLogic class to be used for the gamestate
	 */
	public Class<? extends GameStateLogic> getGameLogicClass() {
		return gameLogicClass;
	}

	/**
	 * returns the initialstate flag of the game state
	 * 
	 * @return <b>true</b> if it is the initial state.<b>false</b> otherwise.
	 */
	public boolean isInitialState() {
		return isInitialState;
	}

	/**
	 * returns the first game state that has the initial flag set to true
	 * 
	 * @return <b>null</b> if there is no initial game state configured. Otherwise returns game state that has the initial flag set to true.
	 */
	public static GameStateType getInitialState() {
		for (GameStateType state : GameStateType.values()) {
			if (state.isInitialState()) {
				return state;
			}
		}
		return null;
	}
}
