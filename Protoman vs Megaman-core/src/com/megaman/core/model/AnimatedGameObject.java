package com.megaman.core.model;

import com.megaman.gamestates.logic.GSGameLogic;

public abstract class AnimatedGameObject extends GameObject {
	// how many animations should be rendered per second
	private float	animationsPerSecond		= 0;
	private int		numColumns				= 1;
	private int		numRows					= 1;
	// some attributes to calculate the correct current animation
	// currentAnimationTime is the time that has passed since the last
	// animation update. If this value is >= animationsPerSecond then
	// the next animation will be displayed
	private float	currentAnimationTime	= 0;
	private int		animationStartRow		= 0;
	private int		animationStartColumn	= 0;
	private int		animationEndRow			= 0;
	private int		animationEndColumn		= 0;
	private int		currentRow				= 0;
	private int		currentColumn			= 0;
	private boolean	isAnimationStopped		= false;
	private boolean	loop					= true;

	public AnimatedGameObject(GSGameLogic gameLogic, int numtAnimationsPerColumn, int numAnimationsPerRow, int animationsPerSecond) {
		super(gameLogic);
		// calculate the time that needs to be passed until updating to the next frame
		this.animationsPerSecond = 1.0f / animationsPerSecond;

		this.numColumns = numtAnimationsPerColumn;
		this.numRows = numAnimationsPerRow;
		this.animationStartRow = this.animationStartColumn = 0;
		this.animationEndRow = this.animationEndColumn = (numColumns * numRows) - 1;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if ((numRows > 1 || numColumns > 1) && animationsPerSecond > 0 && !isAnimationStopped) {
			currentAnimationTime += deltaTime;
			if (currentAnimationTime < 0)
				currentAnimationTime = 0;
			if (currentAnimationTime >= animationsPerSecond) {
				// if time passed >= animationsPer second then render the next animation of the sprite
				if (currentColumn >= animationEndColumn && currentRow >= animationEndRow) {
					// if we are at the end of the animation then reset it to the start of
					// the animation loop if "loop" is true
					if (loop) {
						currentColumn = animationStartColumn;
						currentRow = animationStartRow;
					} else
						isAnimationStopped = true;
				} else {
					// otherwise move one animation frame further
					++currentColumn;
					if (currentColumn >= numColumns) {
						// if we are at the end of the current animation row then
						// move to the next row and start with the first animation there
						currentColumn = 0;
						++currentRow;
						if (currentRow >= numRows) {
							if (loop)
								currentRow = 0;
							else {
								currentColumn = numColumns - 1;
								currentRow--;
								isAnimationStopped = true;
							}
						}
					}
				}

				currentAnimationTime = 0;
			}
		}
	}

	public void stopAnimation() {
		isAnimationStopped = true;
	}

	public void startAnimation() {
		isAnimationStopped = false;
	}

	public void setAnimationPerSecond(int animationsPerSecond) {
		this.animationsPerSecond = 1.0f / animationsPerSecond;
	}

	public void setAnimationsPerRow(int animationsPerRow) {
		this.numRows = animationsPerRow;
		this.animationEndRow = this.animationEndColumn = (numColumns * numRows) - 1;
	}

	public void setAnimationsPerColumn(int animationsPerColumn) {
		this.numColumns = animationsPerColumn;
		this.animationEndRow = this.animationEndColumn = (numColumns * numRows) - 1;
	}

	public void setAnimation(int animation) {
		animationStartRow = animation / numColumns;
		animationStartColumn = animation % numColumns;

		animationEndRow = animationStartRow;
		animationEndColumn = animationStartColumn;

		currentRow = animationStartRow;
		currentColumn = animationStartColumn;
	}

	public void setAnimationIndex(int indexX, int indexY) {
		currentRow = indexY;
		currentColumn = indexX;
	}

	public void setLoopAnimations(int startAnimation, int endAnimation) {
		isAnimationStopped = false;

		int newAnimationStartRow = startAnimation / numColumns;
		int newAnimationStartColumn = startAnimation % numColumns;

		int newAnimationEndRow = endAnimation / numColumns;
		int newAnimationEndColumn = endAnimation % numColumns;

		if (newAnimationStartRow == animationStartRow && newAnimationStartColumn == animationStartColumn && newAnimationEndRow == animationEndRow && newAnimationEndColumn == animationEndColumn)
			return;

		animationStartRow = newAnimationStartRow;
		animationStartColumn = newAnimationStartColumn;

		animationEndRow = newAnimationEndRow;
		animationEndColumn = newAnimationEndColumn;

		currentRow = animationStartRow;
		currentColumn = animationStartColumn;
	}

	public void loopAnimation(boolean loop) {
		this.loop = loop;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public int getCurrentColumn() {
		return currentColumn;
	}

	public int getCurrentAnimation() {
		return (numColumns * currentRow) + currentColumn;
	}
}
