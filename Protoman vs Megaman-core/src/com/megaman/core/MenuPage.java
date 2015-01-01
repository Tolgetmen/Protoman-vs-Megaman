package com.megaman.core;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public abstract class MenuPage {
	protected GameMenu			gameMenu;
	protected Table				table;
	protected Array<Label>		options;
	protected Array<Boolean>	optionEnabled;
	protected Skin				skin;
	protected GameLogic			logic;

	public MenuPage(GameMenu gameMenu, GameLogic logic, Skin skin, boolean fill, Drawable background) {
		table = new Table();
		options = new Array<Label>();
		optionEnabled = new Array<Boolean>();
		this.logic = logic;
		this.gameMenu = gameMenu;
		this.skin = skin;

		if (background != null)
			table.setBackground(background);
		table.setFillParent(fill);

		initialize();
	}

	public Table getTable() {
		return table;
	}

	public void addOption(String label, LabelStyle style, int padTop, int padRight, int padBottom, int padLeft, boolean enabled) {
		Label lbl = new Label(label, style);
		options.add(lbl);
		optionEnabled.add(enabled);
		table.add(lbl).pad(padTop, padLeft, padBottom, padRight).row();
	}

	public void addOption(String label, LabelStyle style, int padTop, int padRight, int padBottom, int padLeft) {
		addOption(label, style, padTop, padRight, padBottom, padLeft, true);
	}

	public void enableOption(int optionIndex, boolean enabled, LabelStyle style) {
		optionEnabled.set(optionIndex, enabled);
		options.get(optionIndex).setStyle(style);
	}

	public abstract void initialize();

	public int getInitialOptionIndex() {
		int index = 0;
		while (!optionEnabled.get(index)) {
			++index;
		}
		return index;
	}

	public abstract void processSelection(int optionIndex);

	public boolean keyDown(int optionIndex, int keyCode) {
		return false;
	}

	public Array<Label> getOptions() {
		return options;
	}

	public boolean isOptionEnabled(int optionIndex) {
		return optionEnabled.get(optionIndex);
	}
}
