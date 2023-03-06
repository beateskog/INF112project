package dev.mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dev.mavenless.ronasurvivors.RonaSurvivors;
import dev.mavenless.ronasurvivors.Utils.InputUtil;
import dev.mavenless.ronasurvivors.Utils.UiHandler;
import dev.mavenless.ronasurvivors.Utils.UiHandler.UiFont;
import dev.mavenless.ronasurvivors.Utils.UiHandler.UiStyle;

public class MainMenu implements Screen {

    final RonaSurvivors game;

    private ExtendViewport extendViewport;
    private InputUtil inputUtil;

    private Stage stage;
    private Table table;
    private Texture backgroundTexture;

    public MainMenu(final RonaSurvivors game) {
        this.game = game;
        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        inputUtil = new InputUtil();

        backgroundTexture = new Texture(Gdx.files.internal("sprites/menu background.png"));

        stage = new Stage(new ScreenViewport(), this.game.batch);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // table.setDebug(true, true);

        // Widgets
        Gdx.app.debug("Tets", "Widget");

        Table menuTable = new Table();
        // menuTable.setDebug(true);
        Container<Table> wrapper = new Container<Table>(menuTable);
        // wrapper.setDebug(true);
        wrapper.height(500);

        menuTable.row().height(400);

        Table titleTable = new Table();
        // titleTable.setDebug(true);
        Image image = new Image(new Texture(Gdx.files.internal("icons/icon.png")));
        titleTable.add(image).size(200);
        titleTable.row();

        Label label = new Label("Rona Survivors v0.0.2",
                UiHandler.labelStyle(UiHandler.UiFont.Pixelated.getFontBorder(20, Color.WHITE, Color.BLACK, 5)));
        // label.scaleBy(20);
        titleTable.add(label).spaceTop(20);

        menuTable.add(titleTable).top();
        menuTable.row();

        // Buttons
        TextButtonStyle textButtonStyle = UiHandler.textButtonStyle(UiStyle.Debug,
                UiFont.Pixelated.getFont(20, Color.WHITE));

                
        final TextButton button3 = new TextButton("Start Game", textButtonStyle);
        menuTable.add(button3).minWidth(200).spaceBottom(20);
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "Button 2: " + button3.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        
        menuTable.row();
        
        

        final TextButton button2 = new TextButton("Shop", textButtonStyle);
        menuTable.add(button2).minWidth(200).spaceBottom(20);
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "Button 2: " + button2.isChecked());
                game.setScreen(new Shop(game));
                dispose();
            }
        });

        menuTable.row();

        TextButton button1 = new TextButton("Exit", textButtonStyle);
        menuTable.add(button1).minWidth(200);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "Button 1");
                Gdx.app.exit();
            }
        });

        table.add(wrapper);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.RED);

        // Input
        inputUtil.update();
        if (inputUtil.down("select")) Gdx.app.debug("Controller", "Down");
        if (inputUtil.up("select")) Gdx.app.debug("Controller", "Up");

        // Background
        extendViewport.apply();
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        game.batch.begin();
        // game.batch.draw(backgroundTexture, extendViewport.getCamera().position.x -
        // Gdx.graphics.getWidth() / 2f,
        // extendViewport.getCamera().position.y - Gdx.graphics.getHeight() / 2f,
        // Gdx.graphics.getWidth(),
        // Gdx.graphics.getHeight());
        game.batch.draw(backgroundTexture, extendViewport.getCamera().position.x - Gdx.graphics.getWidth() / 2f,
                extendViewport.getCamera().position.y - Gdx.graphics.getHeight() / 2f);
        game.batch.end();

        // Stage
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.app.debug("App", "Hidden");
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }

}