package mavenless.ronasurvivors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mavenless.ronasurvivors.Screens.MainMenuScreen;
import mavenless.ronasurvivors.Utils.InputUtil;

public class RonaSurvivors extends Game {

	public SpriteBatch batch;
	public InputUtil input;

	@Override
	public void create() {
		// Debvelopment config
		Gdx.app.setLogLevel(Application.LOG_NONE);

		batch = new SpriteBatch();
		input = new InputUtil();
    	this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		// Polls input system
		input.update();

		super.render();
	}

	@Override
	public void pause(){
	}

	@Override
	public void resume(){
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
