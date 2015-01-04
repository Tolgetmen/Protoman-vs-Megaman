package com.megaman.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.audio.Sound;
import com.megaman.constants.GameConstants;
import com.megaman.core.enums.MusicType;
import com.megaman.core.enums.SoundType;

/**
 * The SoundManager singleton is the core singleton to play and stop libgdx Sound and Music. It also supports
 * functionality to adjust the volume of sounds and music.
 */
public enum SoundManager {
	INSTANCE;

	/**
	 * reference to the current active music instance
	 */
	private Music	currentMusic;
	/**
	 * volume of music. The volume has a value between 0 and 100 and will be
	 * converted to the libgdx internal volume value of 0.0f to 1.0f
	 */
	private int		musicVolume;
	/**
	 * volume of sound. The volume has a value between 0 and 100 and will be
	 * converted to the libgdx internal volume value of 0.0f to 1.0f
	 */
	private int		soundVolume;

	private SoundManager() {
		// load real volume values from the config file
		musicVolume = 100;
		soundVolume = 100;

		switch (Gdx.app.getType()) {
			case Android:
			case WebGL: {
				// TODO add support for android and webgl
				// ignore those cases for now as this is a desktop game
				// to support those types we will need to store volume
				// values in a preference file (check libgdx wiki preferences)
				break;
			}
			default: {
				// read from config file
				musicVolume = GameUtils.getCfgFileValue(GameConstants.CFG_KEY_MUSIC_VOLUME, Integer.class);
				soundVolume = GameUtils.getCfgFileValue(GameConstants.CFG_KEY_SOUND_VOLUME, Integer.class);
				break;
			}
		}
	}

	/**
	 * plays a specific sound with the current sound volume
	 * 
	 * @param sound sound to be played
	 */
	public void playSound(SoundType sound) {
		Sound snd = ResourceManager.INSTANCE.getSound(sound);
		snd.setVolume(snd.play(), soundVolume / 100.0f);
	}

	/**
	 * returns current sound volume
	 * 
	 * @return current sound volume (value between 0 and 100)
	 */
	public int getSoundVolume() {
		return soundVolume;
	}

	/**
	 * sets current sound volume and stores the value in the game config file
	 * 
	 * @param volume volume to be set (value between 0 and 100)
	 */
	public void setSoundVolume(int volume) {
		soundVolume = Math.min(100, Math.max(0, volume));
		GameUtils.setCfgFileValue(GameConstants.CFG_KEY_SOUND_VOLUME, "" + soundVolume);
	}

	/**
	 * plays a specific music with the current music volume. If an OnCompletionListener
	 * is passed then the listener will be notified once the music has finished playing. 
	 * 
	 * @param music type of music to be played
	 * @param loop <b>true</b> to loop music playback. <b>false</b> to play music only once.
	 * @param onCompleteListener listener to notify when music has finished playing
	 */
	public void playMusic(MusicType music, boolean loop, OnCompletionListener onCompleteListener) {
		Music msc = ResourceManager.INSTANCE.getMusic(music);

		if (currentMusic != null && !currentMusic.equals(msc)) {
			// a different music is set -> stop the current active music
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

	/**
	 * Similar to {@link #playMusic(MusicType, boolean, com.badlogic.gdx.audio.Music.OnCompletionListener)} 
	 * without specifying an OnCompletionListener
	 * 
	 * @param music type of music to be played
	 * @param loop <b>true</b> to loop music playback. <b>false</b> to play music only once.
	 */
	public void playMusic(MusicType music, boolean loop) {
		playMusic(music, loop, null);
	}

	/**
	 * stops the current active music if there is any and returns the position
	 * of the current active music. This value can be used to resume a music to
	 * its last position.
	 * 
	 * @return position of current active music or <b>0</b> if there is no active music yet
	 */
	public float stopCurrentMusic() {
		if (currentMusic != null) {
			float result = currentMusic.getPosition();
			currentMusic.stop();
			return result;
		}

		return 0.0f;
	}

	/**
	 * sets position of the current active music. See also {@link #stopCurrentMusic()}
	 * 
	 * @param position position of music to be set
	 */
	public void setMusicPosition(float position) {
		if (currentMusic != null) {
			currentMusic.setPosition(position);
		}
	}

	/**
	 * returns current music volume
	 * 
	 * @return current music volume (value between 0 and 100)
	 */
	public int getMusicVolume() {
		return musicVolume;
	}

	/**
	 * sets current music volume and stores value in the game config file
	 * 
	 * @param volume new music volume (value between 0 and 100)
	 */
	public void setMusicVolume(int volume) {
		musicVolume = Math.min(100, Math.max(0, volume));
		GameUtils.setCfgFileValue(GameConstants.CFG_KEY_MUSIC_VOLUME, "" + musicVolume);
		if (currentMusic != null) {
			currentMusic.setVolume(musicVolume / 100.0f);
		}
	}
}
