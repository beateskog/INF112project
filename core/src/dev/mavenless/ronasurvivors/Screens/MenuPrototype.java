package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mavenless.ronasurvivors.RonaSurvivors;
import mavenless.ronasurvivors.Utils.InputUtil;

public class MenuPrototype implements Screen{
    private final RonaSurvivors game;
    private Skin skin;
    private Stage stage;
    private ExtendViewport extendViewport;
    private InputUtil inputUtil;
    private Texture backgroundImage;


    public MenuPrototype (RonaSurvivors game){
        this.game = game;
        create();
    }
   
    private void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/prototypeUI/MenuPrototype.json"));
        Gdx.input.setInputProcessor(stage);

        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        Table table = new Table();
        table.setName("Høie");
        table.setFillParent(true);

        backgroundImage = new Texture(Gdx.files.internal("sprites/menu background.png"));

        final ImageButton imageButton1 = new ImageButton(skin, "doctorImageButton");
        imageButton1.setName("Doctor");
        table.add(imageButton1);
        imageButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "Button 1: " + imageButton1.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        final ImageButton imageButton2 = new ImageButton(skin, "doctorImageButton");
        imageButton2.setName("Doctor");
        table.add(imageButton2);
        
        imageButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "Button 2: " + imageButton2.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        

        final ImageTextButton imageTextButton1 = new ImageTextButton(null, skin, "Anti-Vaxxer");
        imageTextButton1.setName("Anti-Vaxxer");
        imageTextButton1.setText("It do be like that");
        table.add(imageTextButton1);
        
        imageTextButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("UI", "ImageTextButton 1: " + imageTextButton1.isChecked());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        

        table.row();
        final ImageButton imageButton3 = new ImageButton(skin, "doctorImageButton");
        imageButton3.setName("Doctor");
        table.add(imageButton3);

        final ImageTextButton imageTextButton2 = new ImageTextButton(null, skin);
        imageTextButton2.setText("Please forgive me for what I got to yabba dabba do");
        table.add(imageTextButton2);

        final ImageTextButton imageTextButton3 = new ImageTextButton(null, skin, "PlagueDoctor");
        imageTextButton3.setName("PlagueDoctor");
        table.add(imageTextButton3);

        table.row();
        table.add();

        Button button = new Button(skin);
        table.add(button);

        table.add();
        stage.addActor(table);
    }

    
    @Override
    public void render(float arg) {
        ScreenUtils.clear(Color.RED);
       
        //extendViewport.apply();
       //game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, extendViewport.getCamera().position.x - Gdx.graphics.getWidth() / 2f,
                extendViewport.getCamera().position.y - Gdx.graphics.getHeight() / 2f);
        game.batch.end();

        //stage.getViewport().apply();
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
    public void show() {
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
}
