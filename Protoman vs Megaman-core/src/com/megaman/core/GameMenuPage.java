package com.megaman.core;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.utils.ResourceManager;

/**
 * GameMenuPage is one of the core classes for Label based keyboard menus. It contains different options that can be selected by the user. 
 * It uses the libgdx Table class to have an actor for the stage attribute of the GameMenu and to automatically layout the options(=Labels) in
 * a specified manner.
 * It also uses the libgdx Skin class in order to define properties how the options should look like (font,color,background,...). A skin consists
 * of a JSON file that describes the properties how options should look like and of a texture atlas containing graphics of the skin.
 * 
 * A GameMenuPage is configured (=which class defines the page, which skin is used and the page's background image) within the GameMenuPageType enum.
 * 
 * A Skin is configured within the SkinType enum.				
 *
 */
public abstract class GameMenuPage {
	/**
	 * reference to the GameMenu of this page in order to call the changeMenuPage() method of the GameMenu.
	 */
	protected final GameMenu		gameMenu;
	/**
	 * the actor for the stage of the GameMenu. It is also a group for the different options of the page.
	 */
	protected Table					table;
	/**
	 * options of the menu page
	 */
	protected Array<Label>			options;
	/**
	 * stores the information if an option can be selected or not. If an option is disabled then the increase-/decreaseSelection()
	 * method of the GameMenu will ignore the option. Also the getInitialOptionIndex() method ignores disabled options.
	 */
	protected Array<Boolean>		optionEnabled;
	/**
	 * libgdx Skin reference in order get the properties how an option should look like
	 */
	protected final Skin			skin;
	/**
	 * reference to the GameStateLogic to be able to call logic specific methods
	 */
	protected final GameStateLogic	logic;
	/**
	 * reference to the GDXGame instance in order to be able to call f.e. the setGameState() method	
	 */
	protected final GDXGame			game;

	public GameMenuPage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		this.table = new Table();
		this.logic = logic;
		this.game = game;
		this.gameMenu = gameMenu;
		this.skin = ResourceManager.INSTANCE.getSkin(type.getSkinType());
		if (type.getSkinBackgroundImage() != null) {
			table.setBackground(skin.getDrawable(type.getSkinBackgroundImage()));
		}

		// stretch the table to the StretchViewPort of the GameMenu
		table.setFillParent(true);

		// initialize the menu page (=add the options to the menu page)
		initialize();
	}

	/**
	 * This method is used to add an option to the menu page. Each menu page must have at least one option.
	 * An option can either be enabled or disabled.
	 * 
	 * If it is disabled then it will be ignored for the increase-/decreaseSelection() calls and for the
	 * getInitialOptioinIndex() method.
	 * 
	 * @param label 		text of the option
	 * @param enabled		<b>true</b> to enable the option. <b>false</b> to disable the option and to
	 * 						exclude it from the increase-/decreaseSelection() and getInitialOptioinIndex() calls 
	 * @param style 		LabelStyle of the option taken from the skin
	 * @param padTop		top padding of the option
	 * @param padRight		right padding of the option
	 * @param padBottom		bottom padding of the option
	 * @param padLeft		left padding of the option
	 */
	public void addOption(String label, boolean enabled, LabelStyle style, int padTop, int padRight, int padBottom, int padLeft) {
		if (options == null) {
			// no options defined yet
			// --> initialize options and optionEnabled arrays
			options = new Array<Label>();
			optionEnabled = new Array<Boolean>();
		}

		// create a new label with the specified text and style
		Label lbl = null;
		if (style == null) {
			lbl = new Label(label, skin.get("default", LabelStyle.class));
		} else {
			lbl = new Label(label, style);
		}
		// add the new label to the options and optionEnabled arrays
		options.add(lbl);
		optionEnabled.add(enabled);
		// add the new option to the table instance with the specified padding
		// and also make a call to the row() method to finish one row of the table.
		// This means that only one label is displayed per row
		table.add(lbl).pad(padTop, padLeft, padBottom, padRight).row();
	}

	/**
	 * Similar like {@link #addOption(String, boolean, com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle, int, int, int, int)}. 
	 * The difference is that it automatically uses the "default" LabelStyle of the skin.
	 * 
	 * @param label 		text of the option
	 * @param enabled		<b>true</b> to enable the option. <b>false</b> to disable the option and to
	 * 						exclude it from the increase-/decreaseSelection() and getInitialOptioinIndex() calls
	 * @param padTop		top padding of the option
	 * @param padRight		right padding of the option
	 * @param padBottom		bottom padding of the option
	 * @param padLeft		left padding of the option
	 */
	public void addOption(String label, boolean enabled, int padTop, int padRight, int padBottom, int padLeft) {
		addOption(label, enabled, skin.get("default", LabelStyle.class), padTop, padRight, padBottom, padLeft);
	}

	/**
	 * Enables or disables an option of the menu page.  
	 * 
	 * If an option is disabled then it will be ignored for the increase-/decreaseSelection() calls and for the
	 * getInitialOptioinIndex() method.
	 * 
	 * @param optionIndex index of option that should be enabled/disabled. Must be greater or equal 0 and <code>options.size()</code>
	 * @param enabled <b>true</b> to enable an option. <b>false</b> to disable an option.
	 * @param style new LabelStyle that should be used for the option
	 */
	public void enableOption(int optionIndex, boolean enabled, LabelStyle style) {
		optionEnabled.set(optionIndex, enabled);
		if (style != null) {
			options.get(optionIndex).setStyle(style);
		}
	}

	/**
	 * This method is automatically called from the constructor of the GameMenuPage class. 
	 * Use it to make calls to the <code>addOption</code>) method to add options to the page.
	 * A page must contain at least one option.
	 */
	public abstract void initialize();

	/**
	 * Returns the option index of the menu page that should be automatically selected when
	 * the page gets active.
	 * Per default this method returns the first enabled option of the <code>options</code> array.
	 * 
	 * @return option index of option that should be selected when the page gets active
	 */
	public int getInitialOptionIndex() {
		int index = 0;
		while (!optionEnabled.get(index)) {
			++index;
		}
		return index;
	}

	/**
	 * This method is called when the GameMenu <code>processSelection</code> method is called.
	 * Use it to handle the logic of the given option index.
	 * 
	 * @param optionIndex index of option that should be processed
	 */
	public abstract void processSelection(int optionIndex);

	/**
	 * This method is called when the GameMenu <code>keyDown</code> method is called.
	 * Use it to handle the logic of keyboard or controller events.
	 * 
	 * @param optionIndex index of option that should be processed
	 * @param keyOrButtonCode code of pressed key or button
	 * 
	 * @return <b>true</b> if event was successfully handled. <b>false</b> otherwise.
	 */
	public boolean keyDown(int optionIndex, int keyOrButtonCode) {
		return false;
	}
}
