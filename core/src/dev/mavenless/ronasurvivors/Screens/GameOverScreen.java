package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import mavenless.ronasurvivors.RonaSurvivors;
import mavenless.ronasurvivors.Utils.UiHandler;

public class GameOverScreen implements Screen {

    private Skin skin1;
    private Stage stage;
    private RonaSurvivors game;
    private Integer kills;

    public GameOverScreen(RonaSurvivors game, Integer kills) {
        this.game = game;
        this.kills = kills;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin1 = new Skin(Gdx.files.internal("ui/Menu/MainMenuScreen.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.RED);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(
                new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(textureRegionDrawableBg);
        table.setFillParent(true);

        Stack stack1 = new Stack();
        BitmapFont f1 = UiHandler.UiFont.Pixelated.getFont(10, Color.BLACK);
        BitmapFont f2 = UiHandler.UiFont.Pixelated.getFont(10, Color.WHITE);
        LabelStyle style1 = new LabelStyle(f1, Color.BLACK);
        LabelStyle style2 = new LabelStyle(f2, Color.WHITE);

        Label bigLabel1 = new Label("game over", skin1);
        bigLabel1.setStyle(style1);
        bigLabel1.setFontScale(10f);
        Label smallLabel1 = new Label("game over", skin1);
        smallLabel1.setStyle(style2);
        smallLabel1.setFontScale(9.9f);

        stack1.add(smallLabel1);
        stack1.add(bigLabel1);
        table.add(stack1);
        table.row();

        Stack stack2 = new Stack();
        String killString = "Total kills: " + kills.toString();
        Label bigLabel2 = new Label(killString, skin1);
        bigLabel2.setStyle(style1);
        bigLabel2.setFontScale(5f);
        Label smallLabel2 = new Label(killString, skin1);
        smallLabel2.setStyle(style2);
        smallLabel2.setFontScale(4.9f);

        stack2.add(smallLabel2);
        stack2.add(bigLabel2);
        table.add(stack2);

        table.row();
        table.row().padTop(100);

        TextButton textButton1 = new TextButton(null, skin1);
        textButton1.setText("TRY AGAIN");
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

        TextButton textButton3 = new TextButton(null, skin1);
        textButton3.setText("MAIN MENU");
        table.add(textButton3);
        textButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "textButton3: " + textButton3.isChecked());
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        table.row();

        TextButton textButton2 = new TextButton(null, skin1);
        textButton2.setText("EXIT");
        table.add(textButton2);
        textButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "textButton2: " + textButton2.isChecked());
                System.exit(0);
            }
        });

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
        skin1.dispose();
    }

    // Unused methods:
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