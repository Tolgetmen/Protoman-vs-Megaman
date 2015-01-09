package com.gdxgame.core.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdxgame.core.GameStateLogic;

/**
 * 
 * GameObject is one of the core classes for game objects. Each GameObject instance has a position
 * in the game world and a size. It also has a bounding rectangle to check for collisions.
 * 
 * It is not necessarily linked with an AnimatedSprite. For that purpose use the AnimatedGameObject class.
 * 
 * A GameObject instance always holds a reference to the GameStateLogic in which it was created.
 * 
 */
public abstract class GameObject {
	/**
	 * position in the game world
	 */
	private Vector2					position;
	/**
	 * width of the game object
	 */
	private float					width;
	/**
	 * height of the game object
	 */
	private float					height;
	/**
	 * bounding rectangle of the game object defined by its position, width and height
	 */
	private Rectangle				bounds;
	/**
	 * reference to the GameStateLogic in which the game object was created
	 */
	protected final GameStateLogic	logic;

	public GameObject(GameStateLogic logic) {
		position = new Vector2();
		this.logic = logic;
	}

	/**
	 * Each GameObject instance has to implement an update method in order to update the
	 * specific game object logic. 
	 * This method should be called within the update() method of the GameStateLogic
	 * 
	 * @param deltaTime time between previous frame and current frame
	 */
	public abstract void update(float deltaTime);

	/**
	 * returns x position of the game object
	 * 
	 * @return x position of game object
	 */
	public float getX() {
		return position.x;
	}

	/**
	 * sets x position of the game object
	 * 
	 * @param x new x position of game object
	 */
	public void setX(float x) {
		position.x = x;
	}

	/**
	 * returns y position of the game object
	 * 
	 * @return y position of game object
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * sets y position of the game object
	 * 
	 * @param y new y position of game object
	 */
	public void setY(float y) {
		position.y = y;
	}

	/**
	 * sets x and y position of the game object
	 * 
	 * @param x new x position of game object
	 * @param y new y position of game object
	 */
	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	/**
	 * returns width of the game object
	 * 
	 * @return current width of game object
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * sets width of the game object 
	 * 
	 * @param width new width of game object
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * returns height of the game object
	 * 
	 * @return current height of game object
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * sets height of the game object
	 * 
	 * @param height new height of game object
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * sets width and height of the game object
	 * 
	 * @param width new width of game object
	 * @param height new height of game object
	 */
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * returns current bounding rectangle of the game object. The
	 * rectangle is defined by the game object's position, width and height
	 * 
	 * @return bounding rectangle of game object
	 */
	public Rectangle getBoundingRectangle() {
		if (bounds == null) {
			// method is called for the first time
			// create a new rectangle instance for later usage instead
			// of always creating a new instance. This way we will have
			// a better performance during the update() calls of the GameStateLogic
			// because the gc does not need to clean up the instance all the time
			bounds = new Rectangle();
		}

		bounds.x = position.x;
		bounds.y = position.y;
		bounds.width = width;
		bounds.height = height;

		return bounds;
	}
}
