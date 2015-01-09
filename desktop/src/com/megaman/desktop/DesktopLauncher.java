package com.megaman.desktop;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.GDXGame;

public class DesktopLauncher {
	private static void readConfigFromFile(LwjglApplicationConfiguration config, String iniFilePath, String configSection) throws InvalidFileFormatException, IOException {
		Ini iniFile = new Ini(new File(GameConstants.CFG_FILE_PATH));
		Section section = iniFile.get(GameConstants.CFG_FILE_CFG_SECTION);

		if (section != null) {
			// and store its values in the gameConfigValues
			for (Entry<String, String> entry : section.entrySet()) {
				if (GameConstants.CFG_KEY_WIDTH.equals(entry.getKey())) {
					config.width = Integer.parseInt(entry.getValue());
				} else if (GameConstants.CFG_KEY_HEIGHT.equals(entry.getKey())) {
					config.height = Integer.parseInt(entry.getValue());
				} else if (GameConstants.CFG_KEY_FULLSCREEN.equals(entry.getKey())) {
					config.fullscreen = Boolean.parseBoolean(entry.getValue());
				}
			}
		}
	}

	public static void main(String[] arg) throws InvalidFileFormatException, IOException {
		System.out.println("1");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GameConstants.WINDOW_TITLE;
		config.addIcon("gameicon.png", FileType.Internal);
		config.width = GameConstants.GAME_WIDTH;
		config.height = GameConstants.GAME_HEIGHT;
		config.fullscreen = false;
		readConfigFromFile(config, "../core/assets/game.cfg", "Gameconfig");
		config.vSyncEnabled = config.fullscreen;
		System.out.println("2");

		Settings settings = new Settings();
		settings.combineSubdirectories = true;
		TexturePacker.process(settings, "../core/assets/graphics/hud", "../core/assets/packedGraphics", "hud");
		TexturePacker.process(settings, "../core/assets/graphics/game", "../core/assets/packedGraphics", "gameGraphics");
		TexturePacker.process(settings, "../core/assets/graphics/menu", "../core/assets/packedGraphics", "menuGraphics");

		System.out.println("3");
		new LwjglApplication(new GDXGame(), config);
		System.out.println("4");
	}
}
