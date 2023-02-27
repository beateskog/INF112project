package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dev.krirogn.ronasurvivors.RonaSurvivors;
import dev.krirogn.ronasurvivors.Utils.InputUtil;

public class MenuScreen implements Screen {

    final RonaSurvivors game;

    private ExtendViewport extendViewport;
    private InputUtil inputUtil;

    private Stage stage;

    public MenuScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        stage = new Stage(new ScreenViewport());

        // Input
        inputUtil = new InputUtil();
        Gdx.input.setInputProcessor(stage);

        uiSetup();
    }

    private void uiSetup() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        Image background = new Image(new Texture(Gdx.files.internal("sprites/menu background.png")));
        stack.add(background);

        Table table = new Table();
        stack.add(table);

        Image image = new Image(new Texture(Gdx.files.internal("icons/icon.png")));
        table.add(image);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        // Input
        inputUtil.update();

        // Sprites
        extendViewport.apply();
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        game.batch.begin();
        
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
