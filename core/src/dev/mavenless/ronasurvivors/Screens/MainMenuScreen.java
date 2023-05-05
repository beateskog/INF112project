package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private Label header = new Label("Rona Survivors", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

    public MainMenuScreen(RonaSurvivors game){
        this.game = game;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/Menu/MainMenuScreen.json"));
        Gdx.input.setInputProcessor(stage);
        
        header.setFontScale(8f, 5f);
        
        Table table = new Table();
        table.setBackground(skin.getDrawable("Group 1"));
        table.setFillParent(true);

        
        table.add(startButton());
        table.row();
        table.add(instructionsButton());
        table.row();
        table.add(exitButton());
        
        stage.addActor(table);
    }
    
    private TextButton startButton(){
        TextButton textButton1 = new TextButton(null, skin);
        textButton1.setText("START GAME");
        textButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "startButton: " + textButton1.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        return textButton1;
    }
    
    private TextButton instructionsButton(){
        TextButton textButton2 = new TextButton(null, skin);
        textButton2.setText("INSTRUCTIONS");
        textButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "instructionsButton: " + textButton2.isChecked());
                game.setScreen(new InstructionsScreen(game));
                dispose();
            }
        });
        return textButton2;
    }
    
    private TextButton exitButton(){
        TextButton textButton3 = new TextButton(null, skin);
        textButton3.setText("EXIT");
        textButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        return textButton3;
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.addActor(header);
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        header.setPosition(stage.getWidth() / 4 - header.getWidth() / 2, stage.getHeight() - header.getHeight() * 10);
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
