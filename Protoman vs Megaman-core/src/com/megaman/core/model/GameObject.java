package com.megaman.core.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.megaman.gamestates.logic.GSGameLogic;

public abstract class GameObject {
	protected String			name;
	protected Vector2			position;
	protected float				width;
	protected float				height;
	private float				transparency;
	protected Rectangle			bounds;
	protected final GSGameLogic	gameLogic;

	public GameObject(GSGameLogic gameLogic) {
		position = new Vector2();
		this.gameLogic = gameLogic;
	}

	public abstract void update(float deltaTime);

	public float getX() {
		return position.x;
	}

	public void setX(float x) {
		position.x = x;
	}

	public float getY() {
		return position.y;
	}

	public void setY(float y) {
		position.y = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Rectangle getBoundingRectangle() {
		if (bounds == null)
			bounds = new Rectangle();
		bounds.x = position.x;
		bounds.y = position.y;
		bounds.width = width;
		bounds.height = height;
		return bounds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getTransparency() {
		return transparency;
	}

	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	public GSGameLogic getGameLogic() {
		return gameLogic;
	}
}
