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

public class InstructionsScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private RonaSurvivors game;

    public InstructionsScreen(RonaSurvivors game) {
        this.game = game;
        create();
    }

    private void create() {
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

        Label bigLabel1 = new Label("Instructions", skin);
        bigLabel1.setStyle(style1);
        bigLabel1.setFontScale(7f);
        Label smallLabel1 = new Label("Instructions", skin);
        smallLabel1.setStyle(style2);
        smallLabel1.setFontScale(6.9f);

        stack.add(smallLabel1);
        stack.add(bigLabel1);
        table.add(stack);

        table.row();

        // Instruction buttons
        table.add(insButton("UP BUTTON:"));
        table.add(insButton("W"));
        table.row();

        table.add(insButton("DOWN BUTTON:"));
        table.add(insButton("S"));
        table.row();

        table.add(insButton("LEFT BUTTON:"));
        table.add(insButton("A"));
        table.row();

        table.add(insButton("RIGHT BUTTON:"));
        table.add(insButton("D"));
        table.row();

        table.add(insButton("QUIT GAME:"));
        table.add(insButton("esc"));
        table.row();

        table.add(backButton());

        stage.addActor(table);
    }

    private TextButton backButton() {
        TextButton back = new TextButton(null, skin);
        back.setText("BACK");
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "back: " + back.isChecked());
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        return back;
    }

    private TextButton insButton(String str) {
        TextButton ins = new TextButton(null, skin);
        ins.setText(str);
        return ins;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

}
