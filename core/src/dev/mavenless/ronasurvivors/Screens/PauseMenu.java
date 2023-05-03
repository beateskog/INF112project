package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mavenless.ronasurvivors.RonaSurvivors;

public class PauseMenu implements Screen{
    private Stage stage;
    private Skin skin;
    private final RonaSurvivors game;
    
    public PauseMenu(RonaSurvivors game){
        this.game = game;
        this.create();
    }

    public RonaSurvivors getGame(){
        return this.game;
    }

    private void create(){
        this.skin = new Skin(Gdx.files.internal("ui/Menu/MainMenuScreen.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.add(resumeButton());
        table.row();
        table.add(exitButton());
    }

    private TextButton resumeButton(){
        TextButton textButton1 = new TextButton(null, skin);
        textButton1.setText("RESUME GAME");
        // textButton1.addListener(new ChangeListener() {
        //     @Override
        //     public void changed(ChangeEvent event, Actor actor) {
        //         getGame().resume();
        //         dispose();
        //     }
        // }
        return textButton1;
    }

    private TextButton exitButton(){
        TextButton textButton2 = new TextButton(null, skin);
        textButton2.setText("EXIT");
        // textButton2.addListener(new ChangeListener() {
        //     @Override
        //     public void changed(ChangeEvent event, Actor actor) {
        //         game.resume();
        //         dispose();
        //     }
        // }
        return textButton2;
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void render(float arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}
