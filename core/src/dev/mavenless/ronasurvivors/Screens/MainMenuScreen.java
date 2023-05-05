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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mavenless.ronasurvivors.RonaSurvivors;
import mavenless.ronasurvivors.Utils.UiHandler;

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

        Stack stack = new Stack();
        BitmapFont f1 = UiHandler.UiFont.Pixelated.getFont(10, Color.BLACK);
        BitmapFont f2 = UiHandler.UiFont.Pixelated.getFont(10, Color.WHITE);
        LabelStyle style1 = new LabelStyle(f1, Color.BLACK);
        LabelStyle style2 = new LabelStyle(f2, Color.WHITE);

        Label bigLabel1 = new Label("Rona Survivors", skin);
        bigLabel1.setStyle(style1);
        bigLabel1.setFontScale(7f);
        Label smallLabel1 = new Label("Rona Survivors", skin);
        smallLabel1.setStyle(style2);
        smallLabel1.setFontScale(6.9f);

        stack.add(smallLabel1);
        stack.add(bigLabel1);
        table.add(stack);
        
        table.row();
        table.add(startButton());
        table.row();
        table.add(instructionsButton());
        table.row();
        table.add(exitButton());
        
        stage.addActor(table);
    }
    
    private TextButton startButton() {
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
    
    private TextButton instructionsButton() {
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
    
    private TextButton exitButton() {
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
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }
}
