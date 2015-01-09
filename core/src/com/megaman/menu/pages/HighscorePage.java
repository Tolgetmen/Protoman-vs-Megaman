package com.megaman.menu.pages;

import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxgame.core.GDXGame;
import com.gdxgame.core.GameMenu;
import com.gdxgame.core.GameMenuPage;
import com.gdxgame.core.GameStateLogic;
import com.gdxgame.core.enums.GameMenuPageType;
import com.gdxgame.core.enums.GameStateType;
import com.gdxgame.core.enums.MusicType;
import com.gdxgame.core.enums.SkinType;
import com.gdxgame.core.utils.GameUtils;
import com.gdxgame.core.utils.ResourceManager;
import com.gdxgame.core.utils.SoundManager;
import com.megaman.menu.HighscoreMenu;

public class HighscorePage extends GameMenuPage implements OnCompletionListener {
	private final int				OPTION_RETRY	= 0;
	private final int				OPTION_QUIT		= 1;

	private BitmapFont				font;
	private Map<String, Integer>	highscore;

	public HighscorePage(GameMenuPageType type, GameMenu gameMenu, GDXGame game, GameStateLogic logic) {
		super(type, gameMenu, game, logic);
		font = ResourceManager.INSTANCE.getSkin(SkinType.SKIN_MAIN_MENU).getFont("minecraft_31");
	}

	@SuppressWarnings("unchecked")
	public void setHighscore(Object highscore) {
		this.highscore = (Map<String, Integer>) highscore;
	}

	@Override
	public void initialize() {
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.retry"), true, 490, 0, 20, 0);
		addOption(GameUtils.getLocalizedLabel("MainMenu.option.quit"), true, 0, 0, 0, 0);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		super.render(spriteBatch);

		spriteBatch.begin();
		// highscore labels
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.blocked.total"), 150, 405);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.blocked.normal"), 180, 370);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.blocked.boss"), 180, 335);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.leaked"), 150, 290);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.life"), 150, 245);
		font.draw(spriteBatch, GameUtils.getLocalizedLabel("HighScore.points"), 150, 170);

		font.draw(spriteBatch, "--------------------", 150, 210);

		// highscore resultes
		font.draw(spriteBatch, "" + highscore.get("blocked"), 520, 405);
		font.draw(spriteBatch, "" + highscore.get("blocked_normal"), 520, 370);
		font.draw(spriteBatch, "" + highscore.get("blocked_boss"), 520, 335);
		font.draw(spriteBatch, "" + highscore.get("leaked"), 520, 290);
		font.draw(spriteBatch, "" + highscore.get("life"), 520, 245);
		font.draw(spriteBatch, "" + highscore.get("points"), 520, 170);
		spriteBatch.end();
	}

	@Override
	public void processSelection(int optionIndex) {
		switch (optionIndex) {
			case OPTION_RETRY: {
				game.setGameState(GameStateType.GAME, true, true, null);
				break;
			}
			case OPTION_QUIT: {
				SoundManager.INSTANCE.playMusic(MusicType.MENU_QUIT, false, this);
				((HighscoreMenu) gameMenu).disableControls();
				break;
			}
		}
	}

	@Override
	public void onCompletion(Music music) {
		// wait until protoman music was played to close the game
		game.setGameState(null, true, true, null);
	}
}
