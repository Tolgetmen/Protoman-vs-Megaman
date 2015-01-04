package com.megaman.core;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.megaman.constants.GameConstants;
import com.megaman.core.enums.GameMenuPageType;

/**
 * 
 * GameMenu is the one of core classes for Label based keyboard menus. It consists of at least one menu page and each menu page
 * contains the menu options for that page. 
 * It uses the libgdx Stage class to have a scene graph in the background that contains actors(=the Labels). The stage
 * of the GameMenu class is always a StretchViewport to stretch the menu to the given size.
 * 
 * A GameMenuPage is configured (=which class defines the page, which skin is used and the page's background image) within the GameMenuPageType enum.
 * A Skin is configured within the SkinType enum.
 * 
 */
public abstract class GameMenu {
	/**
	 * libgdx Stage that is used to store and render the different actors(=labels)
	 */
	private Stage					stage;
	/**
	 * stack of GameMenuPages. Always contains at least one menu page (=startPage parameter of constructor).
	 * The page that is currently on top will be rendered. Other pages are hidden.
	 */
	private Stack<GameMenuPage>		menuPages;
	/**
	 * current selected option of the active menu page
	 */
	private int						currentOptionIndex;
	/**
	 * reference to the GameStateLogic that is creating the menu. The reference can be used
	 * to call functionality from the logic out of the menu.
	 */
	protected final GameStateLogic	logic;

	public GameMenu(GameStateLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage) {
		stage = new Stage(new StretchViewport(menuWidth, menuHeight));
		this.logic = logic;

		changeMenuPage(startPage);
	}

	/**
	 * This method is used to change the current active GameMenuPage of the GameMenu. If the <b>newPage</b> parameter
	 * is <b>null</b> then the menu will change to the previous page. Otherwise it will create a new page instance
	 * of type <b>newPage</b> and will put it on top of the menuPages stack.
	 * The page that is on top of the menuPages stack is the active menu page.
	 * 
	 * A GameMenuPage is configured (=which class defines the page, which skin is used and the page's background image) within the GameMenuPageType enum.
	 * 
	 * @param newPage new page that should get active. <b>null</b> to remove the current page and go back to the previous page
	 */
	public void changeMenuPage(GameMenuPageType newPage) {
		if (menuPages == null) {
			// initialize the menu pages stack. The page at the top is the current active page
			// that will be displayed
			menuPages = new Stack<GameMenuPage>();
		}

		if (newPage != null) {
			// create a new page instance and set it as new active page
			Class<? extends GameMenuPage> pageClass = newPage.getPageClass();
			if (pageClass != null) {
				try {
					if (menuPages.size() > 0) {
						// if there is already a page available then remove
						// it from the stage to not display it anymore
						menuPages.peek().table.remove();
					}

					// create the new page instance and put it on top the menu pages stack to set it active
					menuPages.add(newPage.getPageClass().getDeclaredConstructor(GameMenuPageType.class, GameMenu.class, GDXGame.class, GameStateLogic.class).newInstance(newPage, this, logic.game, logic));
				} catch (IllegalArgumentException e) {
					Gdx.app.error(GameConstants.LOG_TAG_ERROR, "Illegal arguments for changeMenuPage", e);
				} catch (NoSuchMethodException e) {
					Gdx.app.error(GameConstants.LOG_TAG_ERROR, "No such method for changeMenuPage", e);
				} catch (Exception e) {
					Gdx.app.error(GameConstants.LOG_TAG_ERROR, "General exception for changeMenuPage", e);
				}
			} else {
				Gdx.app.error(GameConstants.LOG_TAG_ERROR, "GameMenuPageType enum is not properly configured. A GameMenuPage class is not defined for type: " + newPage);
			}
		} else {
			// switch to previous page
			menuPages.pop().table.remove();
		}

		// add the active page to the stage in order to render it
		stage.addActor(menuPages.peek().table);
		// set the current selected option index to the initial option index of the menu page
		currentOptionIndex = menuPages.peek().getInitialOptionIndex();
	}

	/**
	 * This method should be called to move to the next enabled option of the active menu page
	 */
	public void increaseSelection() {
		do {
			++currentOptionIndex;
			currentOptionIndex %= menuPages.peek().options.size;
		} while (!menuPages.peek().optionEnabled.get(currentOptionIndex));
	}

	/**
	 * This method should be called to move to the previous enabled option of the active menu page
	 */
	public void decreaseSelection() {
		do {
			--currentOptionIndex;
			if (currentOptionIndex < 0) {
				currentOptionIndex = menuPages.peek().options.size - 1;
			}
		} while (!menuPages.peek().optionEnabled.get(currentOptionIndex));
	}

	/**
	 * This method returns the current selected option (=Label) of the active menu page
	 * 
	 * @return current selected Label of active menu page
	 */
	public Label getCurrentOption() {
		return menuPages.peek().options.get(currentOptionIndex);
	}

	/**
	 * This method should be called to process the logic of the current selected option.
	 * It will forward the call to the active menu page.
	 */
	public void processSelection() {
		menuPages.peek().processSelection(currentOptionIndex);
	}

	/**
	 * This method should be called to process keyboard and controller button events of
	 * the current selected option
	 * 
	 * @param keyOrButtonCode code of pressed key or button
	 * 
	 * @return <b>true</b> when event was successfully handled. <b>false</b> otherwise.
	 */
	public boolean keyDown(int keyOrButtonCode) {
		return menuPages.peek().keyDown(currentOptionIndex, keyOrButtonCode);
	}

	/**
	 * This method should be called to render the GameMenu
	 */
	public void render() {
		// update all the actors of the stage by delta time
		stage.act();
		// render the stage (=render options of the menu page + background of the menu page's table)
		stage.draw();
	}

	/**
	 * This method should be called to dispose any resources of the GameMenu.
	 * It disposes the stage instance of the GameMenu.
	 */
	public void dispose() {
		stage.dispose();
	}
}
