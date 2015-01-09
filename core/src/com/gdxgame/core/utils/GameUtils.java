package com.gdxgame.core.utils;

import java.util.Locale;
import java.util.MissingResourceException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.graphics.AnimatedSprite;
import com.gdxgame.core.model.AnimatedGameObject;
import com.gdxgame.core.model.GameObject;

/**
 * GameUtils is a general game utility class. It contains utility methods for game objects and also methods
 * to manipulate the game configuration file for desktop applications.
 * 
 * TODO add support for android and WebGL configuration files (check libgdx Preferences)
 */
public final class GameUtils {
	/**
	 * rectangle to store the current camera viewport
	 */
	private static Rectangle	cameraBounds;
	/**
	 * bundle that contains the labels of the game
	 */
	private static I18NBundle	labelBundle;
	/**
	 * config file that stores the desktop game properties like resolution, fullscreen, sound volume
	 */
	private static Preferences	cfgPreference;

	/**
	 * renders an animated game object using the given animated sprite. The method checks if the game object is within the
	 * camera viewport. If it is not within the camera viewport then the render call will be ignored.
	 * 
	 * @param spriteBatch SpriteBatch to render the AnimatedSprite
	 * @param camera game camera to check if the game object is within the view port 
	 * @param gameObj game object to be rendered
	 * @param sprite sprite to be used for the game object rendering
	 */
	public static void renderGameObject(SpriteBatch spriteBatch, Camera camera, AnimatedGameObject gameObj, AnimatedSprite sprite) {
		if (isWithinCameraView(camera, gameObj)) {
			// game object is within visible area of the game
			// adjust the animated sprite properties to the animated game object properties
			sprite.flip(gameObj.isFlipX(), gameObj.isFlipY());
			sprite.setFrameIndex(gameObj.getFrameIndexX(), gameObj.getFrameIndexY());
			sprite.setPosition(gameObj.getX(), gameObj.getY());
			sprite.setSize(gameObj.getWidth(), gameObj.getHeight());
			spriteBatch.setColor(gameObj.getRed(), gameObj.getGreen(), gameObj.getBlue(), 1.0f - gameObj.getTransparency());
			// render the animated sprite with the animated game object's properties
			spriteBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		}
	}

	/**
	 * checks if a game object is within the given camera's viewport.
	 * 
	 * @param camera game camera to check if the game object is within the view port
	 * @param gameObj game object to check
	 * 
	 * @return <b>true</b> if game object is within camera viewport. <b>false</b> otherwise.
	 */
	public static boolean isWithinCameraView(Camera camera, GameObject gameObj) {
		if (cameraBounds == null) {
			// first call to this method
			// create a Rectangle instance and keep it for later use
			// it is better to keep an instance to avoid gc during update/render calls
			cameraBounds = new Rectangle();
		}

		cameraBounds.x = camera.position.x - camera.viewportWidth / 2;
		cameraBounds.y = camera.position.y - camera.viewportHeight / 2;
		cameraBounds.width = camera.viewportWidth;
		cameraBounds.height = camera.viewportHeight;

		return Intersector.overlaps(cameraBounds, gameObj.getBoundingRectangle());
	}

	/**
	 * checks if two game objects are colliding using their bounding rectangles
	 * 
	 * @param gameObjA game object A
	 * @param gameObjB game object B
	 * 
	 * @return <b>true</b> if the game objects are colliding. <b>false</b> otherwise.
	 */
	public static boolean intersects(GameObject gameObjA, GameObject gameObjB) {
		return Intersector.overlaps(gameObjA.getBoundingRectangle(), gameObjB.getBoundingRectangle());
	}

	/**
	 * loads the game config file for a desktop application. The config files contains information like
	 * the resolution, music volume or sound volume.
	 * 
	 * The file path and config section are defined in the GameConstants
	 */
	public static void loadCfgPreference() {
		cfgPreference = Gdx.app.getPreferences(GameConstants.PREFERENCE_CFG_NAME);
		if (cfgPreference.get().size() <= 0) {
			// config preference is not available yet on the system
			// create it and store the default values in it
			cfgPreference.putInteger(GameConstants.PREFERENCE_KEY_WIDTH, Gdx.graphics.getWidth());
			cfgPreference.putInteger(GameConstants.PREFERENCE_KEY_HEIGHT, Gdx.graphics.getHeight());
			cfgPreference.putString(GameConstants.PREFERENCE_KEY_FULLSCREEN, "" + Gdx.graphics.isFullscreen());
			cfgPreference.putString(GameConstants.PREFERENCE_KEY_MUSIC_VOLUME, "" + 100);
			cfgPreference.putString(GameConstants.PREFERENCE_KEY_SOUND_VOLUME, "" + 100);
			cfgPreference.putString(GameConstants.PREFERENCE_KEY_LANGUAGE, Locale.getDefault().getLanguage());
			cfgPreference.flush();
		}
	}

	/**
	 * adds or updates an entry in the game config file
	 * 
	 * @param key key of the entry
	 * @param value value of the entry
	 */
	public static void setCfgPreferenceValue(String key, String value) {
		if (cfgPreference != null) {
			cfgPreference.putString(key, value);
			cfgPreference.flush();
		}
	}

	/**
	 * returns value of the specified key within the game config file
	 * 
	 * @param key key of the entry
	 * @param type type of the entry (int,float,boolean,etc.)
	 * 
	 * @return <b>null</b> if entry cannot be found in the config file. Otherwise the value of the specified key will be returned.
	 */
	public static String getCfgPreferenceValue(String key) {
		if (cfgPreference != null) {
			return cfgPreference.getString(key);
		}

		return null;
	}

	/**
	 * returns localized label {@code labelKey} out of the label bundle file.
	 * 
	 * The label bundle file path is specified within the GameConstants class (check LABE_BUNDLE_PATH)
	 * 
	 * @param labelKey key to be searched within the label bundle
	 * 
	 * @return localized label of key {@code labelKey}
	 */
	public static String getLocalizedLabel(String labelKey) {
		if (labelBundle == null) {
			String cfgFileValue = getCfgPreferenceValue(GameConstants.PREFERENCE_KEY_LANGUAGE);
			Locale locale = null;
			if (cfgFileValue != null) {
				locale = new Locale(cfgFileValue);
			} else {
				// get default locale
				locale = Locale.getDefault();
			}
			labelBundle = I18NBundle.createBundle(Gdx.files.internal(GameConstants.LABEL_BUNDLE_PATH), locale);
		}

		String result = "";
		try {
			result = labelBundle.get(labelKey);
		} catch (MissingResourceException e) {
			Gdx.app.error(GameConstants.LOG_TAG_ERROR, "Could not find label for key: " + labelKey, e);
		}

		return result;
	}

	private GameUtils() {
	}
}
