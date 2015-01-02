package com.megaman.menu;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.core.GameLogic;
import com.megaman.core.enums.GameMenuPageType;
import com.megaman.core.enums.SkinType;
import com.megaman.core.utils.ResourceManager;

public class HighscoreMenu extends MegamanMenu {
	private BitmapFont				font;
	private Map<String, Integer>	highscore;

	@SuppressWarnings("unchecked")
	public HighscoreMenu(GameLogic logic, int menuWidth, int menuHeight, boolean stretch, GameMenuPageType startPage, Object highscore) {
		super(logic, menuWidth, menuHeight, stretch, startPage);

		font = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU).getFont("8bit_25");
		this.highscore = (Map<String, Integer>) highscore;
	}

	@Override
	public void render(SpriteBatch spriteBatch, Camera camera) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		super.render();

		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		font.draw(spriteBatch, "" + highscore.get("blocked"), 500, 600 - 201 + 25);
		font.draw(spriteBatch, "" + highscore.get("blocked_normal"), 500, 600 - 230 + 25);
		font.draw(spriteBatch, "" + highscore.get("blocked_boss"), 500, 600 - 264 + 25);
		font.draw(spriteBatch, "" + highscore.get("leaked"), 500, 600 - 297 + 25);
		font.draw(spriteBatch, "" + highscore.get("life"), 500, 600 - 345 + 25);
		font.draw(spriteBatch, "" + highscore.get("points"), 500, 600 - 440 + 25);

		spriteBatch.draw(megaman, getCurrentOption().getX() - megaman.getWidth() - 40, getCurrentOption().getY(), megaman.getOriginX(), megaman.getOriginY(), megaman.getWidth(), megaman.getHeight(), megaman.getScaleX(), megaman.getScaleY(), megaman.getRotation());
		spriteBatch.draw(protoman, getCurrentOption().getX() + getCurrentOption().getWidth() + 40, getCurrentOption().getY() - 10, protoman.getOriginX(), protoman.getOriginY(), protoman.getWidth(), protoman.getHeight(), protoman.getScaleX(), protoman.getScaleY(), protoman.getRotation());
		spriteBatch.draw(missile, missileX, getCurrentOption().getY() + 4, missile.getOriginX(), missile.getOriginY(), missile.getWidth(), missile.getHeight(), missile.getScaleX(), missile.getScaleY(), missile.getRotation());
		spriteBatch.end();
	}
}
