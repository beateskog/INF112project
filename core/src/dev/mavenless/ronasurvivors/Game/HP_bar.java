package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import mavenless.ronasurvivors.Utils.UiHandler;

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
        Skin skin = UiHandler.UiStyle.Debug.getSkin();
        this.HpBar = new ProgressBar(0, MAX_HEALTH, 1, false, skin);
        this.HpBar.setValue(health);
        this.HpBar.setWidth(200);
        this.HpBar.setPosition(stage.getWidth()/2-(this.HpBar.getWidth()/2), stage.getHeight()-20);
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
}
