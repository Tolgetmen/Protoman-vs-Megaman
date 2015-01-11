package com.gdxgame.core;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdxgame.core.constants.GameConstants;
import com.gdxgame.core.enums.GameMenuPageType;

/**
 * 
 * GameMenu is one of the core classes for {@link Label} based keyboard menus. It consists of at least one {@link GameMenuPage} and each menu page
 * contains the menu options for that page.<br>
 * It uses the libgdx {@link Stage} class to have a scene graph in the background that contains actors(=the Labels). The stage
 * of the GameMenu class is always a {@link StretchViewport} to stretch the menu to the given size.
 * <br><br>
 * A {@link GameMenuPage} is configured (=which class defines the page, which skin is used and the page's background image) within 
 * the {@link GameMenuPageType} enum.
 * <br><br>
 * A {@link com.badlogic.gdx.scenes.scene2d.ui.Skin} is configured within the {@link com.gdxgame.core.enums.SkinType} enum.
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
	 * updates the menu by delta time(=time passed since last frame) by internally calling the
	 * active menu page's update method. Also updates the actors of the stage.<br>
	 * Can be used to f.e. add animations or periodically change a menu value.
	 * 
	 * @param deltaTime time passed since last frame
	 */
	public void update(float deltaTime) {
		stage.act(deltaTime);

		menuPages.peek().update(deltaTime);
	}

	/**
	 * render the menu by drawing the stage of the menu and calling the active menu page's
	 * render method.
	 * 
	 * @param spriteBatch reference to the {@link GDXGame} SpriteBatch to draw things
	 */
	public void render(SpriteBatch spriteBatch) {
		stage.draw();

		menuPages.peek().render(spriteBatch);
	}

	/**
	 * changes the active menu page to the give menu page type. If the {@code newPage} parameter
	 * is <b>null</b> then the menu will change to the previous page. Otherwise it will create a new page instance
	 * of type <b>newPage</b> and will put it on top of the menu pages stack.
	 * The page that is on top of the menu pages stack is the active menu page.
	 * <br><br>
	 * A {@link GameMenuPage} is configured (=which class defines the page, which skin is used and the page's background image) within the GameMenuPageType enum.
	 * 
	 * @param newPage page type to get active. <b>null</b> to remove the current page and go back to the previous page
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
	 * increases the current selected option of the active menu page
	 */
	public void increaseSelection() {
		do {
			++currentOptionIndex;
			currentOptionIndex %= menuPages.peek().options.size;
		} while (!menuPages.peek().optionEnabled.get(currentOptionIndex));
	}

	/**
	 * decreases the current selected option of the active menu page
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
	 * returns the current selected option (=Label) of the active menu page
	 * 
	 * @return current selected Label of active menu page
	 */
	public Label getCurrentOption() {
		return menuPages.peek().options.get(currentOptionIndex);
	}

	/**
	 * returns the current active game menu page
	 * 
	 * @return active menu page
	 */
	public GameMenuPage getCurrentPage() {
		return menuPages.peek();
	}

	/**
	 * processes the current selected option by forwarding the call to the active menu page's
	 * {@link GameMenuPage#processSelection(int)} method
	 */
	public void processSelection() {
		menuPages.peek().processSelection(currentOptionIndex);
	}

	/**
	 * handles keyboard's keyDown events by forwarding the call to the active menu page.
	 * The mapping between controller buttons and keyboard buttons should happen already before this call
	 * in the {@link GameStateLogic} logic.
	 * 
	 * @param keyCode code of pressed key
	 * 
	 * @return <b>true</b> when event was successfully handled. <b>false</b> otherwise.
	 */
	public boolean keyDown(int keyCode) {
		return menuPages.peek().keyDown(currentOptionIndex, keyCode);
	}

	/**
	 * handles keyboard's keyUp events by forwarding the call to the active menu page.
	 * The mapping between controller buttons and keyboard buttons should happen already before this call
	 * in the {@link GameStateLogic} logic.
	 * 
	 * @param keyCode code of released key
	 * 
	 * @return <b>true</b> when event was successfully handled. <b>false</b> otherwise.
	 */
	public boolean keyUp(int keyCode) {
		return menuPages.peek().keyUp(currentOptionIndex, keyCode);
	}

	/**
	 * disposes any resources of the game menu (f.e. the stage).
	 */
	public void dispose() {
		stage.dispose();
	}
}
