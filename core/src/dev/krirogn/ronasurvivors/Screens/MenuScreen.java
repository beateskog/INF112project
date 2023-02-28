package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dev.krirogn.ronasurvivors.RonaSurvivors;
import dev.krirogn.ronasurvivors.Utils.InputUtil;
import dev.krirogn.ronasurvivors.Utils.UiHandler;
import dev.krirogn.ronasurvivors.Utils.UiHandler.UiFont;

public class MenuScreen implements Screen {

    final RonaSurvivors game;

    private InputUtil inputUtil;

    private ExtendViewport extendViewport;
    private Stage stage;

    private Texture backgroundImage;

    public MenuScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        stage = new Stage(new ScreenViewport());

        backgroundImage = new Texture(Gdx.files.internal("sprites/bg.jpg"));

        // Input
        inputUtil = new InputUtil();
        Gdx.input.setInputProcessor(stage);

        uiSetup();
    }

    private void uiSetup() {
        Container<Table> root = new Container<Table>();
        root.debugAll();
        root.setFillParent(true);
        stage.addActor(root);

        Table menu = new Table();
        root.setActor(menu);

        Image image = new Image(new Texture(Gdx.files.internal("icons/icon.png")));
        float s = Gdx.graphics.getHeight() * 0.2f;
        menu.add(image).prefWidth(s).prefHeight(s).fillY().align(Align.top).row();

        Label label = new Label(
            "Rona Survivors",
            UiHandler.labelStyle(
                UiFont.Pixelated.getFontBorder(
                    20,
                    Color.WHITE,
                    Color.BLACK,
                    3
                )
            )
        );
        menu.add(label).fillY().align(Align.bottom);

        // Skin skin = UiHandler.UiStyle.Debug.getSkin();
        // Container<Table> tableContainer = new Container<Table>();

        // float sw = Gdx.graphics.getWidth();
        // float sh = Gdx.graphics.getHeight();

        // float cw = sw * 0.7f;
        // float ch = sh * 0.5f;

        // tableContainer.setSize(cw, ch);
        // tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
        // tableContainer.fillX();

        // Table table = new Table(skin);

        // Label topLabel = new Label("A LABEL", skin);
        // topLabel.setAlignment(Align.center);
        // Slider slider = new Slider(0, 100, 1, false, skin);
        // Label anotherLabel = new Label("ANOTHER LABEL", skin);
        // anotherLabel.setAlignment(Align.center);

        // CheckBox checkBoxA = new CheckBox("Checkbox Left", skin);
        // CheckBox checkBoxB = new CheckBox("Checkbox Center", skin);
        // CheckBox checkBoxC = new CheckBox("Checkbox Right", skin);

        // Table buttonTable = new Table(skin);

        // TextButton buttonA = new TextButton("LEFT", skin);
        // TextButton buttonB = new TextButton("RIGHT", skin);

        // table.row().colspan(3).expandX().fillX();
        // table.add(topLabel).fillX();
        // table.row().colspan(3).expandX().fillX();
        // table.add(slider).fillX();
        // table.row().colspan(3).expandX().fillX();
        // table.add(anotherLabel).fillX();
        // table.row().expandX().fillX();

        // table.add(checkBoxA).expandX().fillX();
        // table.add(checkBoxB).expandX().fillX();
        // table.add(checkBoxC).expandX().fillX();
        // table.row().expandX().fillX();;

        // table.add(buttonTable).colspan(3);

        // buttonTable.pad(16);
        // buttonTable.row().fillX().expandX();
        // buttonTable.add(buttonA).width(cw/3.0f);
        // buttonTable.add(buttonB).width(cw/3.0f);

        // tableContainer.setActor(table);
        // stage.addActor(tableContainer);
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
        game.batch.draw(
            backgroundImage,
            extendViewport.getCamera().position.x - Gdx.graphics.getWidth() / 2f,
            extendViewport.getCamera().position.y - Gdx.graphics.getHeight() / 2f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
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
