package com.gdxgame.core;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * GameInputAdapter is the core class for input handling. It extends the libgdx InputAdapter to be able
 * to listen to keyboard, mouse and touch events. It also implements the ControllerListener interface
 * to be able to listen to Controller events.
 * 
 * Use the libgdx Keys class to compare they keycode parameter of the InputAdapter with the actual
 * keys on the keyboard.
 * 
 * Use the libgdx Buttons class to compare the button parameter of the InputAdapter with the actual
 * mouse buttons.
 * 
 * For controllers you need to specifiy your own buttonCode matching since every controller will
 * send different values for the buttonCode parameter.
 *
 */
public abstract class GameInputAdapter extends InputAdapter implements ControllerListener {
	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.connected(com.badlogic.gdx.controllers.Controller)}
	 */
	@Override
	public void connected(Controller controller) {
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.disconnected(com.badlogic.gdx.controllers.Controller)}
	 */
	@Override
	public void disconnected(Controller controller) {
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.buttonDown(com.badlogic.gdx.controllers.Controller, int)}
	 */
	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.buttonUp(com.badlogic.gdx.controllers.Controller, int)}
	 */
	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.axisMoved(com.badlogic.gdx.controllers.Controller, int, float)}
	 */
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.povMoved(com.badlogic.gdx.controllers.Controller, int, com.badlogic.gdx.controllers.PovDirection)}
	 */
	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.xSliderMoved(com.badlogic.gdx.controllers.Controller, int, boolean)}
	 */
	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.ySliderMoved(com.badlogic.gdx.controllers.Controller, int, boolean)}
	 */
	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	/**
	 * see {@link com.badlogic.gdx.controllers.ControllerListener.accelerometerMoved(com.badlogic.gdx.controllers.Controller, int, com.badlogic.gdx.math.Vector3)}
	 */
	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}
}
