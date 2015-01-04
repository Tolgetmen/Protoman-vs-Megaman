package com.megaman.core.model;

import com.megaman.core.GameStateLogic;
import com.megaman.core.enums.TextureType;

/**
 * 
 * AnimatedGameObject is one of the core classes for game objects. It is used to define a link between a
 * game object and an AnimatedSprite.
 *  
 * Similar to the AnimatedSprite class it contains functionality to move around a frame for any texture. The
 * information of the AnimatedGameObject's frame can then be used to set the correct frame index for the
 * AnimatedSprite.
 * 
 * Additionally it contains functionality to fadeout/-in game objects over a specified time.
 * 
 * AnimatedGameObject should always be linked with an AnimatedSprite. Otherwise use the GameObject class instead.
 * 
 * check GameUtils.renderGameObject method to see how the AnimatedGameObject and AnimatedSprite are linked before rendering
 * 
 */
public abstract class AnimatedGameObject extends GameObject {
	/**
	 * the time how long an animated game object displays a single animation(=frame)
	 */
	private float	animationTime;
	/**
	 * number of animations in a row
	 */
	private int		animationsX;
	/**
	 * number of animations in a column
	 */
	private int		animationsY;
	/**
	 * the time how long the animated game object is in a specific animation
	 */
	private float	currentAnimationTime;
	/**
	 * animation start index x
	 */
	private int		animationStartX;
	/**
	 * animation start index y
	 */
	private int		animationStartY;
	/**
	 * animation end index x
	 */
	private int		animationEndX;
	/**
	 * animation end index y
	 */
	private int		animationEndY;
	/**
	 * current animation index x
	 */
	private int		currentX;
	/**
	 * current animation index y
	 */
	private int		currentY;
	/**
	 * should the animation be stopped?
	 * if stop is true then the animated game object will no longer update its frame
	 */
	private boolean	stop;
	/**
	 * how many times should an animation be displayed?
	 * if loop is false then the animation is only displayed once and will then stop
	 * at the last frame index
	 */
	private boolean	loop;
	/**
	 * should the AnimatedSprite be flipped horicontally
	 */
	private boolean	flipX;
	/**
	 * should the AnimatedSprite be flipped vertically
	 */
	private boolean	flipY;
	/**
	 * current transparency of the animated game object
	 */
	private float	transparency;
	/**
	 * whenever the fadeTo() method is called a value is calculated how much
	 * the transparency should be changed each frame to reach the correct
	 * targetTransparency within a certain time
	 */
	private float	transparencyGainPerSecond;
	/**
	 * target transparency of the fadeTo() method call
	 */
	private float	targetTransparency;

	public AnimatedGameObject(GameStateLogic logic, TextureType textureType, int animationsPerSecond) {
		super(logic);

		// calculate the time that needs to be passed until updating to the next frame
		this.animationTime = 1.0f / animationsPerSecond;

		if (textureType != null) {
			this.animationsX = textureType.getNumColumns();
			this.animationsY = textureType.getNumRows();
		} else {
			animationsX = animationsY = 1;
		}

		// start from the first frame index (=top left corner)
		this.animationStartY = this.animationStartX = 0;
		// stop at the last frame index (=bottom right corner)
		this.animationEndY = this.animationEndX = (animationsX * animationsY) - 1;

		transparency = targetTransparency = transparencyGainPerSecond = 0;
		loop = true;
		stop = false;
	}

	/**
	 * Updates the animation and transparency of an animated game object. 
	 * This will change the current frame index to the next whenever the currentAnimationTime
	 * is greater than the animationTime;
	 * 
	 *  @param deltaTime time passed since last frame
	 */
	@Override
	public void update(float deltaTime) {
		// check if transparency needs to be updated
		float currentTransparency = getTransparency();
		if (currentTransparency != targetTransparency) {
			// if yes -> update it
			currentTransparency += (transparencyGainPerSecond * deltaTime);

			if ((transparencyGainPerSecond < 0 && currentTransparency < targetTransparency) || (transparencyGainPerSecond > 0 && currentTransparency > targetTransparency)) {
				// if target transparency is reached then set it exactly to the target transparency value
				currentTransparency = targetTransparency;
			}

			// update the transparency
			setTransparency(currentTransparency);
		}

		// check if there is an animation possible
		if ((animationsY > 1 || animationsX > 1) && animationTime > 0 && !stop) {
			// if yes -> update the current animation time
			currentAnimationTime += deltaTime;

			if (currentAnimationTime < 0) {
				// this should not happen because deltaTime should always
				// be greater zero. But to be safe we check for such a case
				currentAnimationTime = 0;
			}

			if (currentAnimationTime >= animationTime) {
				// if time passed >= time to display one animation then move to the next animation
				if (currentX >= animationEndX && currentY >= animationEndY) {
					if (loop) {
						// if we are at the end of the animation then reset it to the start of
						// the animation loop if "loop" is true
						currentX = animationStartX;
						currentY = animationStartY;
					} else {
						// otherwise stop the animation because we have reached the end
						stop = true;
					}
				} else {
					// otherwise move one animation frame further
					++currentX;
					if (currentX >= animationsX) {
						// if we are at the end of the current animation row then
						// move to the next row and start with the first animation there
						currentX = 0;
						++currentY;
						if (currentY >= animationsY) {
							if (loop) {
								// if we are at the end of the animation then reset it to the start of
								// the animation loop if "loop" is true
								currentX = animationStartX;
								currentY = animationStartY;
							} else {
								// otherwise stop the animation because we have reached the end
								currentX = animationsX - 1;
								currentY--;
								stop = true;
							}
						}
					}
				}

				// reset the current animation time for the new frame
				currentAnimationTime = 0;
			}
		}
	}

