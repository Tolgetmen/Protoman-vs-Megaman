package com.megaman.core;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.megaman.enums.AudioType;
import com.megaman.enums.SkinType;
import com.megaman.enums.TextureType;
import com.megaman.graphics.AnimatedSprite;

public enum ResourceManager {
	INSTANCE;

	private Map<String, TextureAtlas>							textureAtlasMap;
	private Map<SkinType, Skin>									skinMap;
	private Map<TextureAtlas, Map<TextureType, AtlasRegion>>	atlasRegionMap;
	private Map<TextureType, AnimatedSprite>					spriteMap;
	private Map<AudioType, Sound>								soundMap;
	private Map<AudioType, Music>								musicMap;

	private ResourceManager() {
		skinMap = new HashMap<SkinType, Skin>();
		textureAtlasMap = new HashMap<String, TextureAtlas>();
		atlasRegionMap = new HashMap<TextureAtlas, Map<TextureType, AtlasRegion>>();
		spriteMap = new HashMap<TextureType, AnimatedSprite>();
		soundMap = new HashMap<AudioType, Sound>();
		musicMap = new HashMap<AudioType, Music>();
	}

	public void loadTextureAtlas(String internalAtlasName) {
		if (textureAtlasMap != null && !textureAtlasMap.containsKey(internalAtlasName)) {
			// load texture atlas
			TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(internalAtlasName));
			textureAtlasMap.put(internalAtlasName, textureAtlas);
			HashMap<TextureType, AtlasRegion> regionMap = new HashMap<TextureType, TextureAtlas.AtlasRegion>();
			atlasRegionMap.put(textureAtlas, regionMap);

			// create an animated sprite for each texture
			for (AtlasRegion region : textureAtlas.getRegions()) {
				TextureType key = TextureType.getGraphicsConstantByAtlasRegionName(region.name);
				if (key != null) {
					regionMap.put(key, region);
				} else {
					Gdx.app.log("loadTextureAtlas", "Undefined MMGraphicsConstants for region: " + region.name);
				}
			}
		}
	}

	public TextureAtlas getTextureAtlas(String internalAtlasName) {
		if (textureAtlasMap != null && textureAtlasMap.containsKey(internalAtlasName)) {
			return textureAtlasMap.get(internalAtlasName);
		} else {
			Gdx.app.log("getTextureAtlas", "TextureAtlas was not successfully loaded yet: " + internalAtlasName);
			return null;
		}
	}

	public void disposeTextureAtlasAndSprites(String internalAtlasName) {
		if (textureAtlasMap != null && textureAtlasMap.containsKey(internalAtlasName)) {
			TextureAtlas textureAtlas = textureAtlasMap.get(internalAtlasName);
			Map<TextureType, AtlasRegion> regionMap = atlasRegionMap.get(textureAtlas);
			for (TextureType type : regionMap.keySet()) {
				disposeAnimatedSprite(type);
			}
			textureAtlas.dispose();
			regionMap.clear();
			textureAtlasMap.remove(internalAtlasName);
		}
	}

	public void loadAnimatedSprite(TextureType texture) {
		if (spriteMap != null && !spriteMap.containsKey(texture) && textureAtlasMap != null && textureAtlasMap.containsKey(texture.getTextureAtlasPath())) {
			TextureAtlas textureAtlas = textureAtlasMap.get(texture.getTextureAtlasPath());
			if (atlasRegionMap != null && atlasRegionMap.containsKey(textureAtlas)) {
				Map<TextureType, AtlasRegion> regionMap = atlasRegionMap.get(textureAtlas);
				if (regionMap != null && regionMap.containsKey(texture)) {
					AnimatedSprite animatedSprite = new AnimatedSprite(regionMap.get(texture), texture.getNumColumns(), texture.getNumRows());
					spriteMap.put(texture, animatedSprite);
				}
			} else {
				Gdx.app.log("loadAnimatedSprite", "TextureAtlas does not contain region: " + texture.getAtlasRegionName());
			}
		}
	}

	public AnimatedSprite getAnimatedSprite(TextureType texture) {
		if (spriteMap != null && spriteMap.containsKey(texture)) {
			return spriteMap.get(texture);
		} else {
			Gdx.app.log("getAnimatedSprite", "AnimatedSprite was not successfully loaded yet: " + texture.getOriginalFilePath());
			return null;
		}
	}

	public void disposeAnimatedSprite(TextureType texture) {
		if (spriteMap != null && spriteMap.containsKey(texture)) {
			spriteMap.remove(texture);
		}
	}

	public void loadSound(AudioType sound) {
		if (!sound.isMusic() && soundMap != null && !soundMap.containsKey(sound)) {
			soundMap.put(sound, Gdx.audio.newSound(Gdx.files.internal(sound.getFilePath())));
		}
	}

	public Sound getSound(AudioType sound) {
		if (!sound.isMusic() && soundMap != null && soundMap.containsKey(sound)) {
			return soundMap.get(sound);
		} else {
			Gdx.app.log("getSound", "Sound was not successfully loaded yet: " + sound.getFilePath());
			return null;
		}
	}

	public void disposeSound(AudioType sound) {
		if (!sound.isMusic() && soundMap != null && soundMap.containsKey(sound)) {
			soundMap.get(sound).dispose();
			soundMap.remove(sound);
		}
	}

	public void loadMusic(AudioType music) {
		if (music.isMusic() && musicMap != null && !musicMap.containsKey(music)) {
			musicMap.put(music, Gdx.audio.newMusic(Gdx.files.internal(music.getFilePath())));
		}
	}

	public Music getMusic(AudioType music) {
		if (music.isMusic() && musicMap != null && musicMap.containsKey(music)) {
			return musicMap.get(music);
		} else {
			Gdx.app.log("getMusic", "Music was not successfully loaded yet: " + music.getFilePath());
			return null;
		}
	}

	public void disposeMusic(AudioType music) {
		if (music.isMusic() && musicMap != null && musicMap.containsKey(music)) {
			musicMap.get(music).dispose();
			musicMap.remove(music);
		}
	}

	public void loadSkin(SkinType skin) {
		if (skinMap != null && !skinMap.containsKey(skin)) {
			loadTextureAtlas(skin.getTextureAtlasFilePath());
			TextureAtlas textureAtlas = getTextureAtlas(skin.getTextureAtlasFilePath());
			skinMap.put(skin, new Skin(Gdx.files.internal(skin.getFilePath()), textureAtlas));
		}

	}

	public Skin getSkin(SkinType skin) {
		if (skinMap != null && skinMap.containsKey(skin)) {
			return skinMap.get(skin);
		} else {
			Gdx.app.log("getSkin", "Skin was not successfully loaded yet: " + skin.getFilePath());
			return null;
		}
	}

	public void disposeSkin(SkinType skin) {
		if (skinMap != null && skinMap.containsKey(skin)) {
			disposeTextureAtlasAndSprites(skin.getTextureAtlasFilePath());
			skinMap.get(skin).dispose();
			skinMap.remove(skin);
		}
	}

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
