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
 * Each resource has its own "accesscounter" meaning that if you call a load method for the same resource twice you also need to dispose it twice.
 * The accesscounter is increased whenever calling a load method and is decreased whenever calling a dispose method. If the counter is 0 then the
 * resource is disposed completely.
 * 
 * F.e. if one of your gamestates requires texture A and another one also requires texture A and both gamestates are loaded within GDXGame then
 * disposing the resources of one gamestate does not make texture A invalid for the other gamestate since it is not disposed (accesscounter is one).
 * 
 */
public enum ResourceManager {
	INSTANCE;

	/**
	 * Resource is a simple class to counter how many times a specific resource (textureatlas, sound, ...) is accessed.
	 * The counter is increased whenever a load method is called.
	 * The counter is decreased whenever a dispose method is called.
	 * Whenever the counter of a resource is 0 then the resource can be completely disposed.
	 */
	private class Resource<T> {
		/**
		 * reference to the resource (textureatlas,sprite,sound,music,skin)
		 */
		private T	resource;
		/**
		 * accesscounter of the resource. if 0 then the resource is no longer needed
		 */
		private int	counter;

		public Resource(T resource) {
			this.resource = resource;
			counter = 1;
		}

		public void incCounter() {
			++counter;
		}

		public int decCounter() {
			return --counter;
		}

		public T getResource() {
			return resource;
		}
	}

	/**
	 * map for storing texture atlases. Key is the filepath of the texture atlas
	 */
	private Map<String, Resource<TextureAtlas>>					textureAtlasMap;
	/**
	 * map for storing skins. Key is the skintype defined in the SkinType enum
	 */
	private Map<SkinType, Resource<Skin>>						skinMap;
	/**
	 * map for storing the AtlasRegions of an texture atlas. The region
	 * is automatically linked with the TextureType containing the texture.
	 */
	private Map<TextureAtlas, Map<TextureType, AtlasRegion>>	atlasRegionMap;
	/**
	 * map for storing AnimatedSprites. Key is the TextureType of the sprite that describes the used texture.
	 */
	private Map<TextureType, Resource<AnimatedSprite>>			spriteMap;
	/**
	 * map for storing sounds. Key is the soundtype defined in the SoundType enum
	 */
	private Map<SoundType, Resource<Sound>>						soundMap;
	/**
	 * map for storing music. Key is the musictype defined in the MusicType enum
	 */
	private Map<MusicType, Resource<Music>>						musicMap;

	private ResourceManager() {
		skinMap = new HashMap<SkinType, Resource<Skin>>();
		textureAtlasMap = new HashMap<String, Resource<TextureAtlas>>();
		atlasRegionMap = new HashMap<TextureAtlas, Map<TextureType, AtlasRegion>>();
		spriteMap = new HashMap<TextureType, Resource<AnimatedSprite>>();
		soundMap = new HashMap<SoundType, Resource<Sound>>();
		musicMap = new HashMap<MusicType, Resource<Music>>();
	}

