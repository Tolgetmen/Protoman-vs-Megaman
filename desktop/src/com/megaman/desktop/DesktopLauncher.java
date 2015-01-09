package com.megaman.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;

public class DesktopLauncher {
	private static void readConfigFromPreference(LwjglApplicationConfiguration config) throws FileNotFoundException, IOException {
		String OS = System.getProperty("os.name").toLowerCase();
		String preferencePath = "";

		if (OS.indexOf("win") >= 0) {
			// windows -> read preference from %UserProfile%/.prefs/prefName path
			preferencePath = System.getProperty("user.home") + "/.prefs/" + GameConstants.PREFERENCE_CFG_NAME;
		} else {
			// linux or OSX -> read preference from ~/.prefs/prefName path
			preferencePath = "~/.prefs/" + GameConstants.PREFERENCE_CFG_NAME;
		}

		File file = new File(preferencePath);
		if (file.exists()) {
			// preference exists -> read values
			XmlReader xmlReader = new XmlReader();
			Element parse = xmlReader.parse(new FileInputStream(file));
			for (int i = 0; i < parse.getChildCount(); ++i) {
				Element child = parse.getChild(i);
				ObjectMap<String, String> attributes = child.getAttributes();
				if (attributes.containsValue(GameConstants.PREFERENCE_KEY_WIDTH, false)) {
					config.width = Integer.parseInt(child.getText());
					continue;
				} else if (attributes.containsValue(GameConstants.PREFERENCE_KEY_HEIGHT, false)) {
					config.height = Integer.parseInt(child.getText());
					continue;
				} else if (attributes.containsValue(GameConstants.PREFERENCE_KEY_FULLSCREEN, false)) {
					config.fullscreen = Boolean.parseBoolean(child.getText());
					continue;
				}
			}
		}
	}

	public static void main(String[] arg) throws FileNotFoundException, IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GameConstants.WINDOW_TITLE;
		config.addIcon("gameicon.png", FileType.Internal);
		config.width = GameConstants.GAME_WIDTH;
		config.height = GameConstants.GAME_HEIGHT;
		config.fullscreen = false;
		readConfigFromPreference(config);
		config.vSyncEnabled = config.fullscreen;

		// check debug/run configuration ENVIRONMENT tab
		// if the "DEVEOPMENT" flag is true, then the graphics will be packed together
		// set the flag to false before exporting the jar
		String getenv = System.getenv("DEVELOPMENT");
		if (getenv != null && "true".equals(getenv)) {
			Settings settings = new Settings();
			settings.combineSubdirectories = true;
			TexturePacker.process(settings, "../core/assets/graphics/hud", "../core/assets/packedGraphics", "hud");
			TexturePacker.process(settings, "../core/assets/graphics/game", "../core/assets/packedGraphics", "gameGraphics");
			TexturePacker.process(settings, "../core/assets/graphics/menu", "../core/assets/packedGraphics", "menuGraphics");
		}

		new LwjglApplication(new GDXGame(), config);
	}
}
