package com.gdxgame.core.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.gdxgame.core.enums.TextureType;

/**
 * AnimatedSprite is a wrapper class for the libgdx Sprite class to encapsulate the setRegion() method of the Sprite and
 * to support texture usage out of a libgdx TextureAtlas.
 * 
 * AnimatedSprite calculates the correct frame size of the texture by passing the texture amount of animations per
 * column and per row. It also finds out the correct position of the texture within a texture atlas texture.
 * 
 * The class supports functionality to move the frame around within the texture.
 * 
 */
public class AnimatedSprite extends Sprite {
	/**
	 * Texture offset x to the top left corner of the texture within the TextureAtlas texture
	 */
	private float	texOffsetX;
	/**
	 * Texture offset y to the top left corner of the texture within the TextureAtlas texture
	 */
	private float	texOffsetY;
	/**
	 * x position of the top left corner of the frame
	 */
	private int		frameX;
	/**
	 * y position of the top left corner of the frame
	 */
	private int		frameY;
	/**
	 * width of the top left corner of the frame
	 */
	private int		frameWidth;
	/**
	 * height of the frame
	 */
	private int		frameHeight;
	/**
	 * flipx value of the libgdx Sprite. store it separately because it seems like
	 * it gets automatically reset after a call to setRegion()
	 */
	private boolean	isFlippedX;
	/**
	 * flipx value of the libgdx Sprite. store it separately because it seems like
	 * it gets automatically reset after a call to setRegion()
	 */
	private boolean	isFlippedY;

	public AnimatedSprite(Texture texture, TextureType textureType) {
		super(texture);

		this.frameWidth = texture.getWidth() / textureType.getAnimationsX();
		this.frameHeight = texture.getHeight() / textureType.getAnimationsY();

		texOffsetX = texOffsetY = 0;

		initialize();
	}

	public AnimatedSprite(AtlasRegion atlasRegion, TextureType textureType) {
		super(atlasRegion.getTexture());

		this.frameWidth = atlasRegion.originalWidth / textureType.getAnimationsX();
		this.frameHeight = atlasRegion.originalHeight / textureType.getAnimationsY();

		this.texOffsetX = atlasRegion.getRegionX();
		this.texOffsetY = atlasRegion.getRegionY();

		initialize();
	}

	/**
	 * Initializes a new AnimatedSprite by setting the correct size and origin point
	 * of the libgdx Sprite instance. 
	 * Also sets the initial frame index to the top left corner of the texture.
	 */
	private void initialize() {
		isFlippedX = isFlippedY = false;

		setSize(frameWidth, frameHeight);
		setOrigin(frameWidth * 0.5f, frameHeight * 0.5f);
		setFrameIndex(0, 0);
	}

	/**
	 * Moves the frame to the specified position.
	 * X = 0 and Y = 0 is the top left corner while
	 * X = (animations per row-1) and Y = (animations per column-1) is the bottom right corner.  
	 * 
	 * @param x x index of the new frame
	 * @param y y index of the new frame
	 */
	public void setFrameIndex(int x, int y) {
		// calculate the correct frame position
		frameX = (int) (texOffsetX + x * frameWidth);
		frameY = (int) (texOffsetY + y * frameHeight);
		// make a call to the libgdx Sprite setRegion()
		// and flip() method. SetRegion() resets the flip values
		// therefore we store it separately
		this.setRegion(frameX, frameY, frameWidth, frameHeight);
		this.flip(isFlippedX, isFlippedY);
	}

	/**
	 * Similar like {@link com.badlogic.gdx.graphics.g2d.Sprite#setFlip(boolean, boolean)} but it
	 * additionally sets the internal flip values of the AnimatedSprite class
	 */
	@Override
	public void setFlip(boolean flipX, boolean flipY) {
		super.setFlip(flipX, flipY);
		isFlippedX = flipX;
		isFlippedY = flipY;
	}

	/**
	 * Similar like {@link com.badlogic.gdx.graphics.g2d.Sprite#flip(boolean, boolean)} but it
	 * additionally sets the internal flip values of the AnimatedSprite class
	 */
	@Override
	public void flip(boolean flipX, boolean flipY) {
		super.flip(flipX, flipY);
		isFlippedX = flipX;
		isFlippedY = flipY;
	}
}
