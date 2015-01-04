package com.gdxgame.core.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxgame.constants.GameConstants;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.SkinType;
import com.gdxgame.core.enums.SoundType;
import com.gdxgame.core.enums.TextureType;
import com.gdxgame.core.graphics.AnimatedSprite;

/**
 * The ResourceManager singleton is the core singleton to load and dispose resources. It supports loading and disposing of the following objects:
 * 		- libgdx TextureAtlas
 * 		- libgdx Skin
 * 		- AnimatedSprite
 * 		- libgdx Sound
 * 		- libgdx Music
 * 
 * ResourceManager keeps track of already loaded resources to not load them multiple times. It also has a convenient method to dispose
 * all current resources. This is useful when the game is closing.
 * 
 */
public enum ResourceManager {
	INSTANCE;

	/**
	 * map for storing texture atlases. Key is the filepath of the texture atlas
	 */
	private Map<String, TextureAtlas>							textureAtlasMap;
	/**
	 * map for storing skins. Key is the skintype defined in the SkinType enum
	 */
	private Map<SkinType, Skin>									skinMap;
	/**
	 * map for storing the AtlasRegions of an texture atlas. The region
	 * is automatically linked with the TextureType containing the texture.
	 */
	private Map<TextureAtlas, Map<TextureType, AtlasRegion>>	atlasRegionMap;
	/**
	 * map for storing AnimatedSprites. Key is the TextureType of the sprite that describes the used texture.
	 */
	private Map<TextureType, AnimatedSprite>					spriteMap;
	/**
	 * map for storing sounds. Key is the soundtype defined in the SoundType enum
	 */
	private Map<SoundType, Sound>								soundMap;
	/**
	 * map for storing music. Key is the musictype defined in the MusicType enum
	 */
	private Map<MusicType, Music>								musicMap;

	private ResourceManager() {
		skinMap = new HashMap<SkinType, Skin>();
		textureAtlasMap = new HashMap<String, TextureAtlas>();
		atlasRegionMap = new HashMap<TextureAtlas, Map<TextureType, AtlasRegion>>();
		spriteMap = new HashMap<TextureType, AnimatedSprite>();
		soundMap = new HashMap<SoundType, Sound>();
		musicMap = new HashMap<MusicType, Music>();
	}

