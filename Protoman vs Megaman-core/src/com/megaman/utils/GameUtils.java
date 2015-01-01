package com.megaman.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.megaman.core.ResourceManager;
import com.megaman.enums.AudioType;
import com.megaman.model.GameObject;

public final class GameUtils {
	private static Rectangle	cameraBounds;
	private static Music		currentMusic;

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

	public static void playSound(AudioType sound) {
		ResourceManager.INSTANCE.getSound(sound).play();
	}

	public static void playMusic(AudioType music, boolean loop, OnCompletionListener onCompleteListener) {
		Music msc = ResourceManager.INSTANCE.getMusic(music);

		if (currentMusic != null) {
			if (currentMusic.equals(msc))
				return;
			currentMusic.stop();
		}

		currentMusic = msc;
		currentMusic.setLooping(loop);
		currentMusic.play();

		if (onCompleteListener != null) {
			currentMusic.setOnCompletionListener(onCompleteListener);
		}
	}

	public static void playMusic(AudioType music, boolean loop) {
		playMusic(music, loop, null);
	}

	private GameUtils() {
	}
}
