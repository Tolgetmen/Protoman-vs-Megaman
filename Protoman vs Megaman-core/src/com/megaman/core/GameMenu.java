package com.megaman.core;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.megaman.core.utils.ResourceManager;
import com.megaman.enums.GameMenuPageType;

public abstract class GameMenu {
	private Stage			stage;
	private Stack<GameMenuPage>	menuPages;
	protected GameLogic		logic;

	private Array<Label>	currentOptions;
	private int				currentOptionIndex;

	private boolean			stretch;

	public GameMenu(GameLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage) {
		menuPages = new Stack<GameMenuPage>();
		stage = new Stage(new StretchViewport(menuWidth, menuHeight));
		this.logic = logic;
		this.stretch = stretch;

		changeMenuPage(startPage);

		currentOptions = menuPages.peek().getOptions();
		currentOptionIndex = menuPages.peek().getInitialOptionIndex();
	}

	public void processSelection() {
		menuPages.peek().processSelection(currentOptionIndex);
	}

	public boolean keyDown(int keyCode) {
		return menuPages.peek().keyDown(currentOptionIndex, keyCode);
	}

	public Label getCurrentOption() {
		return currentOptions.get(currentOptionIndex);
	}

	public void increaseSelection() {
		do {
			++currentOptionIndex;
			currentOptionIndex %= currentOptions.size;
		} while (!menuPages.peek().isOptionEnabled(currentOptionIndex));
	}

	public void decreaseSelection() {
		do {
			--currentOptionIndex;
			if (currentOptionIndex < 0)
				currentOptionIndex = currentOptions.size - 1;
		} while (!menuPages.peek().isOptionEnabled(currentOptionIndex));
	}

	private void setActiveMenuPage(GameMenuPage page) {
		stage.addActor(page.getTable());
		currentOptions = page.getOptions();
		currentOptionIndex = page.getInitialOptionIndex();
	}

	public void changeMenuPage(GameMenuPageType pageType) {
		if (pageType != null) {
			try {
				if (menuPages.size() > 0) {
					menuPages.peek().getTable().remove();
				}
				Skin skin = ResourceManager.INSTANCE.getSkin(pageType.getSkinType());
				menuPages.add(pageType.getPageClass().getDeclaredConstructor(GameMenu.class, GameLogic.class, Skin.class, boolean.class, Drawable.class).newInstance(this, logic, skin, stretch, skin.getDrawable(pageType.getSkinBackgroundImage())));
				setActiveMenuPage(menuPages.peek());
			} catch (IllegalArgumentException e) {
				Gdx.app.error("changeMenuPage", "Illegal arguments", e);
			} catch (NoSuchMethodException e) {
				Gdx.app.error("changeMenuPage", "No such method", e);
			} catch (Exception e) {
				Gdx.app.error("changeMenuPage", "General exception", e);
			}
		} else {
			// switch to previous page
			menuPages.pop().getTable().remove();
			if (menuPages.size() > 0) {
				setActiveMenuPage(menuPages.peek());
			}
		}
	}

	public void render() {
		stage.act();
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}
