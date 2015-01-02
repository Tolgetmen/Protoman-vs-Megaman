package com.megaman.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.audio.Sound;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SoundType;

public enum SoundManager {
	INSTANCE;

	private Music	currentMusic;
	private int		musicVolume;
	private int		soundVolume;

	private SoundManager() {
		musicVolume = 100;
		soundVolume = 100;

		switch (Gdx.app.getType()) {
			case Android:
			case WebGL: {
				//ignore those cases for now as this is a desktop game
				// to support those types we will need to store volume
				// values in a preference file (check libgdx wiki preferences)
				break;
			}
			default: {
				// read from config file
				musicVolume = GameUtils.getCfgFileValue("musicVolume", Integer.class);
				soundVolume = GameUtils.getCfgFileValue("soundVolume", Integer.class);
				break;
			}
		}
	}

	public void playSound(SoundType sound) {
		Sound snd = ResourceManager.INSTANCE.getSound(sound);
		snd.setVolume(snd.play(), soundVolume / 100.0f);
	}

	public float stopCurrentMusic() {
		if (currentMusic != null) {
			float result = currentMusic.getPosition();
			currentMusic.stop();
			return result;
		}

		return 0.0f;
	}

	public void setMusicPosition(float position) {
		if (currentMusic != null) {
			currentMusic.setPosition(position);
		}
	}

	public void playMusic(MusicType music, boolean loop, OnCompletionListener onCompleteListener) {
		Music msc = ResourceManager.INSTANCE.getMusic(music);

		if (currentMusic != null) {
			if (currentMusic.equals(msc))
				return;
			currentMusic.stop();
		}

		currentMusic = msc;
		currentMusic.setLooping(loop);
		currentMusic.setVolume(musicVolume / 100.0f);
		currentMusic.play();

		if (onCompleteListener != null) {
			currentMusic.setOnCompletionListener(onCompleteListener);
		}
	}

	public void playMusic(MusicType music, boolean loop) {
		playMusic(music, loop, null);
	}

	private int getVolumeValue(int volume) {
		if (volume < 0) {
			return 0;
		} else if (volume > 100) {
			return 100;
		} else {
			return volume;
		}
	}

	public int getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(int volume) {
		musicVolume = getVolumeValue(volume);
		GameUtils.setCfgFileValue("musicVolume", "" + musicVolume);
		if (currentMusic != null) {
			currentMusic.setVolume(getVolumeValue(musicVolume) / 100.0f);
		}
	}

	public int getSoundVolume() {
		return soundVolume;
	}

	public void setSoundVolume(int volume) {
		soundVolume = getVolumeValue(volume);
		GameUtils.setCfgFileValue("soundVolume", "" + soundVolume);
	}
}
