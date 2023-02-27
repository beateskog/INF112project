package dev.krirogn.ronasurvivors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.krirogn.ronasurvivors.Screens.GameScreen;

public class RonaSurvivors extends Game {

	public SpriteBatch batch;
    public BitmapFont font;

	@Override
	public void create() {
		// Config
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
        font = new BitmapFont();
    	this.setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
        font.dispose();
	}

}
