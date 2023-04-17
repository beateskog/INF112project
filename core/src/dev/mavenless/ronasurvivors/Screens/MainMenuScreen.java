package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mavenless.ronasurvivors.RonaSurvivors;

public class MainMenuScreen implements Screen {
    
    private Skin skin;
    private Stage stage;
    private RonaSurvivors game;

    public MainMenuScreen(RonaSurvivors game){
        this.game = game;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/Menu/MainMenuScreen.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setBackground(skin.getDrawable("Group 1"));
        table.setFillParent(true);

        TextButton textButton1 = new TextButton(null, skin);
        textButton1.setText("START GAME");
        table.add(textButton1);
        textButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "textButton1: " + textButton1.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        table.row();
        TextButton textButton2 = new TextButton(null, skin);
        textButton2.setText("SETTINGS");
        table.add(textButton2);

        table.row();
        TextButton textButton3 = new TextButton(null, skin);
        textButton3.setText("EXIT");
        table.add(textButton3);
        stage.addActor(table);
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {
        Gdx.app.debug("App", "Hidden");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void show() {
    }
}
