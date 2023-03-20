package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Skin skin;

    private Stage stage;

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        ImageButton imageButton = new ImageButton(skin, "doctorImageButton");
        table.add(imageButton);

        imageButton = new ImageButton(skin, "doctorImageButton");
        table.add(imageButton);

        ImageTextButton imageTextButton = new ImageTextButton(null, skin, "Anti-Vaxxer");
        table.add(imageTextButton);

        table.row();
        imageButton = new ImageButton(skin, "doctorImageButton");
        table.add(imageButton);

        imageTextButton = new ImageTextButton(null, skin);
        table.add(imageTextButton);

        imageTextButton = new ImageTextButton(null, skin, "PlagueDoctor");
        table.add(imageTextButton);

        table.row();
        table.add();

        Button button = new Button(skin);
        table.add(button);

        table.add();
        stage.addActor(table);
    }

    public void render() {
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
}