	/**
	 * sets new amount of animations to be displayed per second
	 * 
	 * @param animationsPerSecond number of animations displayed per second
	 */
	public void setAnimationPerSecond(int animationsPerSecond) {
		this.animationTime = 1.0f / animationsPerSecond;
	}

	/**
	 * sets new texture type for the animated game object. this will update the number of animations
	 * per row and number of animations per column of the animated game object.
	 * 
	 * @param textureType new type of texture for animated game object
	 */
	public void setTextureType(TextureType textureType) {
		this.animationsY = textureType.getNumRows();
		this.animationEndY = this.animationEndX = (animationsX * animationsY) - 1;

		this.animationsX = textureType.getNumColumns();
		this.animationEndY = this.animationEndX = (animationsX * animationsY) - 1;
	}

	/**
	 * sets frame to the given animation. The first animation has index 0
	 * while the last animation has index ((animations per row * animations per column) - 1).
	 * 
	 * @param animation index of animation to be set
	 */
	public void setAnimation(int animation) {
		animationStartY = animation / animationsX;
		animationStartX = animation % animationsX;

		animationEndY = animationStartY;
		animationEndX = animationStartX;

		currentY = animationStartY;
		currentX = animationStartX;
	}

	/**
	 * sets start and end index of animations that should be displayed. If your sprite
	 * f.e. has 5 animations and you only want to display animation 1 to 3 then you
	 * can use this method. StartAnimation parameter would be 0 and endAnimation 2. 
	 * 
	 * A call to this method automatically unpauses the animation.
	 * 
	 * @param startAnimation animation index to start
	 * @param endAnimation animation index to end
	 */
	public void setLoopAnimations(int startAnimation, int endAnimation) {
		stop = false;

		int newAnimationStartRow = startAnimation / animationsX;
		int newAnimationStartColumn = startAnimation % animationsX;

		int newAnimationEndRow = endAnimation / animationsX;
		int newAnimationEndColumn = endAnimation % animationsX;

		animationStartY = newAnimationStartRow;
		animationStartX = newAnimationStartColumn;

		animationEndY = newAnimationEndRow;
		animationEndX = newAnimationEndColumn;

		currentY = animationStartY;
		currentX = animationStartX;
	}

	/**
	 * starts the animation from the current frame
	 */
	public void startAnimation() {
		stop = false;
	}

	/**
	 * stops the animation at the current frame
	 */
	public void stopAnimation() {
		stop = true;
	}

	/**
	 * defines if an animation should loop or not. If it does not loop
	 * then the animation will stop at the last animation index
	 * 
	 * @param loop <b>true</b> to start an animation loop. <b>false</b> to stop the animation at the end at the last animation index.
	 */
	public void loopAnimation(boolean loop) {
		this.loop = loop;
	}

	/**
	 * returns current animation frame index x of the animated game object
	 * 
	 * @return frame index x
	 */
	public int getFrameIndexX() {
		return currentX;
	}

	/**
	 * returns current animation frame index y of the animated game object
	 * 
	 * @return frame index y
	 */
	public int getFrameIndexY() {
		return currentY;
	}

	/**
	 * returns current animation index of the animated game object
	 * 
	 * @return animation index
	 */
	public int getCurrentAnimation() {
		return (animationsX * currentY) + currentX;
	}

	/**
	 * returns current transparency of the animated game object
	 * 
	 * @return transparency of animated game object
	 */
	public float getTransparency() {
		return transparency;
	}

	/**
	 * sets transparency of the animated game object. A value of <b>1</b>
	 * means that the animated game object will be invisible. A value of <b>0</b>
	 * means fully opaque.
	 * 
	 * @param transparency new transparency value. Must be between <b>0</b> and <b>1</b>
	 */
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	/**
	 * changes animated game object's transparency to the given targetTransparency within a certain time.
	 * 
	 * @param targetTransparency target transparency of animated game object
	 * @param time time in seconds until animated game object reaches target transparency
	 */
	public void fadeTo(float targetTransparency, float time) {
		float currentTransparency = getTransparency();
		float difference = targetTransparency - currentTransparency;

		if (difference != 0) {
			this.targetTransparency = targetTransparency;
			transparencyGainPerSecond = difference / time;
		}
	}

	/**
	 * flip animation by its x-axis and/or y-axis
	 * 
	 * @param flipX <b>true</b> to flip animation by its x-axis.<b>false</b> otherwise
	 * @param flipY <b>true</b> to flip animation by its y-axis.<b>false</b> otherwise
	 */
	public void flip(boolean flipX, boolean flipY) {
		this.flipX = flipX;
		this.flipY = flipY;
	}

	/**
	 * returns if animated game object is flipped by its x-axis
	 * 
	 * @return <b>true</b> if animated game object is flipped by its x-axis. <b>false</b> otherwise.
	 */
	public boolean isFlipX() {
		return flipX;
	}

	/**
	 * returns if animated game object is flipped by its y-axis
	 * 
	 * @return <b>true</b> if animated game object is flipped by its y-axis. <b>false</b> otherwise.
	 */
	public boolean isFlipY() {
		return flipY;
	}
}
