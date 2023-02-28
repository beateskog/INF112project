package dev.krirogn.ronasurvivors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.krirogn.ronasurvivors.Screens.GameScreen;
import dev.krirogn.ronasurvivors.Screens.MenuScreen;
import dev.krirogn.ronasurvivors.Utils.InputUtil;

public class RonaSurvivors extends Game {

	public SpriteBatch batch;
    public BitmapFont font;
	public InputUtil input;

	@Override
	public void create() {
		// Debvelopment config
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
        font = new BitmapFont();
		input = new InputUtil();
    	this.setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		// Polls input system
		input.update();

		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
        font.dispose();
	}

}