	/**
	 * loads a specific texture atlas. The loaded atlas can then be retrieved with the 
	 * getTextureAtlas() method.
	 * Increases the accesscounter of the texture atlas if already loaded.
	 * 
	 * @param internalAtlasName internal file path of the texture atlas
	 */
	public void loadTextureAtlas(String internalAtlasName) {
		if (textureAtlasMap != null && !textureAtlasMap.containsKey(internalAtlasName)) {
			// load texture atlas
			TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(internalAtlasName));
			textureAtlasMap.put(internalAtlasName, new Resource<TextureAtlas>(textureAtlas));
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
		} else {
			// increase resource counter
			textureAtlasMap.get(internalAtlasName).incCounter();
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
			return textureAtlasMap.get(internalAtlasName).getResource();
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "TextureAtlas was not successfully loaded yet: " + internalAtlasName);
			return null;
		}
	}

	/**
	 * Reduces the accesscounter of the texture atlas. If the accesscounter is 0 then
	 * the texture atlas that was already loaded with the loadTextureAtlas() method
	 * gets disposed.
	 * 
	 * @param internalAtlasName internal file path of the texture atlas
	 */
	public void disposeTextureAtlasAndSprites(String internalAtlasName) {
		if (textureAtlasMap != null && textureAtlasMap.containsKey(internalAtlasName)) {
			Resource<TextureAtlas> resource = textureAtlasMap.get(internalAtlasName);
			if (resource.decCounter() <= 0) {
				TextureAtlas textureAtlas = textureAtlasMap.get(internalAtlasName).getResource();

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
	}

	/**
	 * loads a specific animated sprite. The loaded sprite can then be retrieved with the 
	 * getAnimatedSprite() method.
	 * Increases the accesscounter of the sprite if already loaded.
	 * 
	 * @param TextureType type of texture to be used for the sprite
	 */
	public void loadAnimatedSprite(TextureType texture) {
		// check if texture atlas map contains the atlas type that should be used 
		if (spriteMap != null && !spriteMap.containsKey(texture) && textureAtlasMap != null && textureAtlasMap.containsKey(texture.getTextureAtlasPath())) {
			// check if the atlas regions were successfully loaded for the texture atlas
			TextureAtlas textureAtlas = textureAtlasMap.get(texture.getTextureAtlasPath()).getResource();
			if (atlasRegionMap != null && atlasRegionMap.containsKey(textureAtlas)) {
				Map<TextureType, AtlasRegion> regionMap = atlasRegionMap.get(textureAtlas);
				if (regionMap != null && regionMap.containsKey(texture)) {
					// create sprite out of texture atlas region's texture
					AnimatedSprite animatedSprite = new AnimatedSprite(regionMap.get(texture), texture);
					spriteMap.put(texture, new Resource<AnimatedSprite>(animatedSprite));
				}
			} else {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "TextureAtlas does not contain region: " + texture.getAtlasRegionName());
			}
		} else {
			// increase resource counter
			spriteMap.get(texture).incCounter();
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
			return spriteMap.get(texture).getResource();
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "AnimatedSprite was not successfully loaded yet: " + texture.getOriginalFilePath());
			return null;
		}
	}

	/**
	 * Reduces the accesscounter of the sprite. If the accesscounter is 0 then
	 * the sprite that was already loaded with the loadAnimatedSprite() method
	 * gets disposed.
	 * 
	 * @param texture type of texture of the sprite
	 */
	public void disposeAnimatedSprite(TextureType texture) {
		if (spriteMap != null && spriteMap.containsKey(texture)) {
			Resource<AnimatedSprite> resource = spriteMap.get(texture);
			if (resource.decCounter() <= 0) {
				spriteMap.remove(texture);
			}
		}
	}

	/**
	 * loads a specific sound. The loaded sound can then be retrieved with the getSound() method.
	 * Increases the accesscounter of the sound if already loaded.
	 * 
	 * @param sound type of sound to be used for the sound
	 */
	public void loadSound(SoundType sound) {
		if (soundMap != null && !soundMap.containsKey(sound)) {
			soundMap.put(sound, new Resource<Sound>(Gdx.audio.newSound(Gdx.files.internal(sound.getFilePath()))));
		} else {
			// increase resource counter
			soundMap.get(sound).incCounter();
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
			return soundMap.get(sound).getResource();
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Sound was not successfully loaded yet: " + sound.getFilePath());
			return null;
		}
	}

	/**
	 * Reduces the accesscounter of the sound. If the accesscounter is 0 then
	 * the sound that was already loaded with the loadSound() method
	 * gets disposed.
	 * 
	 * @param sound type of sound
	 */
	public void disposeSound(SoundType sound) {
		if (soundMap != null && soundMap.containsKey(sound)) {
			Resource<Sound> resource = soundMap.get(sound);
			if (resource.decCounter() <= 0) {
				soundMap.get(sound).getResource().dispose();
				soundMap.remove(sound);
			}
		}
	}

	/**
	 * loads a specific music. The loaded music can then be retrieved with the getMusic() method.
	 * Increases the accesscounter of the music if already loaded.
	 * 
	 * @param music type of music to be used for the music
	 */
	public void loadMusic(MusicType music) {
		if (musicMap != null && !musicMap.containsKey(music)) {
			musicMap.put(music, new Resource<Music>(Gdx.audio.newMusic(Gdx.files.internal(music.getFilePath()))));
		} else {
			// increase resource counter
			musicMap.get(music).incCounter();
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
			return musicMap.get(music).getResource();
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Music was not successfully loaded yet: " + music.getFilePath());
			return null;
		}
	}

	/**
	 * Reduces the accesscounter of the music. If the accesscounter is 0 then
	 * the music that was already loaded with the loadMusic() method
	 * gets disposed.
	 * 
	 * @param music type of music
	 */
	public void disposeMusic(MusicType music) {
		if (musicMap != null && musicMap.containsKey(music)) {
			Resource<Music> resource = musicMap.get(music);
			if (resource.decCounter() <= 0) {
				musicMap.get(music).getResource().dispose();
				musicMap.remove(music);
			}
		}
	}

	/**
	 * loads a specific skin. The loaded skin can then be retrieved with the getSkin() method.
	 * Increases the accesscounter of the skin if already loaded.
	 * 
	 * @param skin type of skin to be used for the skin
	 */
	public void loadSkin(SkinType skin) {
		if (skinMap != null && !skinMap.containsKey(skin)) {
			loadTextureAtlas(skin.getTextureAtlasFilePath());
			TextureAtlas textureAtlas = getTextureAtlas(skin.getTextureAtlasFilePath());
			skinMap.put(skin, new Resource<Skin>(new Skin(Gdx.files.internal(skin.getFilePath()), textureAtlas)));
		} else {
			// increase resource counter
			skinMap.get(skin).incCounter();
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
			return skinMap.get(skin).getResource();
		} else {
			Gdx.app.log(GameConstants.LOG_TAG_INFO, "Skin was not successfully loaded yet: " + skin.getFilePath());
			return null;
		}
	}

	/**
	 * Reduces the accesscounter of the skin. If the accesscounter is 0 then
	 * the skin that was already loaded with the loadSkin() method
	 * gets disposed.
	 * 
	 * @param skin type of skin
	 */
	public void disposeSkin(SkinType skin) {
		if (skinMap != null && skinMap.containsKey(skin)) {
			Resource<Skin> resource = skinMap.get(skin);
			if (resource.decCounter() <= 0) {
				disposeTextureAtlasAndSprites(skin.getTextureAtlasFilePath());
				skinMap.get(skin).getResource().dispose();
				skinMap.remove(skin);
			}
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
			for (Map.Entry<String, Resource<TextureAtlas>> entry : textureAtlasMap.entrySet()) {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "Undisposed texture atlas in disposeAllResources(): " + entry.getKey() + "\tRemaining counter was:" + entry.getValue().counter);
				entry.getValue().getResource().dispose();
			}
			textureAtlasMap.clear();
			atlasRegionMap.clear();
			spriteMap.clear();
		}

		if (soundMap != null) {
			for (Map.Entry<SoundType, Resource<Sound>> entry : soundMap.entrySet()) {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "Undisposed sound in disposeAllResources(): " + entry.getKey() + "\tRemaining counter was:" + entry.getValue().counter);
				entry.getValue().getResource().dispose();
			}
			soundMap.clear();
		}

		if (musicMap != null) {
			for (Map.Entry<MusicType, Resource<Music>> entry : musicMap.entrySet()) {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "Undisposed music in disposeAllResources(): " + entry.getKey() + "\tRemaining counter was:" + entry.getValue().counter);
				entry.getValue().getResource().dispose();
			}
			musicMap.clear();
		}

		if (skinMap != null) {
			for (Map.Entry<SkinType, Resource<Skin>> entry : skinMap.entrySet()) {
				Gdx.app.log(GameConstants.LOG_TAG_INFO, "Undisposed skin in disposeAllResources(): " + entry.getKey() + "\tRemaining counter was:" + entry.getValue().counter);
				entry.getValue().getResource().dispose();
			}
			skinMap.clear();
		}
	}
}
