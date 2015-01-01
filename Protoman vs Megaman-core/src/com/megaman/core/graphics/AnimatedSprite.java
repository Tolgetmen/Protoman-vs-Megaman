package com.megaman.core.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimatedSprite extends Sprite {
	// if a sprite is created out of a texture atlas texture we also need to adjust the offset
	// of the atlastexture to get the correct original texture region out of it
	private float	texOffsetX	= 0.0f;
	private float	texOffsetY	= 0.0f;
	// number of animations per row/column to calculate
	// the correct animation frame height/width
	private int		frameX		= 0;
	private int		frameY		= 0;
	private int		frameWidth	= 0;
	private int		frameHeight	= 0;
	// we need to store the flippex values seperately since
	// the "setRegion()" call seems to reset those values
	private boolean	isFlippedX	= false;
	private boolean	isFlippedY	= false;

	public AnimatedSprite(Texture texture, int numColumns, int numRows) {
		super(texture);

		// calculate the correct framewidth/-height
		this.frameWidth = texture.getWidth() / numColumns;
		this.frameHeight = texture.getHeight() / numRows;

		setSize(frameWidth, frameHeight);
		setOrigin(frameWidth * 0.5f, frameHeight * 0.5f);
		setFrameIndex(0, 0);
	}

	public AnimatedSprite(AtlasRegion atlasRegion, int numColumns, int numRows) {
		this(atlasRegion.getTexture(), numColumns, numRows);

		// special adjustments for atlas regions since the original textures are positioned anywhere in the atlas texture
		// we need to define the correct offset and the also get the original texture's width/height instead of the
		// atlas texture's width/height
		this.frameWidth = atlasRegion.originalWidth / numColumns;
		this.frameHeight = atlasRegion.originalHeight / numRows;

		this.texOffsetX = atlasRegion.getRegionX();
		this.texOffsetY = atlasRegion.getRegionY();

		setSize(frameWidth, frameHeight);
		setOrigin(frameWidth * 0.5f, frameHeight * 0.5f);
		setFrameIndex(0, 0);
	}

	public void setFrameIndex(int frameIndexX, int frameIndexY) {
		// set the correct top left corner of the frame
		frameX = (int) (texOffsetX + frameIndexX * frameWidth);
		frameY = (int) (texOffsetY + frameIndexY * frameHeight);

		this.setRegion(frameX, frameY, frameWidth, frameHeight);
		this.flip(isFlippedX, isFlippedY);
	}

	@Override
	public void setFlip(boolean flipX, boolean flipY) {
		super.setFlip(flipX, flipY);
		isFlippedX = flipX;
		isFlippedY = flipY;
	}

	@Override
	public void flip(boolean flipX, boolean flipY) {
		super.flip(flipX, flipY);
		isFlippedX = flipX;
		isFlippedY = flipY;
	}
}
