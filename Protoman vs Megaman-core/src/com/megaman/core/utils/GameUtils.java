package com.megaman.core.utils;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.megaman.core.model.GameObject;

public final class GameUtils {
	private static Rectangle	cameraBounds;
	private static Ini			cfgFile;
	private static Section		gameCfgSection;

	public static boolean isWithinCameraView(Camera camera, GameObject gameObj) {
		if (cameraBounds == null) {
			cameraBounds = new Rectangle();
		}
		cameraBounds.x = camera.position.x - camera.viewportWidth / 2;
		cameraBounds.y = camera.position.y - camera.viewportHeight / 2;
		cameraBounds.width = camera.viewportWidth;
		cameraBounds.height = camera.viewportHeight;

		return Intersector.overlaps(cameraBounds, gameObj.getBoundingRectangle());
	}

	public static boolean intersects(GameObject gameObjA, GameObject gameObjB) {
		return Intersector.overlaps(gameObjA.getBoundingRectangle(), gameObjB.getBoundingRectangle());
	}

	private static void loadCfgFile() {
		if (gameCfgSection != null) {
			// already loaded
			return;
		}

		// read from config file
		try {
			cfgFile = new Ini(new File("../Protoman vs Megaman-core/assets/game.cfg"));
			gameCfgSection = cfgFile.get("Gameconfig");
		} catch (InvalidFileFormatException e) {
			Gdx.app.error("loadIniFile", "InvalidFileFormatException while initializing gameCfgSection attributes", e);
		} catch (IOException e) {
			Gdx.app.error("loadIniFile", "IOException while initializing gameCfgSection attributes", e);
		}
	}

	public static void setCfgFileValue(String key, String value) {
		loadCfgFile();
		if (gameCfgSection != null) {
			gameCfgSection.put(key, value);
			try {
				cfgFile.store();
			} catch (IOException e) {
				Gdx.app.error("setIniFileValue", "Could not save config file value", e);
			}
		}
	}

	public static <T> T getCfgFileValue(String key, Class<T> type) {
		loadCfgFile();
		if (gameCfgSection != null) {
			return gameCfgSection.get(key, type);
		}

		return null;
	}

	private GameUtils() {
	}
}
