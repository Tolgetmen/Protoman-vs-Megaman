package com.megaman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.model.GameObject;
import com.gdxgame.core.utils.ResourceManager;
import com.megaman.enums.ParticleFXType;

public class ParticleFX extends ParticleEffect implements Poolable {
	private float		loopPosition;
	private GameObject	attachedObj;
	private int			objOffsetX;
	private int			objOffsetY;

	public ParticleFX(GameStateLogic logic) {
		super();
		reset();
	}

	private void initParticleEffect(ParticleFXType type, float duration, float loopPosition) {
		load(Gdx.files.internal(type.getFilePath()), ResourceManager.INSTANCE.getTextureAtlas(type.getTextureAtlasPath()), "effects/");
		scaleEffect(type.getScaleFactor());

		start();

		this.loopPosition = loopPosition * 1000;
	}

	public void initialize(ParticleFXType type, float x, float y, float duration, float loopPosition) {
		setPosition(x, y);
		initParticleEffect(type, duration, loopPosition);
	}

	public void initialize(ParticleFXType type, GameObject attachedObj, int objOffsetX, int objOffsetY, float duration, float loopPosition) {
		setPosition(attachedObj.getX() + objOffsetX, attachedObj.getY() + objOffsetY);
		initParticleEffect(type, duration, loopPosition);

		this.attachedObj = attachedObj;
		this.objOffsetX = objOffsetX;
		this.objOffsetY = objOffsetY;
	}

	@Override
	public void update(float deltaTime) {
		if (attachedObj != null) {
			setPosition(attachedObj.getX() + objOffsetX, attachedObj.getY() + objOffsetY);
		}

		if (loopPosition > 0) {
			for (ParticleEmitter emitter : getEmitters()) {
				if (emitter.durationTimer > loopPosition) {
					emitter.durationTimer = loopPosition;
				}
			}
		}

		super.update(deltaTime);
	}

	@Override
	public void reset() {
		dispose();
		attachedObj = null;
		loopPosition = 0;
		objOffsetX = objOffsetY = 0;
		setPosition(0, 0);
	}

	public void stop() {
		for (ParticleEmitter emitter : getEmitters()) {
			if (emitter.duration > loopPosition) {
				loopPosition = emitter.duration;
			}
		}
	}
}