	/**
	 * loads a specific texture atlas. The loaded atlas can then be retrieved with the 
	 * getTextureAtlas() method.
	 * 
	 * @param internalAtlasName internal file path of the texture atlas
	 */
	public void loadTextureAtlas(String internalAtlasName) {
		if (textureAtlasMap != null && !textureAtlasMap.containsKey(internalAtlasName)) {
			// load texture atlas
			TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(internalAtlasName));
			textureAtlasMap.put(internalAtlasName, textureAtlas);
			HashMap<TextureType, AtlasRegion> regionMap = new HashMap<TextureType, TextureAtlas.AtlasRegion>();
			atlasRegionMap.put(textureAtlas, regionMap);

			// build the atlas region map by storing each atlas region by its
			// corresponding TextureType to the map
			for (AtlasRegion region : textureAtlas.getRegions()) {
				TextureType key = TextureType.getGraphicsConstantByAtlasRegionName(region.name);
				if (key != null) {
					regionMap.put(key, region);
				} else {
					Gdx.app.log(GameConstants.LOG_TAG_INFO, "Undefined TextureType for region: " + region.name);
				}
			}
		}
	}

	/**
	 * returns a specific texture atlas that was already loaded with the loadTextureAtlas() method
	 * 
	 * @param internalAtlasName internal file path of the texture atlas
	 * 
	 * @return <b>null</b> if the texture atlas was not successfully loaded yet. Otherwise the texture atlas will be returned.
	 */
	public TextureAtlas getTextureAtlas(String internalAtlasName) {
		if (textureAtlasMap != null && textureAtlasMap.containsKey(internalAtlasName)) {
			return textureAtlasMap.get(internalAtlasName);
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "TextureAtlas was not successfully loaded yet: " + internalAtlasName);
			return null;
		}
	}

	/**
	 * disposes a specific texture atlas that was already loaded with the loadTextureAtlas() method
	 * 
	 * @param internalAtlasName internal file path of the texture atlas
	 */
	public void disposeTextureAtlasAndSprites(String internalAtlasName) {
		if (textureAtlasMap != null && textureAtlasMap.containsKey(internalAtlasName)) {
			TextureAtlas textureAtlas = textureAtlasMap.get(internalAtlasName);

			// dispose all related animated sprites of the texture atlas' regions
			Map<TextureType, AtlasRegion> regionMap = atlasRegionMap.get(textureAtlas);
			for (TextureType type : regionMap.keySet()) {
				disposeAnimatedSprite(type);
			}

			// dispose the atlas and remove it from the textureAtlas map
			textureAtlas.dispose();
			regionMap.clear();
			textureAtlasMap.remove(internalAtlasName);
		}
	}

	/**
	 * loads a specific animated sprite. The loaded sprite can then be retrieved with the 
	 * getAnimatedSprite() method.
	 * 
	 * @param TextureType type of texture to be used for the sprite
	 */
	public void loadAnimatedSprite(TextureType texture) {
		// check if texture atlas map contains the atlas type that should be used 
		if (spriteMap != null && !spriteMap.containsKey(texture) && textureAtlasMap != null && textureAtlasMap.containsKey(texture.getTextureAtlasPath())) {
			// check if the atlas regions were successfully loaded for the texture atlas
			TextureAtlas textureAtlas = textureAtlasMap.get(texture.getTextureAtlasPath());
			if (atlasRegionMap != null && atlasRegionMap.containsKey(textureAtlas)) {
				Map<TextureType, AtlasRegion> regionMap = atlasRegionMap.get(textureAtlas);
				if (regionMap != null && regionMap.containsKey(texture)) {
					// create sprite out of texture atlas region's texture
					AnimatedSprite animatedSprite = new AnimatedSprite(regionMap.get(texture), texture);
					spriteMap.put(texture, animatedSprite);
				}
			} else {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "TextureAtlas does not contain region: " + texture.getAtlasRegionName());
			}
		}
	}

	/**
	 * returns a specific animated sprite that was already loaded with the loadAnimatedSprite() method
	 * 
	 * @param texture type of texture of the sprite
	 * 
	 * @return <b>null</b> if the sprite was not successfully loaded yet. Otherwise the sprite will be returned.
	 */
	public AnimatedSprite getAnimatedSprite(TextureType texture) {
		if (spriteMap != null && spriteMap.containsKey(texture)) {
			return spriteMap.get(texture);
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "AnimatedSprite was not successfully loaded yet: " + texture.getOriginalFilePath());
			return null;
		}
	}

	/**
	 * disposes a specific animated sprite that was already loaded with the loadAnimatedSprite() method
	 * 
	 * @param texture type of texture of the sprite
	 */
	public void disposeAnimatedSprite(TextureType texture) {
		if (spriteMap != null && spriteMap.containsKey(texture)) {
			spriteMap.remove(texture);
		}
	}

	/**
	 * loads a specific sound. The loaded sound can then be retrieved with the getSound() method.
	 * 
	 * @param sound type of sound to be used for the sound
	 */
	public void loadSound(SoundType sound) {
		if (soundMap != null && !soundMap.containsKey(sound)) {
			soundMap.put(sound, Gdx.audio.newSound(Gdx.files.internal(sound.getFilePath())));
		}
	}

	/**
	 * returns a specific sound that was already loaded with the loadSound() method
	 * 
	 * @param sound type of sound
	 * 
	 * @return <b>null</b> if the sound was not successfully loaded yet. Otherwise the sound will be returned.
	 */
	public Sound getSound(SoundType sound) {
		if (soundMap != null && soundMap.containsKey(sound)) {
			return soundMap.get(sound);
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Sound was not successfully loaded yet: " + sound.getFilePath());
			return null;
		}
	}

	/**
	 * disposes a specific sound that was already loaded with the loadSound() method
	 * 
	 * @param sound type of sound
	 */
	public void disposeSound(SoundType sound) {
		if (soundMap != null && soundMap.containsKey(sound)) {
			soundMap.get(sound).dispose();
			soundMap.remove(sound);
		}
	}

	/**
	 * loads a specific music. The loaded music can then be retrieved with the getMusic() method.
	 * 
	 * @param music type of music to be used for the music
	 */
	public void loadMusic(MusicType music) {
		if (musicMap != null && !musicMap.containsKey(music)) {
			musicMap.put(music, Gdx.audio.newMusic(Gdx.files.internal(music.getFilePath())));
		}
	}

	/**
	 * returns a specific music that was already loaded with the loadMusic() method
	 * 
	 * @param music type of music
	 * 
	 * @return <b>null</b> if the music was not successfully loaded yet. Otherwise the music will be returned.
	 */
	public Music getMusic(MusicType music) {
		if (musicMap != null && musicMap.containsKey(music)) {
			return musicMap.get(music);
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Music was not successfully loaded yet: " + music.getFilePath());
			return null;
		}
	}

	/**
	 * disposes a specific music that was already loaded with the loadMusic() method
	 * 
	 * @param music type of music
	 */
	public void disposeMusic(MusicType music) {
		if (musicMap != null && musicMap.containsKey(music)) {
			musicMap.get(music).dispose();
			musicMap.remove(music);
		}
	}

	/**
	 * loads a specific skin. The loaded skin can then be retrieved with the getSkin() method.
	 * 
	 * @param skin type of skin to be used for the skin
	 */
	public void loadSkin(SkinType skin) {
		if (skinMap != null && !skinMap.containsKey(skin)) {
			loadTextureAtlas(skin.getTextureAtlasFilePath());
			TextureAtlas textureAtlas = getTextureAtlas(skin.getTextureAtlasFilePath());
			skinMap.put(skin, new Skin(Gdx.files.internal(skin.getFilePath()), textureAtlas));
		}

	}

	/**
	 * returns a specific skin that was already loaded with the loadSkin() method
	 * 
	 * @param skin type of skin
	 * 
	 * @return <b>null</b> if the skin was not successfully loaded yet. Otherwise the skin will be returned.
	 */
	public Skin getSkin(SkinType skin) {
		if (skinMap != null && skinMap.containsKey(skin)) {
			return skinMap.get(skin);
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Skin was not successfully loaded yet: " + skin.getFilePath());
			return null;
		}
	}

	/**
	 * disposes a specific skin that was already loaded with the loadSkin() method
	 * 
	 * @param skin type of skin
	 */
	public void disposeSkin(SkinType skin) {
		if (skinMap != null && skinMap.containsKey(skin)) {
			disposeTextureAtlasAndSprites(skin.getTextureAtlasFilePath());
			skinMap.get(skin).dispose();
			skinMap.remove(skin);
		}
	}

	/**
	 * disposes all resources (texture atlas, sprites, sounds ans music) that were loaded with the ResourceManager.
	 * There are no more resources available after a call to this method.
	 * 
	 * This method is automatically called when the game is closing.
	 */
	public void disposeAllResources() {
		if (textureAtlasMap != null) {
			for (String key : textureAtlasMap.keySet()) {
				disposeTextureAtlasAndSprites(key);
			}
		}

		if (soundMap != null) {
			for (Sound snd : soundMap.values()) {
				snd.dispose();
			}
		}

		if (musicMap != null) {
			for (Music music : musicMap.values()) {
				music.dispose();
			}
		}
	}
}
