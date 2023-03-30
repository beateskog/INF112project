package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HP_bar{
    private final int MAX_HEALTH;
    private int health;
    private ProgressBar HpBar;

    public HP_bar(int initHealth, Stage stage) {
        this.MAX_HEALTH = initHealth;
        this.health = initHealth;
        init_HpBar(stage);
    }


    private void init_HpBar(Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/HP_bar/HP_bar.json"));
        this.HpBar = new ProgressBar(0, MAX_HEALTH, 1, false, skin);
        this.HpBar.setValue(health);
        this.HpBar.setPosition(stage.getWidth()/2-(this.getWidth()/2), stage.getHeight()-50);
        stage.addActor(HpBar);
    }

    /**
     * Setter function for setting health
     * @param h
     */
    public void setHealth(int h) {
        this.health = h;
        HpBar.setValue(h);
    }

    /**
     * Getter function for retrieving current health
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    public float getWidth() {
        return HpBar.getWidth();
    }
}
