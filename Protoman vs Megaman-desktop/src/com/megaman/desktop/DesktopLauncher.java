package com.megaman.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.megaman.constants.GameConstants;
import com.megaman.core.GDXGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//TODO read config out of config file
		config.title = GameConstants.WINDOW_TITLE;
		config.addIcon("gameicon.png", FileType.Internal);
		config.width = GameConstants.GAME_WIDTH;
		config.height = GameConstants.GAME_HEIGHT;
		config.fullscreen = false;
		config.vSyncEnabled = config.fullscreen;

		//TODO TexturePacker to create TextureAtlas
		Settings settings = new Settings();
		TexturePacker.process(settings, "../Protoman vs Megaman-core/assets/graphics/game", "../Protoman vs Megaman-core/assets/packedGraphics", "gameGraphics");
		TexturePacker.process(settings, "../Protoman vs Megaman-core/assets/graphics/menu", "../Protoman vs Megaman-core/assets/packedGraphics", "menuGraphics");

		new LwjglApplication(new GDXGame(), config);
	}
}
